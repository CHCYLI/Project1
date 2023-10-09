package chess;

import java.util.ArrayList;

class ReturnPiece {
	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};
	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  // 1..8
	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece)other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}

class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {
	
	enum Player { white, black }
	
	public static ArrayList<ReturnPiece> pieceMove = new ArrayList<ReturnPiece>();
	
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {
		
		/* FILL IN THIS METHOD */
		//System.out.println(move);
		//ArrayList<ReturnPiece> pieces = ReturnPlay.piecesOnBoard;
		ReturnPlay temp = new ReturnPlay();
		ReturnPiece tempPiece = new ReturnPiece();
		//System.out.println(move.charAt(1) - '0');
		
		for (int i = 0; i < pieceMove.size(); i++) {
			if (pieceMove.get(i).pieceFile.toString().charAt(0) == move.charAt(0) &&
				pieceMove.get(i).pieceRank == move.charAt(1) - '0') {
					tempPiece.pieceType = pieceMove.get(i).pieceType; 
					
					tempPiece.pieceFile = findFile(move.charAt(3));
					
					tempPiece.pieceRank = move.charAt(4) - '0';
					pieceMove.remove(i);
					pieceMove.add(tempPiece);
					
					break;
			}
			//System.out.println("showsomething");
			//System.out.println(move.charAt(3) + ""+ move.charAt(4));
			//System.out.println(pieceMove.get(i).pieceFile.toString().charAt(0));
		}
		
		temp.piecesOnBoard = pieceMove;
		
		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		return temp;
	}
	
	public static ReturnPiece.PieceFile findFile(char moveFile) { //convert char to PieceFile
		ReturnPiece.PieceFile temp = ReturnPiece.PieceFile.a;
		switch(moveFile) {
		case 'a':
			temp = ReturnPiece.PieceFile.a;
			break;
		
		case 'b':
			temp = ReturnPiece.PieceFile.b;
			break;
			
		case 'c':
			temp = ReturnPiece.PieceFile.c;
			break;
			
		case 'd':
			temp = ReturnPiece.PieceFile.d;
			break;
			
		case 'e':
			temp = ReturnPiece.PieceFile.e;
			break;
			
		case 'f':
			temp = ReturnPiece.PieceFile.f;
			break;
			
		case 'g':
			temp = ReturnPiece.PieceFile.g;
			break;
			
		case 'h':
			temp = ReturnPiece.PieceFile.h;
			break;
		}
		
		return temp;
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		ArrayList<ReturnPiece> pieces = new ArrayList<ReturnPiece>();
		
		for (int i = 1; i <= 32; i++) {
			ReturnPiece temp = new ReturnPiece();
			int remainder = i%8;
			if (i <= 8) { //rank 8
				temp.pieceRank = 8;
				switch(remainder) {
				
				case 1:
					temp.pieceFile = ReturnPiece.PieceFile.a;
					temp.pieceType = ReturnPiece.PieceType.BR;
					break;
					
				case 2:
					temp.pieceFile = ReturnPiece.PieceFile.b;
					temp.pieceType = ReturnPiece.PieceType.BN;
					break;
					
				case 3:
					temp.pieceFile = ReturnPiece.PieceFile.c;
					temp.pieceType = ReturnPiece.PieceType.BB;
					break;
					
				case 4:
					temp.pieceFile = ReturnPiece.PieceFile.d;
					temp.pieceType = ReturnPiece.PieceType.BQ;
					break;
					
				case 5:
					temp.pieceFile = ReturnPiece.PieceFile.e;
					temp.pieceType = ReturnPiece.PieceType.BK;
					break;
					
				case 6:
					temp.pieceFile = ReturnPiece.PieceFile.f;
					temp.pieceType = ReturnPiece.PieceType.BB;
					break;
					
				case 7:
					temp.pieceFile = ReturnPiece.PieceFile.g;
					temp.pieceType = ReturnPiece.PieceType.BN;
					break;
					
				case 0:
					temp.pieceFile = ReturnPiece.PieceFile.h;
					temp.pieceType = ReturnPiece.PieceType.BR;
					break;
				}
			} 
			
			if (i >= 9 && i <= 24) { //rank 7 and 2
				if (i <= 16) {
					temp.pieceRank = 7;
					temp.pieceType = ReturnPiece.PieceType.BP;
				} else {
					temp.pieceRank = 2;
					temp.pieceType = ReturnPiece.PieceType.WP;
				}
				
				switch(remainder) {
				
				case 1:
					temp.pieceFile = ReturnPiece.PieceFile.a;
					break;
					
				case 2:
					temp.pieceFile = ReturnPiece.PieceFile.b;
					break;
					
				case 3:
					temp.pieceFile = ReturnPiece.PieceFile.c;
					break;
					
				case 4:
					temp.pieceFile = ReturnPiece.PieceFile.d;
					break;
					
				case 5:
					temp.pieceFile = ReturnPiece.PieceFile.e;
					break;
					
				case 6:
					temp.pieceFile = ReturnPiece.PieceFile.f;
					break;
					
				case 7:
					temp.pieceFile = ReturnPiece.PieceFile.g;
					break;
					
				case 0:
					temp.pieceFile = ReturnPiece.PieceFile.h;
					break;
				}
			}
			
			if (i > 24) { //rank 1
				temp.pieceRank = 1;
				switch(remainder) {
				
				case 1:
					temp.pieceFile = ReturnPiece.PieceFile.a;
					temp.pieceType = ReturnPiece.PieceType.WR;
					break;
					
				case 2:
					temp.pieceFile = ReturnPiece.PieceFile.b;
					temp.pieceType = ReturnPiece.PieceType.WN;
					break;
					
				case 3:
					temp.pieceFile = ReturnPiece.PieceFile.c;
					temp.pieceType = ReturnPiece.PieceType.WB;
					break;
					
				case 4:
					temp.pieceFile = ReturnPiece.PieceFile.d;
					temp.pieceType = ReturnPiece.PieceType.WQ;
					break;
					
				case 5:
					temp.pieceFile = ReturnPiece.PieceFile.e;
					temp.pieceType = ReturnPiece.PieceType.WK;
					break;
					
				case 6:
					temp.pieceFile = ReturnPiece.PieceFile.f;
					temp.pieceType = ReturnPiece.PieceType.WB;
					break;
					
				case 7:
					temp.pieceFile = ReturnPiece.PieceFile.g;
					temp.pieceType = ReturnPiece.PieceType.WN;
					break;
					
				case 0:
					temp.pieceFile = ReturnPiece.PieceFile.h;
					temp.pieceType = ReturnPiece.PieceType.WR;
					break;
				}
			}
			pieces.add(temp);
		}
		
		pieceMove = pieces;
		PlayChess.printBoard(pieces);
	}
}
