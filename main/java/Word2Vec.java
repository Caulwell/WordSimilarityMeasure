import java.io.*;
import java.util.*;

public class Word2Vec {

    public static void main(String[] args) {

        WordPointerMap pointerMap = new WordPointerMap();

        WordContainer wordContainer = new WordContainer();

        // get list of stop words from document
        Set<String> stopWords = getStopWords();

        // get list of words from document
        ArrayList<String> words = readDoc();

        // remove all stop words from list of words
        words.removeAll(stopWords);

        // Create sparse vectors from list of words
        getVectors(wordContainer, words);

        // save sparse vectors to Random Access File
        wordContainer.saveVectors(pointerMap);

        // Save pointer map
        serializePointers(pointerMap);

        // Q10 - Get most frequent context words for the word "Venice"
        System.out.println("Most frequent context words for venice: " +
                wordContainer.getMostFrequentContextWords("venice"));


    }

    public static void serializePointers(WordPointerMap wordPointerMap){
        ObjectOutputStream out = null;
        OutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("../../src/data/wordPointerMap.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(wordPointerMap);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getVectors(WordContainer wordContainer, ArrayList<String> words){
        for(int i = 0; i < words.size(); i++){
            String word = words.get(i);
            for(int j = i-5; j <=i+5; j++){
                if(j > 0 && j < words.size()){
                    wordContainer.update(word, words.get(j));
                }
            }
        }
    }

    public static ArrayList<String> readDoc() {
        String path = "../../src/data/shakespeare.txt";
        File f = new File(path);
        String text = "";
        int read, N = 1024 * 1024;
        char[] buffer = new char[N];

        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            while(true) {
                read = br.read(buffer, 0, N);
                text += new String(buffer, 0, read)
                        .toLowerCase();

                if(read < N) {
                    break;
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        List<String> arrayOfWords = Arrays.asList(text.split("[^\\p{Alpha}']+"));
        ArrayList<String> words = new ArrayList<>(arrayOfWords);
        return words;
    }

    public static Set<String> getStopWords(){
        String path = "../../src/data/stop.txt";
        Set<String> stopWords = new HashSet<>();

        try(FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr)){
            String line;

            while((line = br.readLine()) != null){
                String[] stopWordArray = line.split(" ");
                stopWords.addAll(Arrays.asList(stopWordArray));
            }
        }

        catch(Exception ex) {ex.printStackTrace();}

        return stopWords;
    }

}