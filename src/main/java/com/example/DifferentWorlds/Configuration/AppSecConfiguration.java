package com.example.DifferentWorlds.Configuration;

import com.example.DifferentWorlds.JWT.AuthTokenFilter;
import com.example.DifferentWorlds.Service.AuthorService;
import com.example.DifferentWorlds.Service.CustomerService;
import com.example.DifferentWorlds.UserDetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
public class AppSecConfiguration {
    private final AuthTokenFilter authTokenFilter;
    // TODO remove unused variable
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorService authorService;


    @Autowired
    public AppSecConfiguration(AuthTokenFilter authTokenFilter, CustomerService customerService, PasswordEncoder passwordEncoder, AuthorService authorService) {
        this.authTokenFilter = authTokenFilter;
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
        this.authorService = authorService;
    }

    @Bean
    public CorsConfigurationSource CorsConfigSource() {
        // TODO avoid commenting on every line the clean code will explain it self
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Allow only your frontend origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*")); // Allow all headers
        config.setAllowCredentials(true); // Allow credentials (e.g., cookies)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors-> cors.configurationSource(CorsConfigSource()))
                .authorizeHttpRequests((req) -> req
                        .requestMatchers(
                                "/h2-console/**",
                                "/api/guest",
                                "/api/auth/login/customer/**",
                                "/api/auth/login/author/**",
                                "/api/auth/login/admin/**",
                                "/api/auth/signup/**",
                                //"/api/auth/logout",
                                "/api/customer/**",
                                "/api/authentication",
                                "/api/customer/login",
                                "/api/admin/**",
                                "/api/purchases/**",
                                "/api/authors/**",
                                "/api/itemManagement"

                        ).permitAll()
                )
//                .authorizeHttpRequests(
//                        req -> req
//                                .requestMatchers("/api/admin/**").hasRole("Admin")
//                                .requestMatchers("/api/purchases/**").hasRole("Customer")
//                                .requestMatchers("/api/authors/**").hasRole("Author")
//                                .requestMatchers("/api/itemManagement").hasRole("Admin")
//                )
                .authorizeHttpRequests(
                        req -> req.anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .oauth2ResourceServer(resource -> {
//                    resource.jwt(custom -> custom.jwtAuthenticationConverter(
//                            new Converter<org.springframework.security.oauth2.jwt.Jwt, AbstractAuthenticationToken>() {
//                                private final JwtGrantedAuthoritiesConverter wrappedConverter;
//
//
//                                @Override
//                                public AbstractAuthenticationToken convert(org.springframework.security.oauth2.jwt.Jwt source) {
//                                    var grantedAuthorities = new ArrayList<>(wrappedConverter.convert(jwt));
//                                    // get role authorities
//                                    var roles = (List<String>) jwt.getClaims().get("roles");
//                                    if (roles != null) {
//                                        for (String role : roles) {
//                                            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//                                        }
//                                    }
//
//                                    return new JwtAuthenticationToken(jwt, grantedAuthorities);
//                                }
//                            }
//                    ));
//                })
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                //.authenticationProvider(authenticationProvider())
                /// when a user tries to access a protected resource without proper authentication, this entry point determines how to respond
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(unauthorizedHeader) /// Configures entry point for unauthorized access
//                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) /// ????
                )
                .build();
    }

    /// returns a UserDetails

    /// Manages authentication requests
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // TODO remove unused methods

    ///  This bean is responsible for authenticating users using user details and password encoder
//    @Bean
//    public AuthenticationProvider customerAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(customerService);
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }
//
//    @Bean
//    public AuthenticationProvider authorAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(authorService);
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }

}
