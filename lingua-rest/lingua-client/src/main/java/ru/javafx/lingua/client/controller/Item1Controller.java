
package ru.javafx.lingua.client.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.HttpHeaders;
import ru.javafx.lingua.client.SessionManager;
import ru.javafx.lingua.client.jfxintegrity.BaseFxmlController;
import ru.javafx.lingua.client.jfxintegrity.FXMLController;
import ru.javafx.lingua.client.resources.Authority;
import ru.javafx.lingua.client.resources.User;

@FXMLController(title = "Item1")
@Scope("prototype")
public class Item1Controller extends BaseFxmlController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SessionManager sessionManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                
        requestWithSessionId();           
            
    }
         
    /*
    Во время каждого rest-запроса надо отправлять куку сессии, предварительно проверяя/обнуляя время её жизни.
    Это самый эффективный путь rest-запросов с аутентификацией.
    */
    public void requestWithSessionId() {
        String urlString = "http://localhost:8080/api/";       
        try { 
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", "JSESSIONID=" + sessionManager.getSessionId());
                   
            PagedResources<Resource<User>> userResources = new Traverson(new URI(urlString), MediaTypes.HAL_JSON)
                    .follow("users")
                    .withHeaders(headers)
                    .toObject(new TypeReferences.PagedResourcesType<Resource<User>>() {});
                      
            PagedResources.PageMetadata metadata = userResources.getMetadata();
            logger.info("Got {} of {} users: ", userResources.getContent().size(), metadata.getTotalElements());

            userResources.getContent().parallelStream().forEach(
                userResource -> {
                    User user = userResource.getContent();                        
                    try {                                            
                        Resources<Authority> authorityResources = new Traverson(new URI(userResource.getLink("self").getHref()), MediaTypes.HAL_JSON)
                                .follow("authorities")
                                .withHeaders(headers)
                                .toObject(new ParameterizedTypeReference<Resources<Authority>>() {});  

                        authorityResources.getContent().parallelStream().forEach(
                            authority -> user.getAuthorities().add(authority)
                        );                    
                    } catch (URISyntaxException ex) {
                        logger.error(ex.getMessage());
                    }                              
                    logger.info("User: {}", user);               
                } 
            );            
        } catch (URISyntaxException ex) {
            logger.error(ex.getMessage());
        }
    }
          
}
