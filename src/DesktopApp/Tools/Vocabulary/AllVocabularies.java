package DesktopApp.Tools.Vocabulary;

import java.util.Collection;

public class AllVocabularies{
    private String vocabularyName;
    private Collection<Words> wordsCollection;
    private String info;

    public AllVocabularies(String vocabularyName, Collection<Words> wordsCollection){
        this.vocabularyName = vocabularyName;
        this.wordsCollection = wordsCollection;
    }

    public AllVocabularies(String vocabularyName, Collection<Words> wordsCollection, String info){
        this.vocabularyName = vocabularyName;
        this.wordsCollection = wordsCollection;
        this.info = info;
    }

    public String getVocabularyName() {
        return vocabularyName.trim();
    }

    public Collection<Words> getWordsCollection() {
        return wordsCollection;
    }

    public void setWordsCollection(Collection<Words> wordsCollection){
        this.wordsCollection = wordsCollection;
    }

    public void setVocabularyName(String vocabularyName){
        this.vocabularyName = vocabularyName;
    }

    public String getInfo(){
        return info;
    }

    public void setInfo(String newInfo){
        info = newInfo;
    }
}
