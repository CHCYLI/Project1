package chess;

import java.util.ArrayList;
import java.util.Objects;

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
	public static boolean[] isRookFirstMove = {true, true, true, true}; //first 2 white, last 2 black, left first then right
	public static boolean[] isKingFirstMove = {true, true}; //white first
	
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
		if (move.length() == 6 && move.charAt(0) == 'r' && move.charAt(1) == 'e' && move.charAt(2) == 's' && move.charAt(3) == 'i' 
				&& move.charAt(4) == 'g' && move.charAt(5) == 'n') {
			//System.out.println("resign here");
			ReturnPlay tempPlay = new ReturnPlay();
			tempPlay.piecesOnBoard = piecesList;
			if (currPlayer == Chess.Player.white) {
				tempPlay.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
			} else {
				tempPlay.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
			}
			
			return tempPlay;
		} //resign, need revise here, what to return?
		
		ReturnPlay temp = new ReturnPlay();
		ReturnPiece tempPiece = new ReturnPiece();
		
		if (move.charAt(0) == move.charAt(3) && move.charAt(1) == move.charAt(4)) { //same position
			temp.piecesOnBoard = piecesList;
			temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
			//System.out.println("same position");
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
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
								//System.out.println("king");
								return temp;
							}
					}
					
					else if (currReturnPiece.pieceType == ReturnPiece.PieceType.BB ||
						currReturnPiece.pieceType == ReturnPiece.PieceType.WB) { //if bishop
							thisPiece = new Bishop (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
								//System.out.println("bishop");
								return temp;
							}
					}
					
					else if (currReturnPiece.pieceType == ReturnPiece.PieceType.BN ||
						currReturnPiece.pieceType == ReturnPiece.PieceType.WN) { //if knight
							thisPiece = new Knight (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
								//System.out.println("knight");
								return temp;
							}
					}
					
					else if (currReturnPiece.pieceType == ReturnPiece.PieceType.BP ||
							currReturnPiece.pieceType == ReturnPiece.PieceType.WP) { //if pawn
							thisPiece = new Pawn (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false && ((Pawn)thisPiece).isCapture() == false && ((Pawn)thisPiece).isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
								//System.out.println("pawn");
								return temp;
							}
					}
					
					else if (currReturnPiece.pieceType == ReturnPiece.PieceType.BQ ||
						currReturnPiece.pieceType == ReturnPiece.PieceType.WQ) { //if queen
							thisPiece = new Queen (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
								//System.out.println("queen");
								return temp;
							}
					}
					
					else if (currReturnPiece.pieceType == ReturnPiece.PieceType.BR||
						currReturnPiece.pieceType == ReturnPiece.PieceType.WR) { //if rook
							thisPiece = new Rook (currReturnPiece, move, piecesList);
							if (thisPiece.isValidMove() == false) {
								temp.piecesOnBoard = piecesList;
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
								//System.out.println("rook");
								return temp;
							}
					}
					else { //no Piece on that tile
						temp.piecesOnBoard = piecesList;
						temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
						//System.out.println("Nope!");
						return temp;
					}
					//System.out.println("HELLO");
					//boolean requestedMoveValid = thisPiece.isValidMove(); 
					//System.out.println("testmessage");
					//System.out.println(thisPiece == null);
					/**create chess type**/
					
					//if currColor != playerColor
					if (thisPiece.isWhite != playerIsWhite) {
						temp.piecesOnBoard = piecesList;
						temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
						//System.out.println("currcolor != playercolor");
						return temp;
					}
					
					////out of bounds
					if (thisPiece.tarFile > 8 || thisPiece.tarFile < 1 || thisPiece.tarRank > 8 || thisPiece.tarRank < 1) {
						temp.piecesOnBoard = piecesList;
						temp.message = ReturnPlay.Message.ILLEGAL_MOVE; 
						//System.out.println("out of bound");
						return temp;
					}
					
					//enPassant check
					if (thisPiece.currPiece.pieceType == ReturnPiece.PieceType.WP || 
					thisPiece.currPiece.pieceType == ReturnPiece.PieceType.BP) {
						if (((Pawn)thisPiece).isEnPassant() && !Objects.isNull(lastPiece) && 
						lastPiece.currPiece.equals(((Pawn)thisPiece).capturedPiece)) {
						//if the last piece moved is the same as the piece now being captured; ignore warning
							for (int e = 0; e < piecesList.size(); e++) {
								if (piecesList.get(e).equals(lastPiece.currPiece)) {
									piecesList.remove(e); //removes enPassant'd piece
									break;
								}
							}
						}
					}
					/**********************check if friendly before move*************/
					for (int j = 0; j < piecesList.size(); j++) {
						ReturnPiece targetPiece = piecesList.get(j);
						if (targetPiece.pieceFile.toString().charAt(0) == move.charAt(3) &&
							targetPiece.pieceRank == move.charAt(4) - '0') {
							if (targetPiece.pieceType.toString().charAt(0) == currReturnPiece.pieceType.toString().charAt(0)) {
								temp.piecesOnBoard = piecesList;
								//System.out.println("Trigger friendly check");
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE; 
								return temp;
							}
						}
						
					}
					/**********************check if friendly before move*************/
					
					/**move*************************/
					tempPiece.pieceType = currReturnPiece.pieceType; 
					
					tempPiece.pieceFile = findFile(move.charAt(3));
					
					tempPiece.pieceRank = move.charAt(4) - '0';
					piecesList.remove(i);
					piecesList.add(tempPiece);
					/**move**************************/
					
					/**inCheck**/
					//return ILLEGAL_MOVE if king is in check
				for (int k = 0; k < piecesList.size(); k++) { //find kings
					ReturnPiece kingReturnPiece = piecesList.get(i);
					if (kingReturnPiece.pieceType == ReturnPiece.PieceType.BK) { //if BK
						blackKing = new King (kingReturnPiece, move, piecesList);
					}
					else if (kingReturnPiece.pieceType == ReturnPiece.PieceType.WK) { //if WK
						whiteKing = new King (kingReturnPiece, move, piecesList);
					}
				}
				if (playerIsWhite == 1) {
					if (whiteKing.isInCheck()) {
						//check if isMate
						boolean canMove[] = {false,false,false,false,false,false,false,false}; //from N, clockwise
						King tempWhiteKing = whiteKing;
						if (whiteKing.directionOfCheck[0] == false || whiteKing.canCaptureCheck[0] == true) {
							tempWhiteKing.currFile = whiteKing.currFile;
							tempWhiteKing.currRank = whiteKing.currRank + 1;
							if (!tempWhiteKing.isInCheck()) {
								canMove[0] = true;
							}
						}
						else if (whiteKing.directionOfCheck[1] == false || whiteKing.canCaptureCheck[1] == true) {
							tempWhiteKing.currFile = whiteKing.currFile + 1;
							tempWhiteKing.currRank = whiteKing.currRank + 1;
							if (!tempWhiteKing.isInCheck()) {
								canMove[1] = true;
							}
						}
						else if (whiteKing.directionOfCheck[2] == false || whiteKing.canCaptureCheck[2] == true) {
							tempWhiteKing.currFile = whiteKing.currFile + 1;
							tempWhiteKing.currRank = whiteKing.currRank;
							if (!tempWhiteKing.isInCheck()) {
								canMove[2] = true;
							}
						}
						else if (whiteKing.directionOfCheck[3] == false || whiteKing.canCaptureCheck[3] == true) {
							tempWhiteKing.currFile = whiteKing.currFile + 1;
							tempWhiteKing.currRank = whiteKing.currRank - 1;
							if (!tempWhiteKing.isInCheck()) {
								canMove[3] = true;
							}
						}
						else if (whiteKing.directionOfCheck[4] == false || whiteKing.canCaptureCheck[4] == true) {
							tempWhiteKing.currFile = whiteKing.currFile;
							tempWhiteKing.currRank = whiteKing.currRank - 1;
							if (!tempWhiteKing.isInCheck()) {
								canMove[4] = true;
							}
						}
						else if (whiteKing.directionOfCheck[5] == false || whiteKing.canCaptureCheck[5] == true) {
							tempWhiteKing.currFile = whiteKing.currFile - 1;
							tempWhiteKing.currRank = whiteKing.currRank - 1;
							if (!tempWhiteKing.isInCheck()) {
								canMove[5] = true;
							}
						}
						else if (whiteKing.directionOfCheck[6] == false || whiteKing.canCaptureCheck[6] == true) {
							tempWhiteKing.currFile = whiteKing.currFile - 1;
							tempWhiteKing.currRank = whiteKing.currRank;
							if (!tempWhiteKing.isInCheck()) {
								canMove[6] = true;
							}
						}
						else if (whiteKing.directionOfCheck[7] == false || whiteKing.canCaptureCheck[7] == true) {
							tempWhiteKing.currFile = whiteKing.currFile - 1;
							tempWhiteKing.currRank = whiteKing.currRank + 1;
							if (!tempWhiteKing.isInCheck()) {
								canMove[7] = true;
							}
						}
						
						int checkCount = 0;
						for (int cm = 0; cm < canMove.length; cm++) {
							if (canMove[i] == false) 
								checkCount++;
						}
						if (checkCount == 8) {
							temp.piecesOnBoard = piecesList;
							temp.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
							return temp;
						}
						//else if not mate
						else {
							System.out.print(ReturnPlay.Message.CHECK);
							piecesList.add(tempPiece); //test if not in Check
							if (whiteKing.isInCheck() == false) { //check if current move can free king
								continue;
							}
							else { //deny current move
								piecesList.remove(piecesList.size()-1); //remove tempPiece we just added
								temp.piecesOnBoard = piecesList;
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
								return temp;
							}
						}		
					}
				}
				else if (playerIsWhite == 0) { //black
					if (blackKing.isInCheck()) {
						//check if isMate
						boolean canMove[] = {false,false,false,false,false,false,false,false}; //from N, clockwise
						King tempBlackKing = blackKing;
						if (blackKing.directionOfCheck[0] == false || blackKing.canCaptureCheck[0] == true) {
							tempBlackKing.currFile = blackKing.currFile;
							tempBlackKing.currRank = blackKing.currRank + 1;
							if (!tempBlackKing.isInCheck()) {
								canMove[0] = true;
							}
						}
						else if (blackKing.directionOfCheck[1] == false || blackKing.canCaptureCheck[1] == true) {
							tempBlackKing.currFile = blackKing.currFile + 1;
							tempBlackKing.currRank = blackKing.currRank + 1;
							if (!tempBlackKing.isInCheck()) {
								canMove[1] = true;
							}
						}
						else if (blackKing.directionOfCheck[2] == false || blackKing.canCaptureCheck[2] == true) {
							tempBlackKing.currFile = blackKing.currFile + 1;
							tempBlackKing.currRank = blackKing.currRank;
							if (!tempBlackKing.isInCheck()) {
								canMove[2] = true;
							}
						}
						else if (blackKing.directionOfCheck[3] == false || blackKing.canCaptureCheck[3] == true) {
							tempBlackKing.currFile = blackKing.currFile + 1;
							tempBlackKing.currRank = blackKing.currRank - 1;
							if (!tempBlackKing.isInCheck()) {
								canMove[3] = true;
							}
						}
						else if (blackKing.directionOfCheck[4] == false || blackKing.canCaptureCheck[4] == true) {
							tempBlackKing.currFile = blackKing.currFile;
							tempBlackKing.currRank = blackKing.currRank - 1;
							if (!tempBlackKing.isInCheck()) {
								canMove[4] = true;
							}
						}
						else if (blackKing.directionOfCheck[5] == false || blackKing.canCaptureCheck[5] == true) {
							tempBlackKing.currFile = blackKing.currFile - 1;
							tempBlackKing.currRank = blackKing.currRank - 1;
							if (!tempBlackKing.isInCheck()) {
								canMove[5] = true;
							}
						}
						else if (blackKing.directionOfCheck[6] == false || blackKing.canCaptureCheck[6] == true) {
							tempBlackKing.currFile = blackKing.currFile - 1;
							tempBlackKing.currRank = blackKing.currRank;
							if (!tempBlackKing.isInCheck()) {
								canMove[6] = true;
							}
						}
						else if (blackKing.directionOfCheck[7] == false || blackKing.canCaptureCheck[7] == true) {
							tempBlackKing.currFile = blackKing.currFile - 1;
							tempBlackKing.currRank = blackKing.currRank + 1;
							if (!tempBlackKing.isInCheck()) {
								canMove[7] = true;
							}
						}
						
						int checkCount = 0;
						for (int cm = 0; cm < canMove.length; cm++) {
							if (canMove[i] == false) 
								checkCount++;
						}
						if (checkCount == 8) {
							temp.piecesOnBoard = piecesList;
							temp.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
							return temp;
						}
						//else if not mate
						else {
							System.out.print(ReturnPlay.Message.CHECK);
							piecesList.add(tempPiece); //test if not in Check
							if (blackKing.isInCheck() == false) { //check if current move can free king
								continue;
							}
							else { //deny current move
								piecesList.remove(piecesList.size()-1); //remove tempPiece we just added
								temp.piecesOnBoard = piecesList;
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
								return temp;
							}
						}		
					}
				}
				/**inCheck**/
				
				piecesList.remove(i);
				piecesList.add(tempPiece);
				
				if (thisPiece.currPiece.pieceType == ReturnPiece.PieceType.WR || 
						thisPiece.currPiece.pieceType == ReturnPiece.PieceType.BR) {
					if (thisPiece.currFile == 1 && thisPiece.currRank == 1) //SW
						isRookFirstMove[0] = false;
					else if (thisPiece.currFile == 8 && thisPiece.currRank == 1) //SE
						isRookFirstMove[1] = false;
					else if (thisPiece.currFile == 1 && thisPiece.currRank == 8) //NW
						isRookFirstMove[2] = false;
					else if (thisPiece.currFile == 8 && thisPiece.currRank == 8) //NE
						isRookFirstMove[3] = false;
				}
					
					
				/**capture**/
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
				/**capture**/
					
				//castling check
				if (thisPiece.currPiece.pieceType == ReturnPiece.PieceType.WK) {
					if (((King)thisPiece).isCastle && isKingFirstMove[0]) {
						int kingFile = thisPiece.tarFile; //int kingRank = 1;
						for (int r = 0; r < piecesList.size(); r++) {
							ReturnPiece checkingPiece = piecesList.get(r);
							if (checkingPiece.pieceType == ReturnPiece.PieceType.WR) {
								int rookFile = checkingPiece.toString().charAt(0) - '`';
								int rookRank = checkingPiece.toString().charAt(1) - '0';
								ReturnPiece tempRook = checkingPiece; //initialized to avoid null pointer access
								if (isRookFirstMove[0] && rookFile == 1 && rookRank == 1 && kingFile == 3) {
									//move SW Rook
									//Rook moves from from a1 to d1
									tempRook.pieceType = ReturnPiece.PieceType.WR; 
									tempRook.pieceFile = ReturnPiece.PieceFile.d;
									tempRook.pieceRank = 1;
									piecesList.remove(r);
									piecesList.add(tempRook);
									isRookFirstMove[0] = false;
								}
								else if (isRookFirstMove[1] && rookFile == 8 && rookRank == 1 && kingFile == 7) {
									//move SE Rook
									//Rook moves from from h1 to f1
									tempRook.pieceType = ReturnPiece.PieceType.WR; 
									tempRook.pieceFile = ReturnPiece.PieceFile.f;
									tempRook.pieceRank = 1;
									piecesList.remove(r);
									piecesList.add(tempRook);
									isRookFirstMove[1] = false;
								}
							}
						}
					}
				}
				else if (thisPiece.currPiece.pieceType == ReturnPiece.PieceType.BK) {
					if (((King)thisPiece).isCastle && isKingFirstMove[0]) {
						int kingFile = thisPiece.tarFile; //int kingRank = 1;
						for (int r = 0; r < piecesList.size(); r++) {
							ReturnPiece checkingPiece = piecesList.get(r);
							if (checkingPiece.pieceType == ReturnPiece.PieceType.WR) {
								int rookFile = checkingPiece.toString().charAt(0) - '`';
								int rookRank = checkingPiece.toString().charAt(1) - '0';
								ReturnPiece tempRook = checkingPiece;
								if (isRookFirstMove[2] && rookFile == 1 && rookRank == 8 && kingFile == 3) {
									//move NW Rook
									//Rook moves from from a8 to d8
									tempRook.pieceType = ReturnPiece.PieceType.BR; 
									tempRook.pieceFile = ReturnPiece.PieceFile.d;
									tempRook.pieceRank = 8;
									piecesList.remove(r);
									piecesList.add(tempRook);
									isRookFirstMove[2] = false;
								}
								else if (isRookFirstMove[3] && rookFile == 8 && rookRank == 8 && kingFile == 7) {
									//move NE Rook
									//Rook moves from from h8 to f8
									tempRook.pieceType = ReturnPiece.PieceType.BR; 
									tempRook.pieceFile = ReturnPiece.PieceFile.f;
									tempRook.pieceRank = 8;
									piecesList.remove(r);
									piecesList.add(tempRook);
									isRookFirstMove[3] = false;
								}
							}
						}
					}
				}
				
				//promotion check
				if (thisPiece.currPiece.pieceType == ReturnPiece.PieceType.WP && thisPiece.currRank == 8) {
					ReturnPiece promoted = thisPiece.currPiece;
					promoted.pieceFile = findFile(move.charAt(3));
					promoted.pieceRank = move.charAt(4) - '0';
					
					for (int p = 0; p < piecesList.size(); p++) { //find original piece index in list
						ReturnPiece tempPiece2 = piecesList.get(p);
						if (tempPiece2.pieceFile == promoted.pieceFile && tempPiece2.pieceRank == promoted.pieceRank) {
							if (move.length() == 7 && move.substring(6).equals("R")) {
								promoted.pieceType = ReturnPiece.PieceType.WR;
							}
							else if (move.length() == 7 && move.substring(6).equals("B")) {
								promoted.pieceType = ReturnPiece.PieceType.WB;
							}
							else if (move.length() == 7 && move.substring(6).equals("N")) {
								promoted.pieceType = ReturnPiece.PieceType.WN;
							}
							else if (move.length() == 7 && move.substring(6).equals("Q") == false) { //extra character but promotion type is not Q
								temp.piecesOnBoard = piecesList;
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
								return temp;
							}
							else { //defaults to Q promotion
								promoted.pieceType = ReturnPiece.PieceType.WQ;
							}
							piecesList.remove(p);
							piecesList.add(promoted);
						}
					}
				}
				else if (thisPiece.currPiece.pieceType == ReturnPiece.PieceType.BP && thisPiece.currRank == 1) {
					ReturnPiece promoted = thisPiece.currPiece;
					promoted.pieceFile = findFile(move.charAt(3));
					promoted.pieceRank = move.charAt(4) - '0';
					
					for (int p = 0; p < piecesList.size(); p++) { //find original piece index in list
						ReturnPiece tempPiece2 = piecesList.get(p);
						if (tempPiece2.pieceFile == promoted.pieceFile && tempPiece2.pieceRank == promoted.pieceRank) {
							if (move.length() == 7 && move.substring(6).equals("R")) {
								promoted.pieceType = ReturnPiece.PieceType.BR;
							}
							else if (move.length() == 7 && move.substring(6).equals("B")) {
								promoted.pieceType = ReturnPiece.PieceType.BB;
							}
							else if (move.length() == 7 && move.substring(6).equals("N")) {
								promoted.pieceType = ReturnPiece.PieceType.BN;
							}
							else if (move.length() == 7 && move.substring(6).equals("Q") == false) { //extra character but promotion type is not Q
								temp.piecesOnBoard = piecesList;
								temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
								//System.out.print(ReturnPlay.Message.ILLEGAL_MOVE); 
								return temp;
							}
							else {
								promoted.pieceType = ReturnPiece.PieceType.BQ;
							}
							piecesList.remove(p);
							piecesList.add(promoted);
						}
					}
				}
					/**************draw**************/
					if (move.length() == 11 && move.charAt(6) == 'd' && move.charAt(7) == 'r' && move.charAt(8) == 'a' && move.charAt(9) =='w'
						&& move.charAt(10) == '?') { //propose draw
						temp.piecesOnBoard = piecesList;
						temp.message = ReturnPlay.Message.DRAW; 
						return temp;
					}
					break;
			}
			
		//space check
		}
		if (spaceCheck == false) {
			temp.piecesOnBoard = piecesList;
			temp.message = ReturnPlay.Message.ILLEGAL_MOVE;
			//System.out.print("space error"); 
			return temp;
		}
		
		temp.piecesOnBoard = piecesList;
		
		if (currPlayer == Chess.Player.white) { //switch side
			currPlayer = Chess.Player.black;
			//System.out.println("Change to black");
		}
		else {
			currPlayer = Chess.Player.white;
			//System.out.println("Change to white");
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