package chess;
import java.lang.Math;
import java.util.ArrayList;

public class Bishop extends Piece{
	private ReturnPiece pieceMove;
	private String Move;
	private ArrayList<ReturnPiece> piecesList;
	
	public Bishop(ReturnPiece piecemove, String move, ArrayList<ReturnPiece> list, boolean[] isRookFirstMove, boolean[] isKingFirstMove) {
		super(piecemove, move, list, isRookFirstMove, isKingFirstMove);
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
		
	}
	
	public boolean isBlock(int quadrant) {
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
