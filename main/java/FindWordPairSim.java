import java.io.*;
import java.util.Scanner;

public class FindWordPairSim {




    public static void main(String[] args) {

        if(args.length < 2 || args.length > 2){
            System.out.println("pass arguments in format java FindWordPairSim <word1> <word2>");
            System.exit(1);
        }

        String word1 = args[0];
        String word2 = args[1];

        // get map of pointers and length from secondary storage
        WordPointerMap pointerMap = getPointerMap();

        // get pointers and length from serialized map
        vectorByteData pointer1 = pointerMap.map.get(word1);
        vectorByteData pointer2 = pointerMap.map.get(word2);

        // get vectors for provided words using pointers and length from RandomAccessFile
        WordSparseVector vector1 = readFromRAF(pointer1);
        WordSparseVector vector2 = readFromRAF(pointer2);


        // Print the similarity between the vectors
        System.out.println(vector1.sim(vector2));


        }

    public static WordSparseVector readFromRAF(vectorByteData data){
        byte[] serializedVector = new byte[data.length];

        RandomAccessFile file = null;
        ObjectInput in = null;
        ByteArrayInputStream bis = null;
        WordSparseVector vector = null;

        try {
            file = new RandomAccessFile("../../src/data/vectors.ser", "r");
            file.seek(data.pointer);

            int num = file.read(serializedVector, 0, data.length);

            bis = new ByteArrayInputStream(serializedVector);
            in = new ObjectInputStream(bis);

            vector = (WordSparseVector) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return vector;
    }

    public static WordPointerMap getPointerMap(){
        WordPointerMap wordPointerMap = null;
        InputStream fileIn = null;
        ObjectInputStream in = null;
        try {
            fileIn = new FileInputStream("../../src/data/wordPointerMap.ser");
            in = new ObjectInputStream(fileIn);
            wordPointerMap = (WordPointerMap) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try{
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return wordPointerMap;
    }

    }

