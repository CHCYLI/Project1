package chess;

import java.util.ArrayList;

public class Queen extends Piece{
	private ReturnPiece pieceMove;
	private String Move;
	private ArrayList<ReturnPiece> piecesList;
	
	public Queen(ReturnPiece piecemove, String move, ArrayList<ReturnPiece> list, boolean[] isRookFirstMove, boolean[] isKingFirstMove) {
		super(piecemove, move, list, isRookFirstMove, isKingFirstMove);
		pieceMove = piecemove;
		Move = move;
		piecesList = list;
	}
	
	public boolean isValidMove() {
		int rookcheck = 0, bishopcheck = 0;
		if (pieceMove.pieceType != ReturnPiece.PieceType.WQ || pieceMove.pieceType != ReturnPiece.PieceType.BQ) { // if queen
			return false;
		}
		
		//*************************** Bishop
		int quadrant = 0;
		if (Math.abs((Move.charAt(1) - '0') - Move.charAt(4) - '0') == //row
			Math.abs(Move.charAt(0) - Move.charAt(3))) { //column
				if (Move.charAt(0) < Move.charAt(3) && Move.charAt(1) < Move.charAt(4)) quadrant = 1; //upper right
				if (Move.charAt(0) > Move.charAt(3) && Move.charAt(1) < Move.charAt(4)) quadrant = 2; //upper left
				if (Move.charAt(0) > Move.charAt(3) && Move.charAt(1) > Move.charAt(4)) quadrant = 3; //lower left
				if (Move.charAt(0) < Move.charAt(3) && Move.charAt(1) > Move.charAt(4)) quadrant = 4; //lower right
				bishopcheck = 1;
		}
		
		//******************************* Rook
		boolean isHorizontal = false;
		if ((pieceMove.pieceFile.toString().charAt(0) != Move.charAt(3) && (Move.charAt(4)- '0') == pieceMove.pieceRank) || //horizontal move
			(pieceMove.pieceFile.toString().charAt(0) == Move.charAt(3) && (Move.charAt(4)- '0') != pieceMove.pieceRank)) { //vertical move
				if (pieceMove.pieceFile.toString().charAt(0) != Move.charAt(3) && (Move.charAt(4)- '0') == pieceMove.pieceRank) isHorizontal = true;
				rookcheck = 1;
		}
		
		
		if (rookcheck == 1) {
			return isBlockedRook(isHorizontal);
		} else if (bishopcheck == 1) {
			return isBlockedBishop(quadrant);
		} else {
			return false;
		}
	}
	
	public boolean isBlockedRook(boolean isHorizontal) {
		if (isHorizontal == true) { //is the target horizontal
			for (int i = 0; i < piecesList.size(); i++) {
				char currPieceFile = piecesList.get(i).pieceFile.toString().charAt(i);
				int currPieceRank = piecesList.get(i).pieceRank;
				
				if (piecesList.get(i).equals(pieceMove) || //if the same as original chess,
					(currPieceFile == Move.charAt(3) && currPieceRank == Move.charAt(4))) { // or same as target chess skip this one
					continue;
				}
				
				
				if (Move.charAt(0) < Move.charAt(3) && Move.charAt(1) == Move.charAt(4)) { //check right side
					if (currPieceFile > Move.charAt(0) && currPieceFile < Move.charAt(3) && currPieceRank == Move.charAt(4)) { //if between
						return false;
					}
				}
				
				if (Move.charAt(0) > Move.charAt(3) && Move.charAt(1) == Move.charAt(4)) { //check left side
					if (currPieceFile < Move.charAt(0) && currPieceFile > Move.charAt(3) && currPieceRank == Move.charAt(4)) { //if between
						return false;
					}
				}
				
			}
		} else { //the target is vertical
			for (int i = 0; i < piecesList.size(); i++) {
				char currPieceFile = piecesList.get(i).pieceFile.toString().charAt(i);
				int currPieceRank = piecesList.get(i).pieceRank;
				
				if (piecesList.get(i).equals(pieceMove) || //if the same as original chess,
					(currPieceFile == Move.charAt(3) && currPieceRank == Move.charAt(4))) { // or same as target chess skip this one
						continue;
					}
				
				if (Move.charAt(0) == Move.charAt(3) && Move.charAt(1) < Move.charAt(4)) { //check up side
					if (currPieceFile == Move.charAt(0) && currPieceRank < Move.charAt(4) && currPieceRank > Move.charAt(1)) { //if between
						return false;
					}
				}
				
				if (Move.charAt(0) == Move.charAt(3) && Move.charAt(1) > Move.charAt(4)) { //check down side
					if (currPieceFile == Move.charAt(0) && currPieceRank > Move.charAt(4) && currPieceRank > Move.charAt(2)) { //if between
						return false;
					}
				}
			}
		}
		
		return true;
		
	}
	
	public boolean isBlockedBishop(int quadrant) {
		for (int i = 0; i < piecesList.size(); i++) {
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
		}
		
		return true;
	}
}
