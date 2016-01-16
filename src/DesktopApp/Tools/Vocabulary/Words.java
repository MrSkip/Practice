package DesktopApp.Tools.Vocabulary;

public class Words {
    private String word = null,
            transcription = null,
            translate = null,
            info = null,
            note = null;

    public Words(String word, String transcription, String translate, String info, String note){
        this.word = word;
        this.transcription = transcription;
        this.translate = translate;
        this.info = info;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
