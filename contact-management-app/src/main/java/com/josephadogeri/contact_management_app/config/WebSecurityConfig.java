package com.josephadogeri.contact_management_app.config;

import com.josephadogeri.contact_management_app.CustomAuthenticationEntryPoint;
import com.josephadogeri.contact_management_app.CustomAuthenticationFailureHandler;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    private final UserDetailsService userDetailsService;

    private static final String[] AUTH_WHITELIST = {
            // for Swagger UI v2
//            "/v2/api-docs",
//            "/api-docs",
//            "/swagger-ui/index.html",
//            "/swagger-ui.index.html",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/webjars/**",
            // for Swagger UI v3 (OpenAPI)
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/v3/api-docs",
            "/v3/api-docs/swagger-config",
            "/v3/api-docs/swagger-config",
            "/users/register",
            "/users/login",
            "/users/register",
            "/users/login",
            "/register",
            "/login",
            "/users/**"
    };

    private final JwtAuthenticationFilter  jwtAuthenticationFilter;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
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
                //.formLogin(Customizer.withDefaults())
                .formLogin(form -> form.disable())
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
            //    .httpBasic(Customizer.withDefaults())
                .httpBasic(httpBasic -> httpBasic.disable())

                .exceptionHandling(exceptionHandling -> exceptionHandling

                        .authenticationEntryPoint(customAuthenticationEntryPoint) // Use custom entry point
                );

//                .httpBasic(            configurer ->
//                        configurer.securityContextRepository(new HttpSessionSecurityContextRepository()));
        return httpSecurity.build();

    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider
                = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());

        return provider;

    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(14);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }


    // Example for Basic Auth: Create a custom entry point to use your failure handler
    @Bean
    public CustomAuthenticationEntryPoint customBasicAuthenticationEntryPoint() {
        CustomAuthenticationEntryPoint entryPoint = new CustomAuthenticationEntryPoint();

        return entryPoint;
    }
}

