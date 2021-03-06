package DesktopApp.Tools.Vocabulary;

import DesktopApp.Tools.ReadLog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import java.util.stream.Collectors;

public class UserVocabularyManager {
    private static Vector<AllVocabularies> allVocabularies = null;

    public static void createVocabulary(String name){
        int x = getIndexOfVocabularyName(name);

        if (x == -1) {
            allVocabularies.add(0, new AllVocabularies(name, new ArrayList<>()));
        }
    }

    public static void changeNoteForDictionary(String vocabularyName, String newNote){
        int x = getIndexOfVocabularyName(vocabularyName);
        allVocabularies.get(x).setInfo(newNote);
    }

    public static Vector<String> getAllNamesOfUserDictionaries(){
        referencesStruct();
        return
                allVocabularies.stream().map(AllVocabularies::getVocabularyName).collect(Collectors.toCollection(Vector::new));
    }

    public static void addWordToVocabulary(String vocabularyName, Words words){
        int x = getIndexOfVocabularyName(vocabularyName);
        if (x == -1) return;

        boolean bl = true;
        for (Words words1 : allVocabularies.get(x).getWordsCollection()) {
            if (words1.getWord().equals(words.getWord())) {
                bl = false;
                break;
            }
        }
        System.out.println("Add - " + bl);
        if (bl){
            Collection<Words> collection = new ArrayList<>();
            collection.add(words);
            collection.addAll(allVocabularies.get(x).getWordsCollection());
            allVocabularies.get(x).setWordsCollection(collection);
        }
    }

    public static void removeWordFromVocabulary(String vocabularyName, Words words){
        int x = getIndexOfVocabularyName(vocabularyName);
        if (x == -1) return;
        Collection<Words> collection = allVocabularies.get(x).getWordsCollection();
        for (Words words1 : collection) {
            if (words1.equals(words)){
                collection.remove(words1);
                break;
            }
        }
        allVocabularies.get(x).setWordsCollection(collection);
    }

    public static void changeVocabularyName(String newName, String oldName){
        int x = getIndexOfVocabularyName(oldName);

        allVocabularies.get(x).setVocabularyName(newName);
    }

    public static void deleteVocabulary(String vocabularyName){
        int x = getIndexOfVocabularyName(vocabularyName);
        if (x != -1){
            allVocabularies.remove(x);
        }
    }

    public static void deleteAllVocabulary(){
        allVocabularies.clear();
    }

    public static String getNote(String vocabularyName){
        int x = getIndexOfVocabularyName(vocabularyName);
        if (x == -1)
            return "";
        return allVocabularies.get(x).getInfo();
    }

    public static Vector<AllVocabularies> getAllVocabularies(){
        return allVocabularies;
    }

    public static void move(String vocabularyName){
        int x = getIndexOfVocabularyName(vocabularyName);
        if (x == -1)
            return;

        allVocabularies.add(0, allVocabularies.get(x));
        allVocabularies.remove(x + 1);
    }

    public static Collection<Words> getVocabulary(String vocabularyName){
        int x = getIndexOfVocabularyName(vocabularyName);
        if (x == -1)
            return new ArrayList<>();

        return allVocabularies.get(x).getWordsCollection();
    }

    private static int getIndexOfVocabularyName(String name){
        int x = -1;
        for (int i = 0; i < allVocabularies.size(); i++) {
            if (allVocabularies.get(i).getVocabularyName().equals(name)) {
                x = i;
                break;
            }
        }
        return x;
    }

    private static void referencesStruct(){
        if (allVocabularies == null) {
            allVocabularies = ReadLog.getUserDictionaries();
        }
    }
}
