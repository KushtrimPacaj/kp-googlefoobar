import java.util.ArrayList;
import java.util.List;

/**
 * Created by kushtrimpacaj on 14/01/2017.
 */
public class Challenge1 {

    public static void main(String[] args) {
        answer(12);
    }

    private static int[] answer(int area) {
        List<Integer> areasValues = new ArrayList<Integer>();

        while (area > 0) {
            int biggestSquare = (int) Math.sqrt(area);

            int biggestSquareArea = biggestSquare * biggestSquare;
            area = area - biggestSquareArea;
            areasValues.add(biggestSquareArea);
        }

        return collectionToArray(areasValues);
    }

    private static int[] collectionToArray(List<Integer> areasValues) {
        int[] answer = new int[areasValues.size()];

        for (int i = 0; i < areasValues.size(); i++) {
            answer[i] = areasValues.get(i);
        }
        return answer;
    }

}
