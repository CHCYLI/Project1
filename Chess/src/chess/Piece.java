package chess;

import java.util.ArrayList;

public class Piece extends Chess {
	String move; ReturnPiece currPiece;
	ArrayList<ReturnPiece> piecesList;
	boolean[] isRookFirstMove = new boolean[4];
	boolean[] isKingFirstMove = new boolean[2];
	
	char currFile = move.charAt(0); char tarFile = move.charAt(3);
	int currRank = move.charAt(1); int tarRank = move.charAt(4);
	
	public Piece(ReturnPiece currPiece, String move, ArrayList<ReturnPiece> list, boolean[] isRookFirstMove, boolean[] isKingFirstMove) {
		this.currPiece = currPiece; this.move = move; //this.isWhite = isWhite;
		this.piecesList = list;
		this.isKingFirstMove = isKingFirstMove;
		this.isRookFirstMove = isRookFirstMove;
	}
	
	public boolean isValidMove() {
		return false; //placeholder method, to be overridden
	}
}
