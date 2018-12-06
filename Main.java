import java.util.ArrayList;
import java.util.List;

public class Main {
	public static final int DIFFICULTY = 6; // increased number makes it harder to win.
	
	public static void main(String[] args) {
		List<List<Integer>> allMoveSets = new ArrayList<>();
		generatePossMoveSets(new ArrayList<Integer>(), allMoveSets,false);
		evalBestMoveSet(allMoveSets);
	}

	public static void generatePossMoveSets(List<Integer> possMoveSet, List<List<Integer>> allMoveSets, boolean playerTurn) {
		if (possMoveSet.size() == DIFFICULTY) {
			List<Integer> possMoveSetCopy = new ArrayList<>();
			for (int num : possMoveSet) {
				possMoveSetCopy.add(num);
			}
			allMoveSets.add(possMoveSetCopy);
		} else if (playerTurn){
			for (int i = 8; i < 14; i++) {
				possMoveSet.add(i);
				generatePossMoveSets(possMoveSet, allMoveSets,false);
				possMoveSet.remove(possMoveSet.size()-1);
			}
		} else {
			for (int i = 1; i < 7; i++) {
				possMoveSet.add(i);
				generatePossMoveSets(possMoveSet,allMoveSets,true);
				possMoveSet.remove(possMoveSet.size()-1);
			}
		}
	}
	
	public static void evalBestMoveSet(List<List<Integer>> allMoveSets) {
		for (List<Integer> moveSet : allMoveSets) {
			
		}
	}
}
