package chess;
import java.lang.Math;
import java.util.ArrayList;

public class Bishop extends Piece{
	//private ReturnPiece pieceMove;
	//private String Move;
	//private ArrayList<ReturnPiece> piecesList;
	
	/*public Bishop(ReturnPiece piecemove, String move, ArrayList<ReturnPiece> list, boolean[] isRookFirstMove, boolean[] isKingFirstMove) {
		super(piecemove, move, list);
		pieceMove = piecemove;
		Move = move;
		piecesList = list;
	}
	
	public boolean isValidMove() {
		if (pieceMove.pieceType != ReturnPiece.PieceType.WB || pieceMove.pieceType != ReturnPiece.PieceType.BB) { // if bishop
			return false;
		}
		
		boolean isRouteCorrect = false;
		int quadrant = 0;
		
		if (Math.abs((Move.charAt(1) - '0') - Move.charAt(4) - '0') == //row
			Math.abs(Move.charAt(0) - Move.charAt(3))) { //column
				if (Move.charAt(0) < Move.charAt(3) && Move.charAt(1) < Move.charAt(4)) quadrant = 1; //upper right
				if (Move.charAt(0) > Move.charAt(3) && Move.charAt(1) < Move.charAt(4)) quadrant = 2; //upper left
				if (Move.charAt(0) > Move.charAt(3) && Move.charAt(1) > Move.charAt(4)) quadrant = 3; //lower left
				if (Move.charAt(0) < Move.charAt(3) && Move.charAt(1) > Move.charAt(4)) quadrant = 4; //lower right
				isRouteCorrect = true;
		} else {
			return false;
		}
		
		if (isRouteCorrect == true) {
			return isBlock(quadrant);
		} else {
			return false;
		}
		
	}*/
	
	public Bishop(ReturnPiece currPiece, String move, ArrayList<ReturnPiece> list) {
		super(currPiece, move, list);
	}
	
	public boolean isValidMove() {
		if (currPiece.pieceType != ReturnPiece.PieceType.WB || currPiece.pieceType != ReturnPiece.PieceType.BB) { // if bishop
			return false;
		}
		
		if (!this.isBlocked()) {
			int fileDiff = tarFile - currFile;
			int rankDiff = tarRank - currRank;
			
			if (Math.abs(rankDiff) == Math.abs(fileDiff) && Math.abs(fileDiff) != 0) { //x=y, i.e. diagonal
				return true;
			}
		}
		
		//int quadrant = 0;
		/*if (Math.abs((currRank) - tarRank) == //row
			Math.abs((currFile) - (tarFile))) { //column
				if (currFile < tarFile && currRank < tarRank) quadrant = 1; //upper right
				if (currFile > tarFile && currRank < tarRank) quadrant = 2; //upper left
				if (currFile > tarFile && currRank > tarRank) quadrant = 3; //lower left
				if (currFile < tarFile && currRank > tarRank) quadrant = 4; //lower right
				//route is correct
				return isBlocked(quadrant);
		}*/
		return false;
	}
	
	public boolean isBlock() {
		/*for (int i = 0; i < piecesList.size(); i++) {
			char currPieceFile = piecesList.get(i).pieceFile.toString().charAt(i);
			int currPieceRank = piecesList.get(i).pieceRank;
			
			if (quadrant == 1) {
				if ((Move.charAt(3) - currPieceFile) == (Move.charAt(4)-'0' - currPieceRank) && //if diagonal
					currPieceFile < Move.charAt(3) && currPieceRank < Move.charAt(4) -'0' && //target and original
					currPieceFile > Move.charAt(0) && currPieceRank > Move.charAt(1)- '0') { //if between
					return false;
				}
				
			} else if (quadrant == 2) {
				if (Math.abs((Move.charAt(3) - currPieceFile)) == (Move.charAt(4)-'0' - currPieceRank) && //if diagonal
						currPieceFile > Move.charAt(3) && currPieceRank < Move.charAt(4) -'0' &&
						currPieceFile < Move.charAt(0) && currPieceRank > Move.charAt(1)- '0') { //if between
						return false;
					}
				
			} else if (quadrant == 3) {
				if (Math.abs((Move.charAt(3) - currPieceFile)) == Math.abs((Move.charAt(4)-'0' - currPieceRank)) && //if diagonal
						currPieceFile > Move.charAt(3) && currPieceRank > Move.charAt(4) -'0' &&
						currPieceFile < Move.charAt(0) && currPieceRank < Move.charAt(1)- '0') { //if between
						return false;
					}
			
			} else if (quadrant == 4) {
				if ((Move.charAt(3) - currPieceFile) == Math.abs((Move.charAt(4)-'0' - currPieceRank)) && //if diagonal
						currPieceFile < Move.charAt(3) && currPieceRank > Move.charAt(4) -'0' &&
						currPieceFile > Move.charAt(0) && currPieceRank < Move.charAt(1)- '0') { //if between
						return false;
					}
				
			}
		}*/
		
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
			
			if (checkingIsWhite == isWhite && Math.abs(checkingRankDiff) == Math.abs(checkingFileDiff)) {
				if (Math.abs(rankDiff) == Math.abs(fileDiff) && checkingFile != currFile) {
					if (fileDiff > 0 && rankDiff > 0 && checkingFileDiff > 0 && checkingRankDiff > 0) { //Q1, for both tar-curr and checking-curr
						if (checkingFileDiff < fileDiff && checkingRankDiff < rankDiff)
							return true;
					}
					else if (fileDiff < 0 && rankDiff > 0 && checkingFileDiff < 0 && checkingRankDiff > 0) { //Q2
						if (checkingFileDiff > fileDiff && checkingRankDiff < rankDiff)
							return true;
					}
					else if (fileDiff < 0 && rankDiff < 0 && checkingFileDiff < 0 && checkingRankDiff < 0) { //Q3
						if (checkingFileDiff > fileDiff && checkingRankDiff > rankDiff)
							return true;
					}
					else if (fileDiff > 0 && rankDiff < 0 && checkingFileDiff > 0 && checkingRankDiff < 0) { //Q4
						if (checkingFileDiff < fileDiff && checkingRankDiff > rankDiff)
							return true;
					}
				}
				
			}
		}
		
		return true;
	}
}
