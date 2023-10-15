package chess;

import java.util.ArrayList;

public class Rook extends Piece{
	private ReturnPiece pieceMove;
	private String Move;
	private ArrayList<ReturnPiece> piecesList;
	
	public Rook(ReturnPiece piecemove, String move, ArrayList<ReturnPiece> list, boolean[] isRookFirstMove, boolean[] isKingFirstMove) {
		super(piecemove, move, list, isRookFirstMove, isKingFirstMove);
		pieceMove = piecemove;
		Move = move;
		piecesList = list;
	}
	
	public boolean isValidMove() {
		if (pieceMove.pieceType != ReturnPiece.PieceType.WR || pieceMove.pieceType != ReturnPiece.PieceType.BR) { // if rook
			return false;
		}
		
		int isRouteCorrect = 0;
		boolean isHorizontal = false;
	
		
		//check if the target is on move's route
		if ((pieceMove.pieceFile.toString().charAt(0) != Move.charAt(3) && (Move.charAt(4)- '0') == pieceMove.pieceRank) || //horizontal move
			(pieceMove.pieceFile.toString().charAt(0) == Move.charAt(3) && (Move.charAt(4)- '0') != pieceMove.pieceRank)) { //vertical move
				if (pieceMove.pieceFile.toString().charAt(0) != Move.charAt(3) && (Move.charAt(4)- '0') == pieceMove.pieceRank) isHorizontal = true;
				isRouteCorrect = 1;
		} else {
			return false;
		}
		
		if (isRouteCorrect == 1) { //check if there is any chess between the spots
			return isBlocked(isHorizontal);
		} else {
			return false;
		}
	}
	
	public boolean isBlocked(boolean isHorizontal) {
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
}
