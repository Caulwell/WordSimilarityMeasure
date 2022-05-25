import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordContainer {

    Map<String,WordSparseVector> map;

    public WordContainer(){
        map = new TreeMap<>();
    }

    public void update(String word, String context){
        WordSparseVector x = map.get(word);
        // if word not in map, create a new sparse vector
        if(x == null){
            x = new WordSparseVector(word);
            // add new key for word, with sparse vector as the value
            map.put(word, x);
        }
        x.update(context);
    }

    public String getMostFrequentContextWords(String word){
        String s = "";
        WordSparseVector wordSparseVector = map.get(word);

        List<Vector> top3 = wordSparseVector.top(3);

        for(Vector v : top3){
            s+= v.toString();
        }
        return s;
    }

    public void saveVectors(WordPointerMap pointerMap){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        RandomAccessFile file = null;

        try{
            file = new RandomAccessFile("../../src/data/vectors.ser", "rw");

            // for each sparse vector
            for(WordSparseVector w : map.values()){

                // write object to byte array stream
                bos = new ByteArrayOutputStream();
                out = new ObjectOutputStream(bos);
                out.writeObject(w);
                out.flush();

                // convert object to byte array
                byte[] bytes = bos.toByteArray();

                // add randomAccessFile data to pointerMap for that word
                pointerMap.update(w.word, new vectorByteData(file.getFilePointer(), bytes.length));

                // write to random access file
                file.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                bos.close();
                file.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
