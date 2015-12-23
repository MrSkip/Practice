package DesktopApp.Tools.Vocabulary;

import java.util.Collection;

public class AllVocabularies{
    private String VocabluaryName;
    private Collection<Words> wordsCollection;

    public AllVocabularies(String vocabluaryName, Collection<Words> wordsCollection){
        this.VocabluaryName = vocabluaryName;
        this.wordsCollection = wordsCollection;
    }

    public String getVocabularyName() {
        return VocabluaryName.toLowerCase().trim();
    }

    public Collection<Words> getWordsCollection() {
        return wordsCollection;
    }
}
