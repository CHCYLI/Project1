package chess;

import java.lang.Math;
import java.util.ArrayList;

public class King extends Piece{
	boolean isCastle = false;
	boolean[] directionOfCheck = {false,false,false,false,false,false,false,false}; //direction of checking piece
	boolean[] canCaptureCheck = {false,false,false,false,false,false,false,false}; //checking piece within movement range of king
	boolean checkedByKnight; //a knight is checking king
	//for boolean[i]: i = 0,2,4,6: N,E,S,W; 1,3,5,7: NE,SE,SW,NW
	
	public King(ReturnPiece currPiece, String move, ArrayList<ReturnPiece> list) {
		super(currPiece, move, list); 
	}
	
	public boolean isValidMove() {

		/*if (currPiece.pieceType != ReturnPiece.PieceType.WK || currPiece.pieceType != ReturnPiece.PieceType.BK) { // if king
			return false;
		}*/
		
		if (!this.isBlocked()) {
			int fileDiff = Math.abs(tarFile - currFile); 
			int rankDiff = Math.abs(tarRank - currRank);
			
			if (rankDiff == 0 && fileDiff == 2) {
				isCastle = true;
				return true;
			}
			
			if (fileDiff == 1 || rankDiff == 1) {
				if (fileDiff*rankDiff == 1 || fileDiff*rankDiff == 0)
					isCastle = false;
					return true;
			}
		}
		return false;		
	}
	
