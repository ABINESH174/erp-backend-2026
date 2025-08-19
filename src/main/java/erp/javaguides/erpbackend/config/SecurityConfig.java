package erp.javaguides.erpbackend.config;

import erp.javaguides.erpbackend.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
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
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((auth) -> auth
                                // 1. Public endpoints (first)
                                .requestMatchers(
                                        "/api/authentication/authenticate",
                                        "/api/authentication/set-password",
                                        "/api/authentication/new-password",
                                        "/api/authentication/get-otp",
                                        "/api/bonafide/**"
                                ).permitAll()

                                .requestMatchers(HttpMethod.POST,
                                        "/api/faculty/post",
                                        "/api/student",
                                        "/api/hod/create",
                                        "/api/office-bearer/create",
                                        "/api/principal/create"
                                ).permitAll()

                                // 2. Specific role-based API access
                                .requestMatchers("/api/principal/**").hasAnyRole("PRINCIPAL","ADMIN")
                                .requestMatchers("/api/office-bearer/**").hasAnyRole("PRINCIPAL", "OB","ADMIN")
                                .requestMatchers("/api/hod/**").hasAnyRole("PRINCIPAL", "HOD","ADMIN")
                                .requestMatchers("/api/faculty/**", "/api/authentication/upload-students/**", "/api/authentication/create/student/**")
                                    .hasAnyRole("PRINCIPAL", "HOD", "FACULTY","ADMIN")
                                .requestMatchers("/api/student/**").hasAnyRole("PRINCIPAL", "HOD", "FACULTY", "STUDENT","ADMIN")
                                .requestMatchers("/api/email/**").hasAnyRole("STUDENT", "FACULTY", "HOD", "OB", "PRINCIPAL", "ADMIN") // Add ADMIN if needed

                                // 3. Catch-all for all other /api/** routes: only ADMIN
                                .requestMatchers("/api/**").hasRole("ADMIN")

                                // 4. Everything else must be authenticated
                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); //Required for cookies
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000","http://192.168.11.69:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));


        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }


}
