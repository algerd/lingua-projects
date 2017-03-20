
package ru.javafx.lingua.client.resources;

import java.util.Objects;

public class Word {
    
	private Integer id;
    private String word;
    private String transcription;
    private String translation;    
    //private Date created;
    
    public Word() {}
    
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

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.word);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Word other = (Word) obj;
        return Objects.equals(this.word, other.word);
    }

    @Override
    public String toString() {
        return "Word{" + "word=" + word + ", transcription=" + transcription + ", translation=" + translation + '}';
    }
    
}
