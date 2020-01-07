package ru.javawebinar.topjava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import ru.javawebinar.topjava.service.UserService;


@Configuration
@EnableWebSecurity
public class CustomRequestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers( "/login").permitAll();

        http.authorizeRequests().antMatchers("/profile/register").anonymous();

        http.authorizeRequests().antMatchers("/**/admin/**").access("hasRole('ROLE_ADMIN')");

        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/meals")
                .failureUrl("/login?error=true")
                .and().logout().logoutSuccessUrl("/login");

        http.authorizeRequests().antMatchers("/**").authenticated();
    }


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/js", "/webjars/**");
    }

/*
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/resources/");
    }

 */

}