	public boolean isInCheck() {
		//Horizontally: R/Q
		//Diagonally: B/Q
		//P (2 tiles)
		//K (8 tiles)
		
		//** CHECK MOVEABLE TILES TOO, maybe implement in another function
		int fileDiff = tarFile - currFile; //xDiff
		int rankDiff = tarRank - currRank; //yDiff
		for (int i = 0; i < piecesList.size(); i++) {
			ReturnPiece checkingPiece = piecesList.get(i);
			int checkingFile = checkingPiece.toString().charAt(0) - '`'; //numerical value of current file/rank checked
			int checkingRank = checkingPiece.toString().charAt(1) - '0';
			int checkingFileDiff = checkingFile - currFile;
			int checkingRankDiff = checkingRank - currRank;
			int checkingIsWhite = 0;
			if (checkingPiece.toString().charAt(3) == 'W') checkingIsWhite = 1;
			else if (checkingPiece.toString().charAt(3) == 'B') checkingIsWhite = 0;
			
			if (checkingIsWhite != isWhite) { //diff color: ENEMY PIECE!
				//Rook-type check
				if ((checkingFile == currFile ^ checkingRank == currRank) &&
				(checkingPiece.toString().charAt(4) == 'Q' || checkingPiece.toString().charAt(4) == 'R')) { //if queen/bishop
					//check for blockage
					boolean isBlocked = false;
					for (int j = 0; j < piecesList.size(); j++) {
						ReturnPiece blockedPiece = piecesList.get(j);
						int blockedFile = blockedPiece.toString().charAt(0) - '`';
						int blockedRank = blockedPiece.toString().charAt(1) - '0';
						int blockedFileDiff = blockedFile - currFile;
						int blockedRankDiff = blockedRank - currRank;
						int blockedIsWhite = 0;
						if (blockedPiece.toString().charAt(3) == 'W') blockedIsWhite = 1;
						else if (blockedPiece.toString().charAt(3) == 'B') blockedIsWhite = 0;
						
						if (blockedRank == checkingRank && Math.abs(checkingFile) > Math.abs(blockedFile)) { //horizontal block
							if (blockedFileDiff * checkingFileDiff > 0) { //difference has same sign, meaning they are on same side
								isBlocked = true;
								break;
							}
						}
						else if (blockedFile == checkingFile && Math.abs(checkingRank) > Math.abs(blockedRank)) { //vertical block
							if (blockedRankDiff * checkingRankDiff > 0) {
								isBlocked = true;
								break;
							}
						}
					}
					//if no blockage:
					if (!isBlocked) {
						if (checkingRankDiff > 0) {
							directionOfCheck[0] = true; //N
							if (checkingRankDiff == 1) //if 1 tile away
								canCaptureCheck[0] = true; //capturable
						}
						else if (checkingFileDiff > 0) {
							directionOfCheck[2] = true; //E
							if (checkingFileDiff == 1)
								canCaptureCheck[2] = true;
						}
						else if (checkingRankDiff < 0) {
							directionOfCheck[4] = true; //S
							if (checkingRankDiff == -1)
								canCaptureCheck[4] = true;
						}
						else if (checkingFileDiff < 0) {
							directionOfCheck[6] = true; //W
							if (checkingFileDiff == -1)
								canCaptureCheck[6] = true;
						}
					}
				}
				//Bishop-type check
				else if (Math.abs(checkingFileDiff) == Math.abs(checkingRankDiff) && //equal absolute diff in file/rank
				(checkingPiece.toString().charAt(4) == 'Q' || checkingPiece.toString().charAt(4) == 'B')) { //if queen/bishop
					boolean isBlocked = false;
					for (int j = 0; j < piecesList.size(); j++) {
						ReturnPiece blockedPiece = piecesList.get(j);
						int blockedFile = blockedPiece.toString().charAt(0) - '`';
						int blockedRank = blockedPiece.toString().charAt(1) - '0';
						int blockedFileDiff = blockedFile - currFile;
						int blockedRankDiff = blockedRank - currRank;
						int blockedIsWhite = 0;
						if (blockedPiece.toString().charAt(3) == 'W') blockedIsWhite = 1;
						else if (blockedPiece.toString().charAt(3) == 'B') blockedIsWhite = 0;
						
						//... ** TBI **
						if (blockedFile < checkingFile && blockedRank < checkingRank) { //NE Block
							if (blockedFile == blockedRank && blockedFileDiff > 0) {
								isBlocked = true;
								break;
							}
						}
						else if (blockedFile < checkingFile && blockedRank > checkingRank) { //SE
							if (blockedFile == -blockedRank && blockedFileDiff > 0) {
								isBlocked = true;
								break;
							}
						}
						else if (blockedFile > checkingFile && blockedRank > checkingRank) { //SW
							if (blockedFile == blockedRank && blockedFileDiff < 0) {
								isBlocked = true;
								break;
							}
						}
						else if (blockedFile > checkingFile && blockedRank < checkingRank) { //NW
							if (blockedFile == -blockedRank && blockedFileDiff < 0) {
								isBlocked = true;
								break;
							}
						}
					
						//if no blockage:
						if (!isBlocked) {
							if (checkingFileDiff == checkingRankDiff && checkingFileDiff > 0) {
								directionOfCheck[1] = true; //NE
								if (checkingFileDiff == 1) //if 1 tile away
									canCaptureCheck[1] = true; //capturable
							}
							else if (checkingFileDiff == -checkingRankDiff && checkingFileDiff > 0) {
								directionOfCheck[3] = true; //SE
								if (checkingFileDiff == 1)
									canCaptureCheck[3] = true;
							}
							else if (checkingFileDiff == checkingRankDiff && checkingFileDiff < 0) {
								directionOfCheck[5] = true; //SW
								if (checkingFileDiff == -1)
									canCaptureCheck[5] = true;
							}
							else if (checkingFileDiff == -checkingRankDiff && checkingFileDiff < 0) {
								directionOfCheck[7] = true; //NW
								if (checkingFileDiff == -1)
									canCaptureCheck[7] = true;
							}
						}
					}
				}
				//Pawn check
				else if (checkingPiece.toString().charAt(4) == 'P') {
					if (isWhite == 1) { //white king
						if (checkingFile == currFile + 1 && checkingRank == currRank + 1) {
							directionOfCheck[1] = true; //NE
							canCaptureCheck[1] = true;
						}
						else if (checkingFile == currFile - 1 && checkingRank == currRank + 1) {
							directionOfCheck[7] = true; //NW
							canCaptureCheck[7] = true;
						}
					}
					else if (isWhite == 0) { //black king
						if (checkingFile == currFile + 1 && checkingRank == currRank - 1) {
							directionOfCheck[3] = true; //SE
							canCaptureCheck[3] = true;
						}
						else if (checkingFile == currFile - 1 && checkingRank == currRank - 1) {
							directionOfCheck[5] = true; //SW
							canCaptureCheck[5] = true;
						}
					}
				}
				//Knight check
				else if (checkingPiece.toString().charAt(4) == 'N') {
					if (Math.abs(checkingFileDiff)*Math.abs(checkingRankDiff) == 2)
						checkedByKnight = true;
				}
			}
		}
		
		if (checkedByKnight)
			return true;
		for (int i = 0; i < directionOfCheck.length; i++) {
			if (directionOfCheck[i])
				return true;
		}
		return false;
	}
	
	public boolean isMate() {
		if (this.isInCheck()) {
			for (int i = 0; i < directionOfCheck.length; i++) {
				if (!directionOfCheck[i])
					return false;
			}
			return true;
		}
		return false;
	}

	/*private ReturnPiece pieceMove;
	private String Move;
	private ArrayList<ReturnPiece> piecesList;
	private boolean[] isKingFirstMove;
	private boolean[] isRookFirstMove;
	
	public King(ReturnPiece piecemove, String move, ArrayList<ReturnPiece> list, boolean[] isRookFirst, boolean[] isKingFirst) {
		super(piecemove, move, list);
		pieceMove = piecemove;
		Move = move;
		piecesList = list;
		isKingFirstMove = isKingFirst;
		isRookFirstMove = isRookFirst;
	}
	
	public boolean isValidMove() {

		if (pieceMove.pieceType != ReturnPiece.PieceType.WK || pieceMove.pieceType != ReturnPiece.PieceType.BK) { // if king
			return false;
		}
		
		int diagonalMove = 0, UpDownMove = 0, rookcheck = 0;
		
		if (Math.abs((Move.charAt(1) - '0') - Move.charAt(4) - '0') == 1 &&//row
			Math.abs(Move.charAt(0) - Move.charAt(3)) == 1) { //column
				diagonalMove = 1;
		}
			
		if ((pieceMove.pieceFile.toString().charAt(0) != Move.charAt(0) && (Move.charAt(4)- '0') == pieceMove.pieceRank) || //horizontal move
			(pieceMove.pieceFile.toString().charAt(0) == Move.charAt(0) && (Move.charAt(4)- '0') != pieceMove.pieceRank)) { //vertical move
				rookcheck = 1;
		}
		
		if (rookcheck == 1) {
			if (Math.abs(pieceMove.pieceFile.toString().charAt(0) - Move.charAt(0)) == 1 || //vertical
				Math.abs((Move.charAt(4)- '0') - pieceMove.pieceRank) == 1) { //horizontal
					UpDownMove = 1;
			}
		}
		
		if (diagonalMove == 1 || UpDownMove == 1) {
			return true;
		} else {
			return false;
		}
		
	}*/
	
