package chess;

import java.util.ArrayList;

public class Knight extends Piece {
	
	public Knight(ReturnPiece currPiece, String move, ArrayList<ReturnPiece> list) {
		super(currPiece,move,list);
	}
	
	public boolean isValidMove() {
		/*if (currPiece.pieceType != ReturnPiece.PieceType.WN || currPiece.pieceType != ReturnPiece.PieceType.BN) {
			return false;
		}*/
		
		if (!this.isBlocked()) {
			if (tarRank == currRank + 1 || tarRank == currRank - 1) {
				if (tarFile == currFile + 2 || tarFile == currFile - 2) {
					return true;
			}
				}
			if (tarRank == currRank + 2 || tarRank == currRank - 2) {
				if (tarFile == currFile + 1 || tarFile == currFile - 1) {
					return true;
				}
			}
		}
		return false;
	}
}