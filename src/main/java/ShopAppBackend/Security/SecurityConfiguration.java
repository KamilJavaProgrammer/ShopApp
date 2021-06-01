package ShopAppBackend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.HttpStatusEntryPoint;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final String secret;
    private final UserDetailsServiceImpl userDetailsServiceimpl;

    @Autowired
    public SecurityConfiguration(@Value("${jwt.secret}") String secret, UserDetailsServiceImpl userDetailsServiceimpl) {
        this.secret = secret;
        this.userDetailsServiceimpl = userDetailsServiceimpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceimpl);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/products").permitAll().
                antMatchers("/products/name").permitAll().
                antMatchers("/login").permitAll().
                antMatchers("/registration").permitAll().
                antMatchers("/name12").hasAuthority("USER").
                antMatchers("/order").permitAll().
                antMatchers("/order/{id}").permitAll()
                .antMatchers("invoice/{id}").authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),userDetailsServiceimpl, secret))
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .cors()
                .and()
                .headers().frameOptions().disable();
        http.cors();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}


