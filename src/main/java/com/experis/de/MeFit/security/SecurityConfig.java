package com.experis.de.MeFit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

     // https://www.baeldung.com/spring-security-expressions
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        boolean enableSecurity = true;

        if (!enableSecurity) {
            http.csrf().disable();
        }
        else {
            // Custom converter to show roles instead of scopes
            final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
            jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(this.jwtGrantedAuthoritiesConverter());

            http.cors()
                    .and()
                    .authorizeRequests()
                    /*
                    .antMatchers("/user/info", "user/info/principal").permitAll()
                    .antMatchers("/api/resources/user").hasRole("User")
                    .antMatchers("/api/resources/admin").hasRole("Admin")
                    */
                    .anyRequest()
                    .authenticated()
                    .and()
                    .oauth2ResourceServer()
                    .jwt()
                    // Using our converter
                    .jwtAuthenticationConverter(customJwtAuthenticationConverter());
        }
    }

    // Implementation of replacing authorities with our roles
    @Bean
    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
        // You can use setAuthoritiesClaimName method
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        return jwtGrantedAuthoritiesConverter;
    }

    // Implementation of replacing authorities with our roles
    @Bean
    public CustomJwtAuthenticationConverter customJwtAuthenticationConverter() {
        // You can use setAuthoritiesClaimName method

        return new CustomJwtAuthenticationConverter("MeFit");
    }
}
