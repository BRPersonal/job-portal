package com.fslabs.work.config;

import com.fslabs.work.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
public class WebSecurityConfig
{

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final ApplicationContext context;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService,
                             CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                             ApplicationContext context)
    {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.context = context;
    }

    //List of urls thats does not need a user to login to access
    private final String[] whiteListedUrls =
    {
            "/",
            "/global-search/**",
            "/register",
            "/register/**",
            "/webjars/**",
            "/resources/**",
            "/assets/**",
            "/css/**",
            "/summernote/**",
            "/js/**",
            "/*.css",
            "/*.js",
            "/*.js.map",
            "/fonts**", "/favicon.ico", "/resources/**", "/error"
    };

    @Bean
    @Order(3)
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        log.debug("Creating Security filter chain");

        http.authenticationProvider(context.getBean("authenticationProvider",
                AuthenticationProvider.class));

        http.authorizeHttpRequests(
                auth -> {
                    auth.requestMatchers(whiteListedUrls).permitAll();
                    auth.anyRequest().authenticated();
                }
        );

        http.formLogin(form->form.loginPage("/login").permitAll()
                        .successHandler(customAuthenticationSuccessHandler))
                .logout(logout-> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/");
                }).cors(Customizer.withDefaults())
                .csrf(csrf->csrf.disable());


        return http.build();
    }

    @Bean
    @Order(2)
    public AuthenticationProvider authenticationProvider()
    {
        log.debug("Creating AuthenticationProvider Bean");

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(context.getBean("passwordEncoder",
                PasswordEncoder.class));
        authenticationProvider.setUserDetailsService(customUserDetailsService);

        return authenticationProvider;
    }

    @Bean
    @Order(1)
    public PasswordEncoder passwordEncoder()
    {
        log.debug("Creating PasswordEncoder Bean");
        return new BCryptPasswordEncoder(11);
    }
}
