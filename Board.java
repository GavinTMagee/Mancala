import java.util.ArrayList;
import java.util.List;

public class Board {
	private static final int MANCALA_1 = 7;
	private static final int MANCALA_2 = 0;
	
	private int[] board = new int[14];
	public boolean currIsPlayer1;
	
	//for AI component
	public int maxScore = 0;
	public int bestMove = 8;
	
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
		board[index] = 0;
		while (pieces > 0) {//each iteration represents one piece placed
			index = (index + 1) % 14;
			if (pieces == 1 && board[index] == 0 && currIsPlayer1 && 1 <= index && index <= 6) {//piece capturing opposite
				board[index] += 1;
				board[MANCALA_1] += board[14 - index];
				board[14 - index] = 0;
				pieces -= 1;
			//} else if (pieces == 1 && currIsPlayer1 && 8 <= index && index <= 13) {//piece landing on what it captures
				//board[MANCALA_1] += board[index] + 1;
				//board[index] = 0;
				//pieces -= 1;
			} else if (pieces == 1 && board[index] == 0 && !currIsPlayer1 && 8 <= index && index <= 13) {//piece capturing opposite
				board[index] += 1;
				board[MANCALA_2] += board[14 - index];
				board[14 - index] = 0;
				pieces -= 1;
			//} else if (pieces == 1 && !currIsPlayer1 && 1 <= index && index <= 6) {//piece landing on what it captures
				//board[MANCALA_2] += board[index] + 1;
				//board[index] = 0;
				//pieces -= 1;
			}else if (currIsPlayer1 && index != 0) {//normal player 1 move
				board[index] += 1;
				pieces -= 1;
			}else if (!currIsPlayer1 && index != 7){// normal player 2 move
				board[index] += 1;
				pieces -= 1;
			}
		}
		if (index != MANCALA_1 && currIsPlayer1) {//player 1 to player 2
			currIsPlayer1 = !currIsPlayer1;//switch player turns
		} else if (index != MANCALA_2 && !currIsPlayer1) {// player 2 to player 1
			currIsPlayer1 = !currIsPlayer1;//switch player turns
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

	public boolean gameOver() {
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
	
	//always must be called with currIsPlayer1 = false
	public void aIMove(int movesAhead, List<Integer> moveSeq) {
		if (movesAhead == 0 || this.sumSide(false) == 0 || this.sumSide(true) == 0) {
			if (this.board[MANCALA_2] >= maxScore) {
				maxScore = this.board[MANCALA_2];
				bestMove = moveSeq.get(0);
			}
			return;
		}
		if (currIsPlayer1) { // player 1 move
			for (int i = 1; i <= 6; i++) {
				if (board[i] != 0) {
					Board boardCopy = this.clone();
					this.moveIndex(i);//choose
					moveSeq.add(i);
					this.aIMove(movesAhead-1,moveSeq);
					moveSeq.remove(moveSeq.size()-1);
					this.unchoose(boardCopy);//unchoose
				}
			}
		} else { // player 2 move
			for (int i = 8; i<=13; i++) {
				if (board[i] != 0) {
					Board boardCopy = this.clone();
					this.moveIndex(i);//choose
					moveSeq.add(i);
					this.aIMove(movesAhead-1,moveSeq);
					moveSeq.remove(moveSeq.size()-1);
					this.unchoose(boardCopy);//unchoose
				}
			}
		}
	}
	
	public void unchoose(Board boardCopy) {
		for (int i = 0; i < 14; i++) {
			this.board[i] = boardCopy.board[i];
		}
		this.currIsPlayer1 = boardCopy.currIsPlayer1;
	}

	public String toString() {
		String repr = "     "; // skips mancala
		for(int i = board.length -1; i > 0; i--) {
			if(i == MANCALA_1) {
				repr+="\n|" + String.format("%02d", board[MANCALA_2]) +"|";
				repr+= new String(new char[(i-1)*4]).replace("\0"," ");
				repr+=" |" + String.format("%02d", board[MANCALA_1]) +"|\n     ";
			} else if (i > 7){
				repr+=String.format("%02d", board[i])+"  ";
			}else {
				repr+=String.format("%02d", board[7-i])+"  ";
			}
		}
		return repr;
	}	
	
//this is what we want to clone
	public Board clone() {
		Board board = new Board();
		for (int i = 0; i < 14; i++) {
			board.board[i] = this.board[i];
		}
		board.currIsPlayer1 = this.currIsPlayer1;
		return board;
	}
}

