package erp.javaguides.erpbackend.jwt;

import erp.javaguides.erpbackend.service.AuthenticationService;
import erp.javaguides.erpbackend.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;

    private final UserDetailsService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // This log message is crucial. If it doesn't appear, the filter is not being executed.
        logger.info("*** JWT Filter is running for request URI: {} ***", request.getRequestURI());
//        final String authHeader = request.getHeader("Authorization");
        String jwt;
        String userEmail;

        jwt = extractJwtFromCookies(request);
        if (jwt == null) {
            logger.warn("No Authorization header found or it's not a Bearer token. Skipping JWT filter.");
            filterChain.doFilter(request, response);
            return;
        }

        logger.info("Extracted JWT token: {}", jwt);
        userEmail = jwtUtil.extractUserName(jwt);
        logger.info("Extracted user email from JWT: {}", userEmail);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            try {
                // Load the UserDetails from the database using the email from the token.
                // This is where a UsernameNotFoundException can be thrown.
                userDetails = this.authenticationService.loadUserByUsername(userEmail);
                logger.info("User details successfully loaded for email: {}", userEmail);
                logger.info("User authorities: {}", userDetails.getAuthorities());

                if (jwtUtil.isTokenValid(jwt, userDetails.getUsername())) {
                    logger.info("JWT token is valid. Creating Authentication object...");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    logger.info("Authentication successful. SecurityContext populated.");
                } else {
                    logger.warn("JWT token is NOT valid for user: {}", userEmail);
                }
            } catch (UsernameNotFoundException e) {
                logger.error("User not found in database for JWT: {}", userEmail);
            }
        } else {
            logger.warn("User email is null or user is already authenticated. Skipping authentication.");
        }
        filterChain.doFilter(request,response);
    }

    //Extracts JWT token from HttpOnly cookie named "jwt".
    private String extractJwtFromCookies(HttpServletRequest request) {
        if(request.getCookies() != null) {
            for(Cookie cookie : request.getCookies()) {
                if("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
