package ru.javafx.lingua.client.controller;

import ru.javafx.lingua.client.service.RequestViewService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javafx.lingua.client.controller.words.WordsController;
import ru.javafx.lingua.client.jfxintegrity.BaseFxmlController;
import ru.javafx.lingua.client.jfxintegrity.FXMLController;

@FXMLController(loadable = false)
public class TopBarController extends BaseFxmlController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
      
    @Autowired
    private MainController parentController;
    
    @Autowired
    private RequestViewService requestViewService;
    
    @FXML
    private AnchorPane topBar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.setView(topBar);
    }
    
    @FXML
    private void showWords() {
        logger.info("showWords");
        requestViewService.show(WordsController.class);
    }
    
    @FXML
    private void showItem1() {
        logger.info("showItem1");
        requestViewService.show(Item1Controller.class);
    }
    
    @FXML
    private void showItem2() {
        logger.info("showItem2");  
        requestViewService.show(Item2Controller.class);
    }
        
    @FXML
    private void showDialog() {
        logger.info("showDialog");  
        requestViewService.showDialog(DialogController.class, Modality.WINDOW_MODAL);
    }
     
}
