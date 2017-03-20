
package ru.javafx.lingua.client.controller.words;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.HttpHeaders;
import ru.javafx.lingua.client.SessionManager;
import ru.javafx.lingua.client.jfxintegrity.BaseFxmlController;
import ru.javafx.lingua.client.jfxintegrity.FXMLController;
import ru.javafx.lingua.client.service.RequestService;

@FXMLController(title = "Words")
@Scope("prototype")
public class WordsController extends BaseFxmlController {
    
    @Autowired
    private RequestService requestService;
        
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private List<Word> wordList;
    private static final String REL_PATH = "words";
    
    @FXML
    private TableView<Word> wordsTable;
    @FXML
    private TableColumn<Word, String> wordColumn;
    @FXML
    private TableColumn<Word, String> transcriptionColumn;
    @FXML
    private TableColumn<Word, String> translationColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initWordsTable();
        setTableValue();
    }
    
    private void initWordsTable() {
        wordColumn.setCellValueFactory(cellData -> cellData.getValue().wordProperty());
        transcriptionColumn.setCellValueFactory(cellData -> cellData.getValue().transcriptionProperty());
        translationColumn.setCellValueFactory(cellData -> cellData.getValue().translationProperty());
    }
    
    private void setTableValue() {
        requestGetWords();
        wordsTable.setItems(FXCollections.observableArrayList(wordList));   
        //sort();       
        //Helper.setHeightTable(albumsTable, 10);  
    }
    
    @Autowired
    private SessionManager sessionManager;
    
    @Value("${spring.data.rest.basePath}")
    private String basePath;
    
    private void requestGetWords() {    
        try { 
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.COOKIE, sessionManager.getSessionIdCookie());
                   
            PagedResources<Resource<Word>> wordResources = new Traverson(new URI(basePath), MediaTypes.HAL_JSON)
                    .follow(REL_PATH)
                    .withHeaders(headers)
                    .toObject(new TypeReferences.PagedResourcesType<Resource<Word>>() {});
                      
            PagedResources.PageMetadata metadata = wordResources.getMetadata();
            logger.info("Got {} of {} words: ", wordResources.getContent().size(), metadata.getTotalElements());
            wordList = wordResources.getContent().stream().map(Resource::getContent).collect(Collectors.toList());                     
        
            System.out.println(wordList);
        } 
        catch (URISyntaxException ex) {
            logger.error(ex.getMessage());
        }
    }
       
    @FXML
    public void addWord() {
        Word word = new Word();
        word.setWord("Cabbage9876ssssttt2");
        word.setTranscription("Cabbag");
        word.setTranslation("капуста77799912");
        requestService.post(REL_PATH, word, Word.class);
        logger.info("Post: {}", word);
        
        word.setTranslation("новая капуста777999");
        requestService.put(REL_PATH, word);
        logger.info("Put: {}", word);
                
        requestService.delete(REL_PATH, word.getId());
        logger.info("Delete: {}", word);      
    }
   
}
