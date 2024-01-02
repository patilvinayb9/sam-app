package com.edge.core.modules.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }

    private AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(edgeUserDetails());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService edgeUserDetails() {
        return new EdgeUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //http.requiresChannel().anyRequest().requiresSecure();

        //http.requiresChannel()
//				.antMatchers("/login*").requiresSecure();

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/about").permitAll()
                .antMatchers("/api/secured/**").hasAnyRole("USER,ADMIN,SUPER_ADMIN")
                .antMatchers("/server/user/**").hasAnyRole("USER,ADMIN,SUPER_ADMIN")
                .antMatchers("/server/admin/**").hasAnyRole("ADMIN,SUPER_ADMIN")
                .antMatchers("/server/superAdmin/**").hasAnyRole("SUPER_ADMIN")
                .antMatchers("/**").hasAnyRole("USER,ADMIN,SUPER_ADMIN,ANONYMOUS")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(new RefererRedirectionAuthenticationSuccessHandler())
                //.loginPage("/login")
                .defaultSuccessUrl("/index.html")
                .failureUrl("/#/page/loginFailure")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/index.html")
                .permitAll()
                .deleteCookies("JSESSIONID");
        //.and()
        //.rememberMe().key("2530742129").userDetailsService(edgeUserDetails());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers("/edgeApp.html", "/api/unsecured/**", "/modules/**", "/common/**", "/compressed/**", "/css/**", "/external/**", "/images/**", "/lib/**", "/favicon.ico");
    }
}
