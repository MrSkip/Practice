package DesktopApp.Tools;

import DesktopApp.Tools.Vocabulary.Manager;
import DesktopApp.Tools.Vocabulary.Words;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Vector;

// ???? If the program use first - write to file path to Vocabulary
// This class must return different data from log.file
public class ReadLog {
    // if the method readLog is call then IfRead = true;
    private static boolean IfRead = false;
    private static int COUNT_OF_USE;
    private static String LANGUAGE;
    private static Vector<String>
            vocabularyPath = new Vector<>(),
            studyLog = new Vector<>(),
            studyRecentLog = new Vector<>(),
            studyUserSites = new Vector<>();

    public static void main(String [] args){
        Manager manager = new Manager();
//        manager.setListWords(readVocabulary("uk-en"));
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
        // if the method is not call than call him
        if (!IfRead) readLog();

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
            String str1 = br.readLine();
            System.out.println(str1);
            if (str1.equals("UKRAINE-ENGLISH"))
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
            in.close();
            fstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manager.getListWords();
    }

     /*
     * Method read all data from logFile into (
     * vocabularyPath - paths for all vocabulary;
     * studyLog - links to sites with path of image;
     * studyRecentLog - links of last open site - max three;
     * studyUserSites - links of user sites )
     */

    public static void readLog(){
        IfRead = true;
        String pathToLog = System.getProperty("user.dir") +
                "\\src\\DesktopApp\\Resource\\log.txt";

        if (!vocabularyPath.isEmpty()) vocabularyPath.clear();
        if (!studyLog.isEmpty()) studyLog.clear();
        if (!studyRecentLog.isEmpty()) studyRecentLog.clear();
        if (!studyUserSites.isEmpty()) studyUserSites.clear();

        try {
            FileInputStream fstream = new FileInputStream(pathToLog);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
            String str;

            while ((str = br.readLine()) != null){
                if (str.equals("~START->")){
                    str = br.readLine();
                    COUNT_OF_USE = Integer.parseInt(str.substring(str.indexOf("-") + 1, str.length()));
                    str = br.readLine();
                    LANGUAGE = str.substring(str.indexOf("-") + 1, str.length());
                }
                if (str.equals("~DICTIONARY->")){
                    while (!(str = br.readLine()).equals("")) {
                        vocabularyPath.add(str);
                    }
                }
                if (str.equals("~STUDY->")){
                    while (!(str = br.readLine()).equals("-USER SITES")) {
                        studyLog.add(str);
                    }
                    while (!(str = br.readLine()).equals("-RECENT")) {
                        studyUserSites.add(str);
                    }
                    while (!(str = br.readLine()).equals("~END STUDY<-")) {
                        studyRecentLog.add(str);
                    }
                }
            }
            if (COUNT_OF_USE == 0){
                File folder = new File(System.getProperty("user.dir") +
                        "\\src\\DesktopApp\\Resource\\Dictionaries");
                File[] listOfFiles = folder.listFiles();
                vocabularyPath.clear();
                if (listOfFiles != null) {
                    for (File listOfFile : listOfFiles) {
                        vocabularyPath.add(listOfFile.getPath());
                    }
                }
            }

            br.close();
            in.close();
            fstream.close();
        } catch (Exception e){
            System.out.println("Error:\n" + e);
        }
    }

    public static boolean writeLog(){
        if (!IfRead) return false;

        String pathToLog = System.getProperty("user.dir") +
                "\\src\\DesktopApp\\Resource\\log.txt";
        FileWriter fw;
        try{
            fw = new FileWriter(pathToLog, false);

            fw.write("~START->");
            fw.write("\ncount of use-" + COUNT_OF_USE +
                    "\nlanguage-" + LANGUAGE.toUpperCase() + "\n");

            fw.write("\n~DICTIONARY->");
            for (String aVocabularyPath : vocabularyPath) {
                fw.write("\n" + aVocabularyPath);
            }
            fw.write("\n");

            fw.write("\n~STUDY->");
            for (String aVocabularyPath : studyLog) {
                fw.write("\n" + aVocabularyPath);
            }
            fw.write("\n-USER SITES");
            for (String aVocabularyPath : studyUserSites) {
                fw.write("\n" + aVocabularyPath);
            }
            fw.write("\n-RECENT");
            for (String aVocabularyPath : studyRecentLog) {
                fw.write("\n" + aVocabularyPath);
            }
            fw.write("\n~END STUDY<-");

            fw.close();
        } catch (Exception e){
            System.out.println("Error: \n" + e);
            return false;
        }
        return false;
    }

    public static Vector<String> getVocabularyPath() {
        // if the method is not call than call him
        if (!IfRead) readLog();

        return vocabularyPath;
    }

    public static Vector<String> getStudyLog() {
        // if the method is not call than call him
        if (!IfRead) readLog();

        return studyLog;
    }

    public static void setCountOfUse(int countOfUse) {
        COUNT_OF_USE = countOfUse;
    }

    public static void setLANGUAGE(String LANGUAGE) {
        ReadLog.LANGUAGE = LANGUAGE;
    }

    public static void setVocabularyPath(Vector<String> vocabularyPath) {
        ReadLog.vocabularyPath = vocabularyPath;
    }

    public static void setStudyLog(Vector<String> studyLog) {
        ReadLog.studyLog = studyLog;
    }

    public static void setStudyRecentLog(Vector<String> studyRecentLog) {
        ReadLog.studyRecentLog = studyRecentLog;
    }

    public static void setStudyUserSites(Vector<String> studyUserSites) {
        ReadLog.studyUserSites = studyUserSites;
    }

    public static Vector<String> getStudyRecentLog() {
        // if the method is not call than call him
        if (!IfRead) readLog();

        return studyRecentLog;
    }

    public static Vector<String> getStudyUserSites() {
        // if the method is not call than call him
        if (!IfRead) readLog();

        return studyUserSites;
    }

    public static int getCountOfUse() {
        // if the method is not call than call him
        if (!IfRead) readLog();

        return COUNT_OF_USE;
    }

    public static String getLANGUAGE() {
        // if the method is not call than call him
        if (!IfRead) readLog();

        return LANGUAGE;
    }
}
