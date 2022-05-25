import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WordPointerMap implements Serializable {

    Map<String, vectorByteData> map;

    public WordPointerMap(){
        map = new HashMap<>();
    }

    public void update(String word, vectorByteData data){
        map.put(word, data);
    }

}
