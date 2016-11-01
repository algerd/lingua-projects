
package ru.javafx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import ru.javafx.utils.JsonDateSerializer;

@Entity
@Table(name="word")
public class Word implements Serializable {
    
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
    
    @NotNull(message = "error.word.word.empty")
    @Size(max = 255, message = "error.word.word.size")
    private String word;
    
    @Size(max = 255, message = "error.word.transcription.size")
    private String transcription;
      
    @NotNull(message = "error.word.translation.empty")
    @Size(max = 1000, message = "error.word.translation.size")
    private String translation;
    
    private Date created;
    
    public Word() {}
    
    @Transient
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @JsonSerialize(using=JsonDateSerializer.class) 
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    @JsonIgnore
	public String getCreatedAsShort(){
		return format.format(created);
	}

    @Override
    public String toString() {
        return "Word{" 
            + "id=" + id 
            + ", word=" + word 
            + ", transcription=" + transcription 
            + ", translation=" + translation 
            + ", created=" + getCreatedAsShort() 
            + '}';
    }
    
}
