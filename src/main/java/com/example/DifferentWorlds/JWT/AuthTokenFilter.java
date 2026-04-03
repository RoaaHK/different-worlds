package com.example.DifferentWorlds.JWT;

import com.example.DifferentWorlds.Repository.CustomerRepository;
import com.example.DifferentWorlds.Service.CustomerService;
import com.example.DifferentWorlds.UserDetails.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/// execute filter only once per request
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;
    private final  CustomUserDetailsService customUserDetailsService;



    @Autowired
    public AuthTokenFilter(JwtUtils jwtUtils, HandlerExceptionResolver handlerExceptionResolver, CustomerRepository customerRepository, CustomerService customerService, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /// create custom filter
    /// load user details based on username
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        /// saving information about the request to a log file or console
        /// part of the URL-endpoint that the client is trying to access
        logger.debug("AuthTokenFilter called for URI: {}", request.getRequestURI());
        try {
            String jwt = parseJwt(request); /// get token from Http req

            // TODO extract it into external method CTRL+ALT+M
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String userName = jwtUtils.extractUsername(jwt);
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); /// enhance auth obj with another details from req not just username and pass
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("Authenticated user: {}", userName);
                    logger.info("Roles from JWT: {}", userDetails.getAuthorities());

                }
            }
        } catch (Exception e) {
            logger.error("Can not set user authentication: {}", e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
        filterChain.doFilter(request, response); /// continue filterChain
    }

    public String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromHeader(request);
        logger.debug("Extracted JWT: {}", jwt);
        return jwt;
    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> (UserDetails) customerRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
}
