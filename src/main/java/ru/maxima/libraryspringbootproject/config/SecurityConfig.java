package ru.maxima.libraryspringbootproject.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.maxima.libraryspringbootproject.security.JWTFilter;
import ru.maxima.libraryspringbootproject.service.UsersDetailsService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UsersDetailsService userDetailsService;
    private JWTFilter filter;

    @Autowired
    public SecurityConfig(UsersDetailsService userDetailsService, JWTFilter filter) {
        this.userDetailsService = userDetailsService;
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/auth/**","/login").permitAll()
                                . requestMatchers("/auth/**").hasRole("USER")
                                . requestMatchers("/time/**").hasRole("USER")
                                .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/vendor/**", "/fonts/**").permitAll()
                                .requestMatchers("/auth/createvent").hasRole("USER")
                                .anyRequest().hasAnyRole("USER"))
                               .userDetailsService(userDetailsService)

                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")

                .defaultSuccessUrl("/auth/index", false)  .permitAll()

                .defaultSuccessUrl("/auth/list", true).permitAll()
                .defaultSuccessUrl("/auth/park", true).permitAll()
                .defaultSuccessUrl("/auth/listevent", true).permitAll()
                .defaultSuccessUrl("/auth/eisen", true).permitAll()
                .defaultSuccessUrl("/auth/dashboard", true).permitAll()
                .defaultSuccessUrl("/auth/calendar", true).permitAll()




                .failureUrl("/auth/login?error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/auth/index");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}

