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
	
	public static ArrayList<ReturnPiece> piecesList = new ArrayList<ReturnPiece>();
	//public static ArrayList<Boolean> isKingFirstMove = new ArrayList<Boolean>();
	//public static ArrayList<Boolean> isRookFirstMove = new ArrayList<Boolean>();
	public static boolean[] isRookFirstMove = {false, false, false, false}; //first 2 white, last 2 black, left first then right
	public static boolean[] isKingFirstMove = {false, false}; //white first
	
	public static Chess.Player currPlayer = Chess.Player.white;
	
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
		if (move == "resign") return null; //resign, need revise here, what to return?
		
		ReturnPlay temp = new ReturnPlay();
		ReturnPiece tempPiece = new ReturnPiece();
		
		if (move.charAt(0) == move.charAt(3) && move.charAt(1) == move.charAt(4)) { //same position
			temp.piecesOnBoard = piecesList;
			System.out.print(ReturnPlay.Message.ILLEGAL_MOVE);
			return temp;
		}
		
		Piece thisPiece = null, lastPiece = null;
		King whiteKing = null, blackKing = null;
		
		int playerIsWhite;
		if (currPlayer == Chess.Player.white) playerIsWhite = 1; //if curr player is white
		else playerIsWhite = 0; //if black
		
		for (int i = 0; i < piecesList.size(); i++) { //find kings
			ReturnPiece currReturnPiece = piecesList.get(i);
			if (currReturnPiece.pieceType == ReturnPiece.PieceType.BK) { //if BK
				blackKing = new King (currReturnPiece, move, piecesList);
			}
			else if (currReturnPiece.pieceType == ReturnPiece.PieceType.WK) { //if WK
				whiteKing = new King (currReturnPiece, move, piecesList);
			}
		}
		boolean spaceCheck = false;
		for (int i = 0; i < piecesList.size(); i++) { //find piece
			ReturnPiece currReturnPiece = piecesList.get(i);
			
			if (currReturnPiece.pieceFile.toString().charAt(0) == move.charAt(0) && //column
				currReturnPiece.pieceRank == move.charAt(1) - '0') { // row //find the piece
				spaceCheck = true;
					/**create chess type**/
					if (currReturnPiece.pieceType == ReturnPiece.PieceType.BK ||
						currReturnPiece.pieceType == ReturnPiece.PieceType.WK) { //if king
							thisPiece = new King (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								System.out.print(ReturnPlay.Message.ILLEGAL_MOVE); 
								return temp;
							}
					}
					
					else if (currReturnPiece.pieceType == ReturnPiece.PieceType.BB ||
						currReturnPiece.pieceType == ReturnPiece.PieceType.WB) { //if bishop
							thisPiece = new Bishop (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								System.out.print(ReturnPlay.Message.ILLEGAL_MOVE); 
								return temp;
							}
					}
					
					else if (currReturnPiece.pieceType == ReturnPiece.PieceType.BN ||
						currReturnPiece.pieceType == ReturnPiece.PieceType.WN) { //if knight
							thisPiece = new Knight (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								System.out.print(ReturnPlay.Message.ILLEGAL_MOVE); 
								return temp;
							}
					}
					
					else if (currReturnPiece.pieceType == ReturnPiece.PieceType.BP ||
						currReturnPiece.pieceType == ReturnPiece.PieceType.WP) { //if pawn
							thisPiece = new Pawn (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								System.out.print(ReturnPlay.Message.ILLEGAL_MOVE); 
								return temp;
							}
					}
					
					else if (currReturnPiece.pieceType == ReturnPiece.PieceType.BQ ||
						currReturnPiece.pieceType == ReturnPiece.PieceType.WQ) { //if queen
							thisPiece = new Queen (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								System.out.print(ReturnPlay.Message.ILLEGAL_MOVE); 
								return temp;
							}
					}
					
					else if (currReturnPiece.pieceType == ReturnPiece.PieceType.BR||
						currReturnPiece.pieceType == ReturnPiece.PieceType.WR) { //if rook
							thisPiece = new Rook (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								System.out.print(ReturnPlay.Message.ILLEGAL_MOVE); 
								return temp;
							}
					}
					else { //no Piece on that tile
						temp.piecesOnBoard = piecesList;
						System.out.print(ReturnPlay.Message.ILLEGAL_MOVE); 
						return temp;
					}
					
					boolean requestedMoveValid = thisPiece.isValidMove();
					System.out.println("testmessage");
					System.out.println(thisPiece == null);
					/**create chess type**/
					
					//if currColor != playerColor
					if (thisPiece.isWhite != playerIsWhite) {
						temp.piecesOnBoard = piecesList;
						System.out.print(ReturnPlay.Message.ILLEGAL_MOVE); 
						return temp;
					}
					
					////if currColor != playerColor
					if (thisPiece.tarFile > 8 || thisPiece.tarFile < 1 || thisPiece.tarRank > 8 || thisPiece.tarRank < 1) {
						temp.piecesOnBoard = piecesList;
						System.out.print(ReturnPlay.Message.ILLEGAL_MOVE); 
						return temp;
					}
					
					/**move**/
					tempPiece.pieceType = currReturnPiece.pieceType; 
					
					tempPiece.pieceFile = findFile(move.charAt(3));
					
					tempPiece.pieceRank = move.charAt(4) - '0';
					piecesList.remove(i);
					piecesList.add(tempPiece);
					/**move**/
				
					if (currReturnPiece.pieceType.toString() == "WK"|| currReturnPiece.pieceType.toString() == "BK") { //check castling
						King tempKing = new King(currReturnPiece, move, piecesList);
						if (tempKing.isCastle == true) {
							castlingSetting(move);
							if(castling(currReturnPiece, move) == false) {
								temp.piecesOnBoard = piecesList;
								return temp;
							}
						}
					} 
				
					
					
					/*************find enemy chess and kill it****************/
					for (int j = 0; j < piecesList.size(); j++) {
						if (piecesList.get(i) == currReturnPiece) {
							//same chess as the current one skips
							if (j == piecesList.size()-1) break;
							continue;
						}
						
						ReturnPiece enemyPiece = piecesList.get(i);
						if (enemyPiece.pieceFile.toString().charAt(0) == move.charAt(3) && //column
							enemyPiece.pieceRank == move.charAt(4) - '0') { //there is an enemy chess, no need to check type because it was checked earlier
								piecesList.remove(i);
								break;
						}
					}
					/*************find enemy chess and kill it****************/
					
					if (move.length() == 11 && move.substring(6, 11) == "draw?") { //propose draw
						System.out.print(ReturnPlay.Message.DRAW);
						return null; //need revise here, return ?
					}
					break;
			}
			
			
		}
		if (spaceCheck == false) {
			temp.piecesOnBoard = piecesList;
			System.out.print("something here"+ReturnPlay.Message.ILLEGAL_MOVE); 
			return temp;
		}
		
		temp.piecesOnBoard = piecesList;
		
		if (currPlayer == Chess.Player.white) { //switch side
			currPlayer = Chess.Player.black;
			System.out.println("Change to black");
		}
		else {
			currPlayer = Chess.Player.white;
			System.out.println("Change to white");
		}
		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		return temp;
	}
	
	public static boolean castling(ReturnPiece currReturnPiece, String Move) {

		if (currReturnPiece.pieceType == ReturnPiece.PieceType.BK) {//if black king
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
		
		if (currReturnPiece.pieceType == ReturnPiece.PieceType.WK) {//if black king
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
	}
	
	public static void castlingSetting(String move) {
		if (move.charAt(0) == ReturnPiece.PieceFile.a.toString().charAt(0)) { //if left column rook
			if (move.charAt(1) - '0' == 8) {//if left black rook
				isRookFirstMove[2] = true;
			} else if (move.charAt(1) - '0' == 1) { //left white rook
				isRookFirstMove[0] = true;
			}
		}
		
		if (move.charAt(0) == ReturnPiece.PieceFile.h.toString().charAt(0)) { //if right column rook
			if (move.charAt(1) - '0' == 8) {//if right black rook
				isRookFirstMove[4] = true; 
			} else if (move.charAt(1) - '0' == 1) { //right white rook
				isRookFirstMove[1] = true;
			}
		}
		
		if (move.charAt(0) == ReturnPiece.PieceFile.e.toString().charAt(0)) { //if king
			if (move.charAt(1) - '0' == 8) {//black rook
				isKingFirstMove[1] = true; 
			} else if (move.charAt(1) - '0' == 1) { //white rook
				isKingFirstMove[0] = true;
			}
		}
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
		currPlayer = Chess.Player.white;
		System.out.println("Setting player to white");
		
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
		
		piecesList = pieces;
		//PlayChess.printBoard(pieces);
	}
}