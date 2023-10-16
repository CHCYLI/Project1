package chess;

import java.util.ArrayList;

public class King extends Piece{
	
boolean isCastle = false;
	
	public King(ReturnPiece currPiece, String move, ArrayList<ReturnPiece> list) {
		super(currPiece, move, list); 
	}
	
	public boolean isValidMove() {

		if (currPiece.pieceType != ReturnPiece.PieceType.WK || currPiece.pieceType != ReturnPiece.PieceType.BK) { // if king
			return false;
		}
		
		if (!this.isBlocked()) {
			int fileDiff = Math.abs(tarFile - currFile); int rankDiff = Math.abs(tarRank - currRank);
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
		//Diagonally
		ReturnPiece checkingPiece;
		return true;
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

