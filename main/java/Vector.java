import java.io.Serializable;

public class Vector implements Comparable<Vector>, Serializable {

    private String word;
    private int freq;

    public Vector(String word){
        this.word = word;
        this.freq = 0;
    }

    public void update() {
        this.freq++;
    }

    public void setFreq(int freq){this.freq = freq;}

    public String getWord(){
        return this.word;
    }

    public String toString() {
        return String.format("<%s,%d>", word, freq);
    }

    public int compareTo(Vector that)  {
        return -1*Integer.compare(freq, that.freq);
    }

    public int getFreq() {
        return this.freq;
    }
}
