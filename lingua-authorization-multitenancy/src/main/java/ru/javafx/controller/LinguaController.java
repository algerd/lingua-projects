
package ru.javafx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javafx.entity.User;
import ru.javafx.service.UserService;
import ru.javafx.validator.UserValidator;
import ru.javafx.multitenant.MultitenantCreateService;
import org.springframework.validation.annotation.Validated;

@RestController
public class LinguaController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private MultitenantCreateService multitenantCreateService;
    
    @Autowired
    private UserValidator userValidator;
   
    /*
    @RequestMapping(value = "/registration", method = POST)
    public ResponseEntity<?> register(BindingResult bindingResult, @RequestBody Object input) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(
            ServletUriComponentsBuilder.fromCurrentRequest().path("").build().toUri());
        
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            userValidator.validate(input, bindingResult);
            if (bindingResult.hasErrors()) {           
				return new ResponseEntity<>(bindingResult.getAllErrors(), httpHeaders, HttpStatus.NON_AUTHORITATIVE_INFORMATION);          
            }
        }
    }
    */
    /*
    @Autowired
    private WordRepository wordRepository;
    
    @RequestMapping(value = "/api/mywords", method = RequestMethod.GET) 
    public ResponseEntity<?> getWords() {
        List<Word> words = wordRepository.findAll(); 
        Resources<Word> resources = new Resources<>(words); 
        resources.add(linkTo(methodOn(LinguaController.class).getWords()).withSelfRel());
        logger.info("getWords: " + resources);
        return ResponseEntity.ok(resources); 
    }
    */

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@Validated @RequestBody User user, UriComponentsBuilder ucBuilder){
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            logger.info("A User with name "+ authentication.getPrincipal() +" is authenticated");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }      
        if (userService.isUserExist(user)) {           
            logger.info("A User with name "+ user.getUsername() +" already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.getAuthorities().add(userService.getAuthority("USER"));       
        userService.save(user);       
        multitenantCreateService.createTenant(user.getId());
        logger.info("Creating User: " + user);  
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
