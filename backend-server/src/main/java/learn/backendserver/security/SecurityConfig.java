package learn.backendserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@ConditionalOnWebApplication
public class SecurityConfig {

    private final JwtConverter jwtConverter;

    public SecurityConfig(JwtConverter jwtConverter) {
        this.jwtConverter = jwtConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.configure(http));

        // the order of the antMatchers() method calls is important
        // as they're evaluated in the order that they're added
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/recipes/search**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/recipes/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/recipes/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/recipes/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/recipes/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/recipes/save**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/app-user-recipe-saved/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/app-user-recipe-saved/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/app-user-recipe-created/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/app-user-recipe-saved/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/organizations/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/organizations/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/organizations/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/organizations/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/organization-app-user/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/organization-app-user").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/organization-recipe").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/organization-recipe/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/security/authenticate").permitAll()
                .requestMatchers(HttpMethod.POST, "/security/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/security/refresh").authenticated()
                // if we get to this point, let's deny all requests
                .requestMatchers("/dist/**").permitAll()
                .requestMatchers("/**").denyAll());

//        http.addFilter(new JwtRequestFilter(authenticationManager(authConfig), jwtConverter));
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}