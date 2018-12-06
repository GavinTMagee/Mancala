public class Board {
	public int[] board;
	public int playerScore;
	public int AIScore;
	public boolean gameOver;
	
	public Board() {
		this.board = new int[14];
		for (int i = 0; i < 14; i++) {
			board[i] = 4;
		}
		board[0] = 0;
		board[7] = 0;
		this.gameOver = false;
		this.playerScore = 0;
		this.AIScore = 0;
		
	}
	
	public void moveIndex(int index){
		int pieces = board[index];
		board[index] = 0;
		for (int i = 0; i < pieces; i++) {
			board[(index + i) % 14] += 1;
		}
		boolean gameOver = true;
		for (int i = 1; i < 7; i ++) {
			if (board[i] != 0) {
				gameOver = false;
			}
		}
		if (gameOver) {// AI no more moves
			for (int i = 8; i < 14; i++) {
				board[0] += board[i];
				board[i] = 0;
			}
			this.gameOver = true;
		}
		gameOver = true;
		for (int i = 8; i < 14; i++) {//player no more moves
			if (board[i] != 0) {
				gameOver = false;
			}
		}
		if (gameOver) {
			for (int i = 1; i < 7; i++) {
				board[7] += board[i];
				board[i] = 0;
			}
			this.gameOver = true;
		}
		this.playerScore = board[0];
		this.AIScore = board[7];
	}
}
