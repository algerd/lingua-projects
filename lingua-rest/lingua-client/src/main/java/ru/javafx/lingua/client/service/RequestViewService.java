
package ru.javafx.lingua.client.service;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.javafx.lingua.client.controller.MainController;
import ru.javafx.lingua.client.jfxintegrity.BaseFxmlController;

@Service
public class RequestViewService {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private MainController mainController;
    
    @Autowired
    private ApplicationContext applicationContext;
   
    public void show(Class<? extends BaseFxmlController> controllerClass) {
        mainController.show(applicationContext.getBean(controllerClass));
    }
    
    public void showDialog(Class<? extends BaseFxmlController> controllerClass, Modality modality) {       
        Stage stage = new Stage();           
        stage.initModality(modality);
        stage.initOwner(applicationContext.getBean("primaryStage", Stage.class));
        Scene scene = new Scene(applicationContext.getBean(controllerClass).getView()); 
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    public void showDialog(Class<? extends BaseFxmlController> controllerClass) {
        showDialog(controllerClass, Modality.WINDOW_MODAL);
    }
    
}
