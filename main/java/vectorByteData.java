import java.io.Serializable;

public class vectorByteData implements Serializable {

    long pointer;
    int length;

    public vectorByteData(long pointer, int length){
        this.pointer = pointer;
        this.length = length;
    }
}
