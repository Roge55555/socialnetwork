package com.senla.project.socialnetwork.config;

import com.senla.project.socialnetwork.security.JwtConfigure;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//включает AOP для authority
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigure jwtConfigure;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//межсайтовая подделка запроса

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//запрет на использование сессий в любом виде
                .and()

                .authorizeRequests()//ограничивае доступ на основе HttpServletRequest

                .antMatchers("/auth/login").permitAll()
                .antMatchers("/users/registration").permitAll()

                .anyRequest()
                .authenticated()
                .and()

                .apply(jwtConfigure);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {//контейнер для поставщиков аутентификации
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
