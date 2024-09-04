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
                .requestMatchers(HttpMethod.DELETE, "/v1/**").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.POST, "/v1/sales").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/v1/sales").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/v1/sales").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.POST, "/v1/users").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/v1/users").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/v1/users").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.POST, "v1/wines").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/v1/wines").permitAll()
                .requestMatchers(HttpMethod.PUT, "/v1/wines").hasAuthority("ADMIN")

                .requestMatchers(HttpMethod.POST, "/v1/models").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/v1/models").permitAll()
                .requestMatchers(HttpMethod.PUT, "/v1/models").hasAuthority("ADMIN")

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