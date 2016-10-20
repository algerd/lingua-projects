
package ru.javafx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    //To enable authentication annotation support
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() 
            .antMatchers("/api/users/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()    
            .and()
            .httpBasic();	   	
	}

}
