
package ru.javafx.lingua.client.controller.words;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Word implements IdAware {

    private int id;
    private final StringProperty word = new SimpleStringProperty("");
    private final StringProperty transcription = new SimpleStringProperty("");
    private final StringProperty translation = new SimpleStringProperty("");
    
    public Word() {}

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }      
    
    public String getWord() {
        return word.get();
    }
    public void setWord(String value) {
        word.set(value);
    }
    public StringProperty wordProperty() {
        return word;
    }
    
    public String getTranscription() {
        return transcription.get();
    }
    public void setTranscription(String value) {
        transcription.set(value);
    }
    public StringProperty transcriptionProperty() {
        return transcription;
    }
    
    public String getTranslation() {
        return translation.get();
    }
    public void setTranslation(String value) {
        translation.set(value);
    }
    public StringProperty translationProperty() {
        return translation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.word);
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
        return "Word{" + "id=" + id + ", word=" + word + ", transcription=" + transcription + ", translation=" + translation + '}';
    }
   
}
