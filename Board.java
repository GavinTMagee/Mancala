public class Board {
	private static final int MANCALA_1 = 7;
	private static final int MANCALA_2 = 0;
	
	private int[] board = new int[14];
	public boolean currIsPlayer1;
	
	public Board() {
		for (int i = 0; i < 14; i++) {
			board[i] = 4;
		}
		board[MANCALA_1] = 0;
		board[MANCALA_2] = 0;
		this.currIsPlayer1 = true;
	}
	
	public void moveIndex(int index) {
		int pieces = board[index];
		while (pieces > 0) {
			index += 1;
			if (pieces == 1 && board[index] == 0 && currIsPlayer1 && 1 <= index && index <= 6) {//piece capturing opposite
				board[index] += 1;
				board[MANCALA_1] += board[14 - index];
				board[14 - index] = 0;
				pieces -= 1;
			} else if (pieces == 1 && currIsPlayer1 && 8 <= index && index <= 13) {//piece landing on what it captures
				board[MANCALA_1] += board[index] + 1;
				board[index] = 0;
				pieces -= 1;
			} else if (pieces == 1 && board[index] == 0 && !currIsPlayer1 && 8 <= index && index <= 13) {//piece capturing opposite
				board[index] += 1;
				board[MANCALA_2] += board[14 - index];
				board[14 - index] = 0;
				pieces -= 1;
			} else if (pieces == 1 && !currIsPlayer1 && 1 <= index && index <= 6) {//piece landing on what it captures
				board[MANCALA_2] += board[index] + 1;
				board[index] = 0;
				pieces -= 1;
			}else {
				if (currIsPlayer1 && index != 0) {
					board[index] += 1;
					pieces -= 1;
				}else if (!currIsPlayer1 && index != 7){
					board[index] += 1;
					pieces -= 1;
				}
			}
		}
	}
	
	private int sumSide(boolean player1Side) {
		int sum = 0;
		int shift = (player1Side? 0:7);
		for(int i = 1; i < 7; i++) {
			sum += board[i+shift];
		}
		return sum;
	}

	private boolean gameOver() {
		return sumSide(true)==0 || sumSide(false)==0;
	}

	// returns true if Player1 has won
	private boolean gameEnd() {
		board[MANCALA_1] += sumSide(true);
		board[MANCALA_2] += sumSide(false);
		// TODO: better tie system
		if(board[MANCALA_1] == board[MANCALA_2]) {
			return (Boolean) null;
		}
		return board[MANCALA_1] > board[MANCALA_2];
	}

	public String toString() {
		String repr = "     "; // skips mancala
		for(int i = board.length -1; i > 0; i--) {
			if(i == MANCALA_1) {
				repr+="\n|" + String.format("%02d", board[MANCALA_1]) +"|";
				repr+= new String(new char[(i-1)*4]).replace("\0"," ");
				repr+=" |" + String.format("%02d", board[MANCALA_2]) +"|\n";
			} else {
				repr+=String.format("%02d", board[i])+"  ";
			}
		}
		return repr;
	}
}
