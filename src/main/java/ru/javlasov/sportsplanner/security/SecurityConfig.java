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

    private static final String BASE_URL_ARTICLES = "/api/v1/articles";

    private static final String BASE_URL_HEALTH = "/api/v1/healths";

    private static final String BASE_URL_TOURNAMENT = "/api/v1/tournaments";

    private static final String BASE_URL_TRAINING = "/api/v1/trains";

    private static final String BASE_URL_USER = "/api/v1/users";

    private final UserCredentialsService userCredentialsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers(HttpMethod.GET, BASE_URL_ARTICLES).permitAll()
                        .requestMatchers(HttpMethod.GET, BASE_URL_ARTICLES + "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, BASE_URL_ARTICLES).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, BASE_URL_ARTICLES + "/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, BASE_URL_ARTICLES + "/accept/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, BASE_URL_ARTICLES + "/decline/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, BASE_URL_ARTICLES).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, BASE_URL_HEALTH).permitAll()
                        .requestMatchers(HttpMethod.DELETE, BASE_URL_HEALTH + "/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, BASE_URL_HEALTH).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, BASE_URL_HEALTH).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, BASE_URL_TOURNAMENT).permitAll()
                        .requestMatchers(HttpMethod.DELETE, BASE_URL_TOURNAMENT + "/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, BASE_URL_TOURNAMENT).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, BASE_URL_TOURNAMENT).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, BASE_URL_TRAINING).permitAll()
                        .requestMatchers(HttpMethod.DELETE, BASE_URL_TRAINING + "/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, BASE_URL_TRAINING).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, BASE_URL_TRAINING).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, BASE_URL_USER).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, BASE_URL_USER).permitAll()
                        .requestMatchers(HttpMethod.PATCH, BASE_URL_USER).hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .userDetailsService(userCredentialsService)
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
