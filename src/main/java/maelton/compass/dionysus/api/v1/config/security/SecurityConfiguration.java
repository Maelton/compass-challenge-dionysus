package maelton.compass.dionysus.api.v1.config.security;

import maelton.compass.dionysus.api.v1.config.security.authentication.jwt.TokenAuthenticationSecurityFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final TokenAuthenticationSecurityFilter tokenAuthenticationSecurityFilter;
    public SecurityConfiguration(TokenAuthenticationSecurityFilter tokenAuthenticationSecurityFilter) {
        this.tokenAuthenticationSecurityFilter = tokenAuthenticationSecurityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/**").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.POST, "/sales").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/sales").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/sales").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.POST, "/users").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.POST, "/wines").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/wines").permitAll()
                .requestMatchers(HttpMethod.PUT, "/wines").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.POST, "/models").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/models").permitAll()
                .requestMatchers(HttpMethod.PUT, "/models").hasAuthority("ADMIN")

                .anyRequest().authenticated()
            ).addFilterBefore(tokenAuthenticationSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }
}