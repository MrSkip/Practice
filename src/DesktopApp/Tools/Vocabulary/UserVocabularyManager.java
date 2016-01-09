package DesktopApp.Tools.Vocabulary;

import DesktopApp.Tools.ReadLog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class UserVocabularyManager {
    private static Vector<AllVocabularies> allVocabularies = new Vector<>();

    public static void createVocabulary(String name){
        referencesStruct();
        int x = getIndexOfVocabularyName(name);

        if (x == -1) {
            allVocabularies.add(0, new AllVocabularies(name, new ArrayList<>()));
        }

        ReadLog.addUserDictionary(name);
    }

    public static boolean addVocabularyToRAM(String name){
        referencesStruct();
        int x = getIndexOfVocabularyName(name);
        if (x != -1)
            return false;

        allVocabularies.add(0, ReadLog.readUserDictionary(name));
        return true;
    }

    public static void addWordToVocabulary(String vocabularyName, Words words){
        int x = getIndexOfVocabularyName(vocabularyName);

        Collection<Words> collection = allVocabularies.get(x).getWordsCollection();
        collection.add(words);
        allVocabularies.get(x).setWordsCollection(collection);
    }

    public static void changeVocabularyName(String newName, String oldName){
        int x = getIndexOfVocabularyName(oldName);

        allVocabularies.get(x).setVocabularyName(newName);
    }

    public static void deleteVocabulary(String vocabularyName){
        int x = getIndexOfVocabularyName(vocabularyName);
        if (x != -1){
            allVocabularies.removeElement(x);
        }
    }

    public static void deleteAllVocabulary(){
        allVocabularies.clear();
    }

    public static Vector<AllVocabularies> getAllVocabularies(){
        return allVocabularies;
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
        if (ReadLog.getAllUserVocabulary() == null)
            ReadLog.setAllVocabularies(allVocabularies);
    }
}
