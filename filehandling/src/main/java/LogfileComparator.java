import java.util.Comparator;
import java.util.Map;

public class LogfileComparator implements Comparator<Map.Entry<String,Integer>> {
    public int compare(Map.Entry<String,Integer> a,Map.Entry<String,Integer>b){
        return b.getValue().compareTo(a.getValue());
    }
}
