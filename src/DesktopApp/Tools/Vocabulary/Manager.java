package DesktopApp.Tools.Vocabulary;

import DesktopApp.Tools.ReadLog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class Manager {
    private Collection<Words> listWords = null;
    Vector<AllVocabularies> allVocabularies = null;

    public Manager(){
        listWords = new ArrayList<>();
        Vector<AllVocabularies> allVocabularies = ReadLog.getAllVocabularies();
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

    // Method return translation of `typeWord`
    public Vector<String> getWord(String vocabularyName, String typeWord){

        if (getTypedVocabulary(vocabularyName) == null){
            System.out.println("Don`t have vocabulary of name - " + vocabularyName);
            return null;
        }

        Iterator<Words> iterator = getTypedVocabulary(vocabularyName).iterator();
        Vector<String> vector = new Vector<>();
        while (iterator.hasNext()){
            Words word = iterator.next();
            if (word.getWord().equalsIgnoreCase(typeWord)){
                vector.add(word.getTranslate());
                while (iterator.hasNext()){
                    word = iterator.next();
                    if (word.getWord().equalsIgnoreCase(typeWord))
                        vector.add(word.getTranslate());
                    else break;
                }
                break;
            }
        }
        return vector;
    }

    // Method return words that start with typeWord
    public Vector<String> getTypedWord(String vocabularyName, String typeWord){

        if (getTypedVocabulary(vocabularyName) == null){
            System.out.println("Don`t have vocabulary of name - " + vocabularyName);
            return null;
        }

        Iterator<Words> iterator = getTypedVocabulary(vocabularyName).iterator();
        Vector<String> vector = new Vector<>();
        while (iterator.hasNext()){
            Words word = iterator.next();
            if (word.getWord().startsWith(typeWord)){
                vector.add(word.getTranslate());
                while (iterator.hasNext()){
                    word = iterator.next();
                    if (word.getWord().startsWith(typeWord))
                        vector.add(word.getTranslate());
                    else break;
                }
                break;
            }
        }
        return vector;
    }

    private Collection<Words> getTypedVocabulary(String name){

        if (allVocabularies == null)
            allVocabularies = ReadLog.getAllVocabularies();

        for (AllVocabularies allVocabulary : allVocabularies) {
            if (allVocabulary.getVocabularyName().equalsIgnoreCase(name.trim()))
                return allVocabulary.getWordsCollection();
        }
        return null;
    }
}