	/*public boolean castling() {

		if (pieceMove.pieceType == ReturnPiece.PieceType.BK) {//if black king
			if ((Move.charAt(0) == ReturnPiece.PieceFile.e.toString().charAt(0) && Move.charAt(1) - '0' == 8) &&
				(Move.charAt(3) == ReturnPiece.PieceFile.c.toString().charAt(0) && Move.charAt(4) - '0' == 8) &&
				isKingFirstMove[1] == false && isRookFirstMove[2] == false) {  //castling with left black rook
				
				for (int i = 0; i < piecesList.size(); i++) {
					if (piecesList.get(i).pieceFile.toString().charAt(i) == ReturnPiece.PieceFile.b.toString().charAt(0) && piecesList.get(i).pieceRank == 8) return false;
					if (piecesList.get(i).pieceFile.toString().charAt(i) == ReturnPiece.PieceFile.c.toString().charAt(0) && piecesList.get(i).pieceRank == 8) return false;
					if (piecesList.get(i).pieceFile.toString().charAt(i) == ReturnPiece.PieceFile.d.toString().charAt(0) && piecesList.get(i).pieceRank == 8) return false;
				}
					return true;
			}
			
			if ((Move.charAt(0) == ReturnPiece.PieceFile.e.toString().charAt(0) && Move.charAt(1) - '0' == 8) &&
				(Move.charAt(3) == ReturnPiece.PieceFile.g.toString().charAt(0) && Move.charAt(4) - '0' == 8) &&
				isKingFirstMove[1] == false && isRookFirstMove[3] == false) {  //castling with right black rook
				
				for (int i = 0; i < piecesList.size(); i++) {
					if (piecesList.get(i).pieceFile.toString().charAt(i) == ReturnPiece.PieceFile.f.toString().charAt(0) && piecesList.get(i).pieceRank == 8) return false;
					if (piecesList.get(i).pieceFile.toString().charAt(i) == ReturnPiece.PieceFile.g.toString().charAt(0) && piecesList.get(i).pieceRank == 8) return false;
				}
					return true;
			}
		}
		
		if (pieceMove.pieceType == ReturnPiece.PieceType.WK) {//if black king
			if ((Move.charAt(0) == ReturnPiece.PieceFile.e.toString().charAt(0) && Move.charAt(1) - '0' == 1) &&
				(Move.charAt(3) == ReturnPiece.PieceFile.c.toString().charAt(0) && Move.charAt(4) - '0' == 1) &&
				isKingFirstMove[1] == false && isRookFirstMove[2] == false) {  //castling with left white rook
				
				for (int i = 0; i < piecesList.size(); i++) {
					if (piecesList.get(i).pieceFile.toString().charAt(i) == ReturnPiece.PieceFile.b.toString().charAt(0) && piecesList.get(i).pieceRank == 1) return false;
					if (piecesList.get(i).pieceFile.toString().charAt(i) == ReturnPiece.PieceFile.c.toString().charAt(0) && piecesList.get(i).pieceRank == 1) return false;
					if (piecesList.get(i).pieceFile.toString().charAt(i) == ReturnPiece.PieceFile.d.toString().charAt(0) && piecesList.get(i).pieceRank == 1) return false;
				}
					return true;
			}
				
			if ((Move.charAt(0) == ReturnPiece.PieceFile.e.toString().charAt(0) && Move.charAt(1) - '0' == 1) &&
				(Move.charAt(3) == ReturnPiece.PieceFile.g.toString().charAt(0) && Move.charAt(4) - '0' == 1) &&
				isKingFirstMove[1] == false && isRookFirstMove[3] == false) {  //castling with right white rook
				
				for (int i = 0; i < piecesList.size(); i++) {
					if (piecesList.get(i).pieceFile.toString().charAt(i) == ReturnPiece.PieceFile.f.toString().charAt(0) && piecesList.get(i).pieceRank == 1) return false;
					if (piecesList.get(i).pieceFile.toString().charAt(i) == ReturnPiece.PieceFile.g.toString().charAt(0) && piecesList.get(i).pieceRank == 1) return false;
				}
					return true;
			}
		}

		return false;
	}*/
}

