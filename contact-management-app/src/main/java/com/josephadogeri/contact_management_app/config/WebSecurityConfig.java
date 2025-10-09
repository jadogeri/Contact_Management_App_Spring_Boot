package com.josephadogeri.contact_management_app.config;

import com.josephadogeri.contact_management_app.oldcode.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    private static final String[] AUTH_WHITELIST = {
            // Swagger UI v2
            "/v2/api-docs",
            "/swagger-ui.html",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            // Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/swagger-ui/**",
            "/users/**"
    };

    private final JwtAuthenticationFilter  jwtAuthenticationFilter;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint//, CustomAuthenticationEntryPoint customAuthenticationEntryPoint
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        //this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        request-> request
                                .requestMatchers( AUTH_WHITELIST).permitAll()
                                .anyRequest().authenticated()

                )
//                .formLogin(form -> form.disable())
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());




//        httpSecurity
//             .csrf(csrf -> csrf.disable())
//             .authorizeHttpRequests(
//                     request-> request
//                             .requestMatchers( AUTH_WHITELIST).permitAll()
//                             .anyRequest().authenticated()
////
//             )
//             //.formLogin(Customizer.withDefaults())
// //            .formLogin(form -> form.disable())
//             .addFilterBefore(jwtAuthenticationFilter,
//                     UsernamePasswordAuthenticationFilter.class)
//            .httpBasic(Customizer.withDefaults())
//             //.httpBasic(httpBasic -> httpBasic.disable())
////
//             .exceptionHandling(exceptionHandling -> exceptionHandling
////
////
//                     .authenticationEntryPoint(customAuthenticationEntryPoint) // Use custom entry point
//             );

        return httpSecurity.build();


    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider
//                = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(bCryptPasswordEncoder());
//
//        return provider;
        DaoAuthenticationProvider provider
                = new DaoAuthenticationProvider(userDetailsService);
        //provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());

        //this removes the masking of usernamenot found
        //and sets all exception to bad request : invalid username and password for security reasons
        provider.setHideUserNotFoundExceptions(false); // This line changes the behavior

        return provider;

    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(14);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return new CustomAuthenticationFailureHandler();
//    }
//
//
//    // Example for Basic Auth: Create a custom entry point to use your failure handler
//    @Bean
//    public CustomAuthenticationEntryPoint customBasicAuthenticationEntryPoint() {
//        CustomAuthenticationEntryPoint entryPoint = new CustomAuthenticationEntryPoint();
//
//        return entryPoint;
//    }
}

