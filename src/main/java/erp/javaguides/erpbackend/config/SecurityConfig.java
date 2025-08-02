package erp.javaguides.erpbackend.config;

import erp.javaguides.erpbackend.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Crucial log message to confirm this method is being executed.
        logger.info("*** Configuring Security Filter Chain ***");
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/api/authentication/**").permitAll()
                                .requestMatchers("/api/principal/**").hasRole("PRINCIPAL")
                                .requestMatchers("/api/office-bearer/**").hasAnyRole("PRINCIPAL", "OB")
                                .requestMatchers("/api/hod/**").hasAnyRole("PRINCIPAL", "HOD")
                                .requestMatchers("/api/faculty/**").hasAnyRole("PRINCIPAL", "HOD", "FACULTY")
                                .requestMatchers("/api/student/**").hasAnyRole("PRINCIPAL", "HOD", "FACULTY", "STUDENT")
                                .requestMatchers("/api/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
