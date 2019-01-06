import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static final int DIFFICULTY = -1; // increased number makes it harder to win., -1 is hardest
	//false = player 2 turn
	//true = player 1 turn
	
	public static void main(String[] args) {
		Board board = new Board();
		System.out.println(board);
		Scanner input = new Scanner(System.in);
		while (!board.gameOver()) {
			while (board.currIsPlayer1) {//player 1 turn
				System.out.print("What is your move: ");
				int moveAt = Integer.parseInt(input.nextLine());
				System.out.println();
				board.moveIndex(moveAt);
				System.out.println(board);
				System.out.println("==========================\n");
			}while (!board.currIsPlayer1) {//player 2 turn
				board.miniMax(DIFFICULTY, board,true);
				board.moveIndex(board.bestMove);
				System.out.println("AI MOVE");
				System.out.println(board);
				System.out.println("==========================\n");
				board.bestMove = -1;
			}
		}
	}
}
