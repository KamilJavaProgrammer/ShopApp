package ShopAppBackend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .authorizeRequests().
                antMatchers("/users/registration").permitAll().
                antMatchers("/users/verification").permitAll().
                antMatchers("/users/login").permitAll().
                antMatchers("/users/login/admin").permitAll().
                antMatchers("/users/all").authenticated().
                antMatchers("/users/auth").authenticated().
                antMatchers("/socket").authenticated().
                antMatchers("/topic").authenticated().
                antMatchers(HttpMethod.POST,"/users/password").permitAll().
                antMatchers(HttpMethod.PATCH,"/users/password").authenticated().
                antMatchers(HttpMethod.GET,"image/{name}").permitAll().
                antMatchers(HttpMethod.GET,"/subcategories").permitAll().
                antMatchers(HttpMethod.POST,"/subcategories/manufacturers").permitAll().
                antMatchers(HttpMethod.POST,"/subcategories").permitAll().
                antMatchers(HttpMethod.GET,"/shopClients").authenticated().
                antMatchers(HttpMethod.GET,"/shopClients/all").authenticated().
                antMatchers(HttpMethod.GET,"/shopClients/{id}").authenticated().
                antMatchers(HttpMethod.DELETE,"/shopClients/{id}").hasAuthority("ADMIN").
                antMatchers(HttpMethod.POST,"/shopClients").hasAuthority("ADMIN").
                antMatchers(HttpMethod.DELETE,"/shopClients").hasAuthority("ADMIN").
                antMatchers(HttpMethod.PATCH,"/shopClients").authenticated().
                antMatchers(HttpMethod.PATCH,"/serviceClients").authenticated().
                antMatchers(HttpMethod.GET,"/sections").permitAll().
                antMatchers(HttpMethod.POST,"/sections").hasAuthority("ADMIN").
                antMatchers(HttpMethod.DELETE,"/sections/{id}").hasAuthority("ADMIN").
                antMatchers(HttpMethod.GET,"/products/all").authenticated().
                antMatchers(HttpMethod.GET,"/products/parts").hasAuthority("ADMIN").
                antMatchers(HttpMethod.DELETE,"/products").hasAuthority("ADMIN").
                antMatchers(HttpMethod.GET,"/products/name/{page}").permitAll().
                antMatchers(HttpMethod.GET,"/products/{id}").permitAll().
                antMatchers(HttpMethod.DELETE,"/products/{id}").hasAuthority("ADMIN").
                antMatchers(HttpMethod.PATCH,"/products/{id}").hasAuthority("ADMIN").
                antMatchers(HttpMethod.POST,"/products/export").hasAuthority("ADMIN").
                antMatchers(HttpMethod.GET,"/products/parcel").authenticated().
                antMatchers(HttpMethod.GET,"/products/search").permitAll().
                antMatchers(HttpMethod.GET,"/news").hasAuthority("ADMIN").
                antMatchers(HttpMethod.POST,"/news").permitAll().
                antMatchers(HttpMethod.DELETE,"/news").hasAuthority("ADMIN").
                antMatchers(HttpMethod.GET,"/invoices/{id}").authenticated().
                antMatchers(HttpMethod.GET,"/invoices").hasAuthority("ADMIN").
                antMatchers(HttpMethod.POST,"/invoices").hasAuthority("ADMIN").
                antMatchers(HttpMethod.GET,"/data/{id}").permitAll().
                antMatchers(HttpMethod.GET,"/data").hasAuthority("ADMIN").
                antMatchers(HttpMethod.GET,"/orders").authenticated().
                antMatchers(HttpMethod.POST,"/orders").permitAll().
                antMatchers(HttpMethod.GET,"/orders/all").hasAuthority("ADMIN").
                antMatchers(HttpMethod.GET,"/categories").permitAll().
                antMatchers(HttpMethod.GET,"/businesses").hasAuthority("ADMIN").
                antMatchers(HttpMethod.DELETE,"/articleLine/{id}").hasAuthority("ADMIN").
                antMatchers(HttpMethod.POST,"/articleLine").hasAuthority("ADMIN").
                antMatchers(HttpMethod.GET,"/articleLine").permitAll().
                antMatchers(HttpMethod.PUT,"/messages").authenticated().
                antMatchers(HttpMethod.PATCH,"/messages").authenticated()
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


