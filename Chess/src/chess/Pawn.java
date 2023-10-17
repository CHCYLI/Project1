package chess;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Objects;

public class Pawn extends Piece {
	ReturnPiece capturedPiece;
	
	//isCapturable Vars
	char tarColor;
	
	//enPassant
	boolean hasMovedTwo = false;
	
	public Pawn(ReturnPiece currPiece, String move, ArrayList<ReturnPiece> list) {
		super(currPiece,move,list);
		if (!Objects.isNull(tarPiece)) {
			tarColor = tarPiece.pieceType.toString().charAt(0); //'W' or 'B'
		}
	}
	
	public boolean isValidMove() {
		/*if (currPiece.pieceType != ReturnPiece.PieceType.WP || currPiece.pieceType != ReturnPiece.PieceType.BP) {
			return false;
		}*/
		
		if (!this.isBlocked() && tarFile == currFile) { //target cell is empty & in same column(file)
			//white
			if (isWhite == 1) {
				if (currRank == 2 && tarRank == currRank + 2) { //first move: can move 2
					hasMovedTwo = true;
					return true;
				}
				if (tarRank == currRank + 1) { //regular rule
					hasMovedTwo = false;
					return true;
				}
			}
			//black
			else if (isWhite == 0) {
				if (currRank == 7 && tarRank == currRank - 2) {
					hasMovedTwo = true;
					return true;
				}
				if (tarRank == currRank - 1) {
					hasMovedTwo = false;
					return true;
				}
			}
		}
		return false; //capturedPiece == null in this case
	}
	
	public boolean isCapture() {
		if (currPiece.pieceType != ReturnPiece.PieceType.WP || currPiece.pieceType != ReturnPiece.PieceType.BP) {
			return false;
		}
		
		if (this.isBlocked() && currColor != tarColor) { //target is occupied & isn't friendly
			//white
			if (isWhite == 1) {
				if (tarRank == currRank + 1) {
					if (tarFile == currFile + 1 || tarFile == currFile - 1)
						capturedPiece = tarPiece;
						return true;
				}
			}
			//black
			else if (isWhite == 0) {
				if (tarRank == currRank - 1) {
					if (tarFile == currFile + 1 || tarFile == currFile - 1)
						capturedPiece = tarPiece;
						return true;
				}
			}
		}
		return false;
	}
	
	public boolean isEnPassant() {
		if (currPiece.pieceType != ReturnPiece.PieceType.WP || currPiece.pieceType != ReturnPiece.PieceType.BP) {
			return false;
		}
		
		//capturing pawn moved 3 ranks (5/4); enemy pawn moved two squares
		//en passant is possible if: 
		//1) tarPiece is an enemy pawn; OK
		//2) currPiece is on rank 5 or 4; OK
		//3) tarPiece has just moved 2 ranks last turn to rank 5 or 4
		//In that case: move front left/right
		//target tile (diagnoal 1) should be empty
		//captured tile is also occupied by a pawn, and that pawn is not friendly
		if (!this.isBlocked()) {
			for (int i = 0; i < piecesList.size(); i++) {
				ReturnPiece checkingPiece = piecesList.get(i);
				int checkingFile = checkingPiece.toString().charAt(0) - '`'; //numerical value of current file/rank checked
				int checkingRank = checkingPiece.toString().charAt(1) - '0';
				int checkingIsWhite = 0;
				if (checkingPiece.toString().charAt(3) == 'W') checkingIsWhite = 1;
				else if (checkingPiece.toString().charAt(3) == 'B') checkingIsWhite = 0;
				
				if (checkingRank == currRank && Math.abs(checkingFile-currFile) == 1) {
					//white
					if (isWhite == 1) {
						if (currRank == 5 && checkingPiece.pieceType.toString() == "BP") { //implement moves check
							//lastMoved check to be implemented in Chess
							if (tarRank == 6 && tarFile == checkingFile)
								capturedPiece = checkingPiece;
								return true;
						}
					}
					//black
					else if (isWhite == 0) {
						if (currRank == 4 && checkingPiece.pieceType.toString() == "WP") {
							if (tarRank == 3 && tarFile == checkingFile)
								capturedPiece = checkingPiece;
								return true;
						}
					}
				}
			}
		}
		return false;
		}
	
	public boolean canPromote() {
		if (this.isValidMove()) {
			if (isWhite == 1 && currRank == 7 && tarRank == 8) {
				return true;
			}
			else if (isWhite == 0 && currRank == 2 && tarRank == 1) {
				return true;
			}
		}
		return false;
	}
}