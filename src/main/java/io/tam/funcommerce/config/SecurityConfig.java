package io.tam.funcommerce.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();
        http
                .authorizeRequests()
                .anyRequest()
                .permitAll();
        http
                .cors(c -> {
                    CorsConfigurationSource configurationSource = r -> {
                        final CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

                        return configuration;
                    };
                    c.configurationSource(configurationSource);
                });
    }
}
