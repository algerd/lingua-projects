
package ru.javafx.lingua.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.HttpHeaders;
import ru.javafx.lingua.client.resources.Authority;
import ru.javafx.lingua.client.resources.User;

public class TraversonExamples {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
        
    /*
    Во время каждого rest-запроса отправляются учётные данные и они будут проверяться на сервере путём запроса
    на сервер бд. Это очень накладно иметь при каждом rest-запросе дополнительный запрос в бд и плюс регистрацию
    мультитенанта. Намного эффективнее по производительности сравнивать id сессии, но это сложнее программно.
    */
    public void requestWithAuthorization() {
        String urlString = "http://localhost:8080/api/";       
        try {           
            // authorization header
            String username = "user";
            String password = "green";
            String encoding = Base64.getEncoder().encodeToString((username + ":" + password).getBytes("utf-8"));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + encoding);
                   
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
            
        } catch (URISyntaxException | UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
    }
      
    public void traverson() {
        
        URI uri = null;       
        try {
            uri = new URI("http://localhost:8080/api/");             
        } catch (URISyntaxException ex) {
            logger.error(ex.getMessage());
        } 
        Traverson traverson = new Traverson(uri, MediaTypes.HAL_JSON);
        // Set up path traversal
		Traverson.TraversalBuilder builder = traverson.follow("users");        
        
        // Log discovered
		//logger.info("Discovered link: {}", builder.asTemplatedLink());
        
        /*     
        ParameterizedTypeReference<Resources<User>> resourceParameterizedTypeReference = new ParameterizedTypeReference<Resources<User>>() {};
        Resources<User> userResources = builder.toObject(resourceParameterizedTypeReference);
        
		logger.info("Got {} users: ", userResources.getContent().size());

        userResources.getContent().stream().forEach(
            user -> logger.info("User: {}", user)
        );
        */ 
        
        
        PagedResources<Resource<User>> userResources = builder.toObject(new TypeReferences.PagedResourcesType<Resource<User>>() {});

        PagedResources.PageMetadata metadata = userResources.getMetadata();
		logger.info("Got {} of {} users: ", userResources.getContent().size(), metadata.getTotalElements());

        userResources.getContent().parallelStream().forEach(
            userResource -> {
                User user = userResource.getContent();                        
                try {                                            
                    Resources<Authority> authorityResources = new Traverson(new URI(userResource.getLink("self").getHref()), MediaTypes.HAL_JSON)
                            .follow("authorities")
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
        
        
        /*
        PagedResources<UserResource> userResources = builder.toObject(new TypeReferences.PagedResourcesType<UserResource>() {});

        PagedResources.PageMetadata metadata = userResources.getMetadata();
		logger.info("Got {} of {} users: ", userResources.getContent().size(), metadata.getTotalElements());

        userResources.getContent().stream().forEach(
            userResource -> {
                logger.info("User: {}", userResource.getContent());
                logger.info("User links: {}", userResource.getLinks());
            } 
        );
        */
        
    }
    
    public void request() {
        String urlString = "http://localhost:8080/api/";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST"); 
            connection.setRequestProperty("Content-Type", "application/hal+json"); 
            connection.setRequestProperty("charset", "utf-8");
                       
            // Set the cookie value to send
            //connection.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
            
            Map<String, List<String>> map = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey() + " ,Value : " + entry.getValue());
            }

            System.out.println("\nGet Response Header By Key ...\n");
            String setCookie = connection.getHeaderField("Set-Cookie");

            if (setCookie == null) {
                System.out.println("Key 'Set-Cookie' is not found!");
            } else {
                System.out.println("Set-Cookie: " + setCookie);
            }           
            
            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            
            
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }  
    }
    
    private void sendPost() {
    
        String urlString = "http://localhost:8080/api/";
        String urlParameters  = "username=user&password=green";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        try {           
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput( true );
            con.setInstanceFollowRedirects( false );        
            con.setRequestMethod("POST");
            con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
            con.setRequestProperty( "charset", "utf-8");
            con.setRequestProperty( "Content-Length", Integer.toString(postDataLength));
            con.setUseCaches( false );
                      
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
                wr.flush();
            }

            // reading the HTML output of the POST HTTP request
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }    
            in.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }     
    }

}
