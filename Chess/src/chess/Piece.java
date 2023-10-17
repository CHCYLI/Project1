package chess;
import java.util.ArrayList;
import java.util.Objects;

public class Piece extends Chess {
	char currColor; int isWhite; //1 if white, 0 if black
	String move; ReturnPiece currPiece; ReturnPiece tarPiece = null;
	ArrayList<ReturnPiece> piecesList;
	
	int currFile, tarFile, currRank, tarRank;
	
	public Piece(ReturnPiece currPiece, String move, ArrayList<ReturnPiece> list) {
		this.currPiece = currPiece; this.move = move; this.piecesList = list;
		currFile = move.charAt(0) - '`'; tarFile = move.charAt(3) - '`';
		currRank = move.charAt(1) - '0'; tarRank = move.charAt(4) - '0';
		
		currColor = currPiece.pieceType.toString().charAt(0);
		if (currPiece.pieceType.toString().charAt(0) == 'W') isWhite = 1;
		else isWhite = 0;
		
		for (int i = 0; i < piecesList.size(); i++) {
			ReturnPiece checkingPiece = piecesList.get(i);
			if (tarFile == checkingPiece.toString().charAt(0)-'`' &&
			tarRank == checkingPiece.toString().charAt(2)-'0') {
				tarPiece = checkingPiece;
				break;
			}
		}
	}
	
	public boolean isValidMove() {
		if (!this.isBlocked()) {
			return true;
		}
		return false; //placeholder method, to be overridden
	}
	
	public boolean isBlocked() { //default blocked check
		if (!Objects.isNull(tarPiece) && tarPiece.toString().charAt(3) == currColor) {
			return true;
		}
		return false;
	}
	
}