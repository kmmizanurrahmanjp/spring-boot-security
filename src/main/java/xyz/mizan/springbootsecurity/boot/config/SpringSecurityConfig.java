package xyz.mizan.springbootsecurity.boot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	/*
	 * 
	 * Database authentication and persistent token
	 * 
	 */
	@Autowired
	private Environment env;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
	
	@Autowired
	PersistentTokenRepository tokenRepository;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	  auth.jdbcAuthentication().dataSource(dataSource())
		.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
		.authoritiesByUsernameQuery("SELECT username, role FROM user_roles WHERE username = ?")
		.passwordEncoder(encoder());
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
	    .rememberMe().rememberMeParameter("remember-me").tokenRepository(tokenRepository).tokenValiditySeconds(300)
        .and()
		.logout().logoutSuccessUrl("/logout")
		.and()
        .exceptionHandling().accessDeniedPage("/access-denied")
        .and()
        .csrf();
        
    }
	
	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder
				.create()
				.username(env.getProperty("spring.datasource.username"))
				.password(env.getProperty("spring.datasource.password"))
				.url(env.getProperty("spring.datasource.url"))
				.driverClassName(env.getProperty("com.mysql.jdbc.Driver"))
				.build();
	}
	
	@Bean
	  public PersistentTokenRepository tokenRepository() {
	    JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
	    jdbcTokenRepositoryImpl.setDataSource(dataSource());
	    return jdbcTokenRepositoryImpl;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	/*
	 * 
	 * Inmamory authentication hash token
	 * 
	 */
	/*@Autowired
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
	}*/
}
