
package ru.javafx.lingua.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import ru.javafx.lingua.client.jfxintegrity.BaseFxmlController;
import ru.javafx.lingua.client.jfxintegrity.FXMLController;

@FXMLController
@Scope("prototype")
public class DialogController extends BaseFxmlController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTitle("Dialog");
    }

}
