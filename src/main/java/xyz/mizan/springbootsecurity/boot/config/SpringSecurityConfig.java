package xyz.mizan.springbootsecurity.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
	
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("employee").password(passwordEncoder.encode("123")).roles("EMP");
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("123")).roles("ADMIN");
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/employee/**").hasAnyRole("EMP")
        .antMatchers("/").permitAll()
        .and()
        .formLogin().loginPage("/login").failureUrl("/login?error").usernameParameter("username").passwordParameter("password").successHandler(customizeAuthenticationSuccessHandler)	
	    .and()
	    .rememberMe().key("svskey").rememberMeParameter("remember-me").rememberMeCookieName("remember-me-cookie").tokenValiditySeconds(86400)
        .and()
		.logout().logoutSuccessUrl("/logout")
		.and()
        .exceptionHandling().accessDeniedPage("/access-denied")
        .and()
        .csrf();
        
    }
	
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
