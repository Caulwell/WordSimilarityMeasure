
import java.io.Serializable;
import java.util.*;

public class WordSparseVector implements Serializable {
    String word;
    Map<String, Vector> map;
    float sim;

    public WordSparseVector(String word){

        this.word = word;
        this.map = new HashMap<>();
    }

    public void update(String context){
        if(context.equals(word)) return;

        Vector x = map.get(context);
        // if the hashmap of this base word does not have a vector for
        // this context word, add an entry for this base word's hashmap
        if(x == null){
            x = new Vector(context);
            map.put(x.getWord(), x);
        }
        // otherwise, add frequency to this context word
        x.update();
    }

    public void populate(String word, int freq){

        Vector x = new Vector(word);
        x.setFreq(freq);
        map.put(x.getWord(), x);
    }

    List<Vector> top(int k) {
        List<Vector> values = new ArrayList<>(map.values());
        Collections.sort(values);
        return values.subList(0, k);
    }


    public String toString(){
        String s = "";
//        String s = this.roll_number + " " + this.word;
        for(Vector vec : this.map.values()){
            s+= vec.toString();
        }
        return s;
    }

    float norm() {
        float norm = 0;
        for (Vector x: map.values()) {
            norm += x.getFreq() *x.getFreq();
        }
        return (float)Math.sqrt(norm);
    }

    float sim(WordSparseVector that) {
        float sim = 0;
        for (String w: this.map.keySet()) {
            // check if w matches with the other sparsevec
            Vector that_w = that.map.get(w);
            if (that_w == null)
                continue;
            sim += this.map.get(w).getFreq() * that_w.getFreq();
        }
        return sim/(this.norm() * that.norm());
    }

}
