package ru.javlasov.sportsplanner.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String BASE_URL_API_ARTICLES = "/api/v1/articles";

    private static final String BASE_URL_API_HEALTH = "/api/v1/healths";

    private static final String BASE_URL_API_TOURNAMENT = "/api/v1/tournaments";

    private static final String BASE_URL_API_TRAINING = "/api/v1/workout";

    private static final String BASE_URL_API_USER = "/api/v1/users";

    private static final String BASE_URL_ARTICLES = "/articles";

    private static final String BASE_URL_API_SPORTS = "/api/v1/sports";

    private final UserCredentialsService userCredentialsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        HttpSecurity filterChain = httpSecurity.csrf(AbstractHttpConfigurer::disable);
        addPermissions(filterChain);
        filterChain.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .userDetailsService(userCredentialsService)
                .formLogin(Customizer.withDefaults());
        return filterChain.build();
    }

//    /article/{id}

    private void addPermissions(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers(HttpMethod.GET, BASE_URL_ARTICLES).permitAll()
                .requestMatchers(HttpMethod.GET, BASE_URL_ARTICLES + "/article/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/registration").permitAll()
                .requestMatchers(HttpMethod.GET, BASE_URL_API_SPORTS).permitAll()
                .requestMatchers(HttpMethod.GET, BASE_URL_API_ARTICLES).permitAll()
                .requestMatchers(HttpMethod.GET, BASE_URL_API_ARTICLES + "/article/**").permitAll()
                .requestMatchers(BASE_URL_API_ARTICLES).hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, BASE_URL_API_ARTICLES + "/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PATCH, BASE_URL_API_ARTICLES + "/accept/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, BASE_URL_API_ARTICLES + "/decline/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, BASE_URL_API_HEALTH + "/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(BASE_URL_API_HEALTH).hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, BASE_URL_API_TOURNAMENT + "/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(BASE_URL_API_TOURNAMENT).hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, BASE_URL_API_TRAINING + "/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(BASE_URL_API_TRAINING).hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, BASE_URL_API_USER).permitAll()
                .requestMatchers(BASE_URL_API_USER).hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
        );
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
