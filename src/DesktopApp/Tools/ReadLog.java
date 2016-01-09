package DesktopApp.Tools;

import DesktopApp.Tools.Vocabulary.AllVocabularies;
import DesktopApp.Tools.Vocabulary.Words;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import java.util.stream.Collectors;

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
            studyUserSites = new Vector<>(),
            pathToUserDictionary = new Vector<>();
    private static Vector<AllVocabularies> allUserVocabulary = null;

    public static void main(String[] args) {
//        Manager manager = new Manager();
//        manager.setListWords(readVocabulary("uk-en"));
//        manager.setListWords(readVocabulary("en-uk"));
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        while (true){
//            try {
//                System.out.println(manager.getWord(br.readLine()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static Vector<AllVocabularies> getAllVocabularies() {
        // if the method is not call than call him
        if (!IfRead) readLog();

        Vector<AllVocabularies> allVocabularies = new Vector<>();

        for (String path : vocabularyPath) {
            String name = path.substring(path.lastIndexOf("\\") + 1, path.lastIndexOf(".txt"));
            allVocabularies.add(new AllVocabularies(name, readVocabulary(name)));
        }

        return allVocabularies;
    }

    // Method get the name of Vocabulary and than return all words from this Vocabulary
    public static Collection<Words> readVocabulary(String vocabularyName) {
        // if the method is not call than call him
        if (!IfRead) readLog();
        String languagePath = null;
        ReadVocabulary readVocabulary = new ReadVocabulary();

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
            if (str1.equalsIgnoreCase("Ukraine-English"))
                while ((str = br.readLine()) != null) {
                    readVocabulary.add(str.substring(0, str.indexOf(" =")),
                            str.substring(str.indexOf("= ") + 2, str.length()));
                }
                // Read words from another Vocabulary
            else
                while ((str = br.readLine()) != null) {
                    readVocabulary.add(str.substring(0, str.indexOf(" =")),
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
        return readVocabulary.getListWords();
    }

     /*
     * Method read all data from logFile into (
     * vocabularyPath - paths for all vocabulary;
     * studyLog - links to sites with path of image;
     * studyRecentLog - links of last open site - max three;
     * studyUserSites - links of user sites )
     */

    public static void readLog() {
        IfRead = true;
        String pathToLog = System.getProperty("user.dir") +
                "\\src\\DesktopApp\\Resource\\log.txt";

        if (!vocabularyPath.isEmpty()) vocabularyPath.clear();
        if (!studyLog.isEmpty()) studyLog.clear();
        if (!studyRecentLog.isEmpty()) studyRecentLog.clear();
        if (!studyUserSites.isEmpty()) studyUserSites.clear();
        if (!pathToUserDictionary.isEmpty()) pathToUserDictionary.clear();

        try {
            FileInputStream fstream = new FileInputStream(pathToLog);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
            String str;

            while ((str = br.readLine()) != null) {
                switch (str) {
                    case "~START->":
                        str = br.readLine();
                        COUNT_OF_USE = Integer.parseInt(str.substring(str.indexOf("-") + 1, str.length()));
                        str = br.readLine();
                        LANGUAGE = str.substring(str.indexOf("-") + 1, str.length());
                        break;
                    case "~DICTIONARY->":
                        while (!(str = br.readLine()).equals("")) {
                            vocabularyPath.add(str);
                        }
                        break;
                    case "~STUDY->":
                        while (!(str = br.readLine()).equals("-USER SITES")) {
                            studyLog.add(str);
                        }
                        while (!(str = br.readLine()).equals("-RECENT")) {
                            studyUserSites.add(str);
                        }
                        while (!(str = br.readLine()).equals("~END STUDY<-")) {
                            studyRecentLog.add(str);
                        }
                        break;
                    case "~USER DICTIONARY":
                        while (!(str = br.readLine()).equals("~END USER DICTIONARY<-")) {
                            pathToUserDictionary.add(str);
                        }
                        break;
                }
            }
            if (COUNT_OF_USE == 0) {
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
        } catch (Exception e) {
            System.out.println("Error:\n" + e);
        }
    }

    public static boolean writeLog() {
        if (!IfRead) return false;

        String pathToLog = System.getProperty("user.dir") +
                "\\src\\DesktopApp\\Resource\\log.txt";
        FileWriter fw;
        try {
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

            fw.write("\n~USER DICTIONARY");
            for (String aPathToUserDictionary : pathToUserDictionary) {
                fw.write("\n" + aPathToUserDictionary);
            }
            fw.write("\n~END USER DICTIONARY<-");

            fw.close();

            if (allUserVocabulary != null){
                for (int i = 0; i < allUserVocabulary.size(); i++) {
                    String pathToDictionary = System.getProperty("user.dir") +
                            "\\src\\DesktopApp\\Resource\\UserDictionaries\\" + allUserVocabulary.get(i).getVocabularyName() + ".txt";
                    File file = new File(pathToDictionary);
                    try{

                        if (file.createNewFile())
                            System.out.println("File created");
                        else
                            System.out.println("File already exist");

                    }catch (IOException e){
                        System.out.println("Error:\n" + e);
                    }

                    fw = new FileWriter(pathToLog, false);
                    fw.write("//" + allUserVocabulary.get(i).getInfo());
                    for (Words words : allUserVocabulary.get(i).getWordsCollection()) {
                        fw.write("\n" + words.getInfo().substring(0, words.getInfo().indexOf(":")));
                        fw.write(words.getWord() + " = [");
                        fw.write(words.getTranscription() + "] = ");
                        fw.write(words.getTranslate() + " ||> ");
                        fw.write(words.getInfo() + " ||< ");
                        fw.write(words.getNote().substring(words.getNote().indexOf(":") + 1));
                    }
                }
            }

        } catch (Exception e) {
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

    public static Vector<String> getUserDictionaries() {
        if (!IfRead) readLog();
        return
                pathToUserDictionary.stream().map(aPathToUserDictionary ->
                        aPathToUserDictionary.substring(aPathToUserDictionary.lastIndexOf("\\") + 1)).collect(Collectors.toCollection(Vector::new));
    }

    public static void addUserDictionary(String name) {
        String pathToDictionary = System.getProperty("user.dir") +
                "\\src\\DesktopApp\\Resource\\UserDictionaries\\" + name + ".txt";

        pathToUserDictionary.removeElement(pathToDictionary);
        pathToUserDictionary.add(0, pathToDictionary);
    }



    public static AllVocabularies readUserDictionary(String name) {
        String pathToDictionary = System.getProperty("user.dir") +
                "\\src\\DesktopApp\\Resource\\UserDictionaries\\" + name + ".txt";
        boolean bl = false;
        Collection<Words> collection = new ArrayList<>();
        AllVocabularies allVocabularies = null;
        for (String vector :
                getUserDictionaries()) {
            if (vector.trim().equals(name.trim()))
                bl = true;
        }
        if (!bl) return null;
        try {
            FileInputStream fstream = new FileInputStream(pathToDictionary);
            System.out.println(pathToDictionary);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));

            String str;
            String info = br.readLine();

            if (info.length() > 2)
                info = info.substring(2);
            else info = "";

            // Read words from Ukraine Vocabulary
            while ((str = br.readLine()) != null) {
                collection.add(
                        new Words(
                                str.substring(str.indexOf(':') + 1, str.indexOf(" =")),
                                str.substring(str.indexOf(" [") + 2, str.indexOf("] =")),
                                str.substring(str.indexOf("] =") + 4, str.indexOf("||>") - 1),
                                str.substring(0, str.indexOf(':') + 1) + str.substring(str.indexOf("||>") + 4, str.indexOf("||<") - 1),
                                str.substring(str.indexOf("||<") + 4)
                        )
                );
            }
            allVocabularies = new AllVocabularies(name, collection, info);
            br.close();
            in.close();
            fstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allVocabularies;
    }

    public static void setAllVocabularies(Vector<AllVocabularies> rAllVocabularies){
        allUserVocabulary = rAllVocabularies;
    }

    public static Vector<AllVocabularies> getAllUserVocabulary(){
        return allUserVocabulary;
    }
}

// Class that can save vocabulary
class ReadVocabulary {
    private Collection<Words> listWords = null;

    public ReadVocabulary(){
        listWords = new ArrayList<>();
    }

    public void add(String word, String transcription, String translate, String info, String note){
        listWords.add(new Words(word, transcription, translate, info, note));
    }

    public void add(String word, String translate){
        listWords.add(new Words(word, null, translate, null, null));
    }

    public Collection<Words> getListWords() {
        return listWords;
    }

    public void setListWords(Collection<Words> listWords) {
        this.listWords = listWords;
    }
}

