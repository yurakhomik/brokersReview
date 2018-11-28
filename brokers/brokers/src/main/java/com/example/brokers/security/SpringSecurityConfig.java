package com.example.brokers.security;

import com.example.brokers.user.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/brokers", "/comments", "/marks").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/users", "/brokers").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/comments", "/marks").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/comments/*", "/marks/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/brokers/*", "/users/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/comments/*", "marks/*").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.DELETE, "/brokers/*", "/users/*").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
