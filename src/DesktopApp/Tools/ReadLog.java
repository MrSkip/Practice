package DesktopApp.Tools;

import DesktopApp.Tools.Vocabulary.Manager;
import DesktopApp.Tools.Vocabulary.Words;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
// ???? If the program use first - write to file path to Vocabulary
// This class must return different data from log.file
public class ReadLog {

    public static void main(String [] args){
        Manager manager = new Manager();
        manager.setListWords(readVocabulary("uk-en"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                System.out.println(manager.getWord(br.readLine()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method get the name of Vocabulary and than return all words from this Vocabulary
    public static Collection<Words> readVocabulary(String vocabularyName){
        Vector<String> vocabularyPath = getVocabularyPath();
        String languagePath = null;
        Manager manager = new Manager();

        // Get the path of selected Vocabulary
        for (String path : vocabularyPath) {
            String name = path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf(".txt"));
            if (name.equalsIgnoreCase(vocabularyName)) {
                languagePath = path;
                break;
            }
        }
        try {
            assert languagePath != null;
            FileInputStream fstream = new FileInputStream(languagePath);

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));

            String str;

            // Read words from Ukraine Vocabulary
            if (br.readLine().equals("UKRAINE-ENGLISH"))
                while ((str = br.readLine()) != null){
                    manager.add(str.substring(0, str.indexOf(" =")),
                                str.substring(str.indexOf("= ") + 2, str.length()));
                }
            // Read words from another Vocabulary
            else
                while ((str = br.readLine()) != null){
                    manager.add(str.substring(0, str.indexOf(" =")),
                                str.substring(str.indexOf(" [") + 2, str.indexOf("] =")),
                                str.substring(str.indexOf("] =") + 4, str.length()),
                                null, null);
                }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manager.getListWords();
    }

    // Method read path to vocabulary from log.txt and return path in Vector
    public static Vector<String> getVocabularyPath(){
        Vector<String> vector = new Vector<>();
        String pathToLog = System.getProperty("user.dir") +
                "\\src\\DesktopApp\\Resource\\log.txt";
        try {
            FileInputStream fstream = new FileInputStream(pathToLog);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
            String str;

            while ((str = br.readLine()) != null){
                if (str.equals("~DICTIONARY->")){
                    while (!(str = br.readLine()).equals("")) {
                        vector.add(str);
                    }
                    break;
                }
            }

            br.close();
        } catch (Exception e){
            System.out.println("Error:\n" + e);
        }
        return vector;
    }
}
