package ShopAppBackend.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final String secret;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserDetailsServiceImpl userDetailsServiceImpl,
                                  String secret) {
        super(authenticationManager);
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.secret = secret;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(TOKEN_HEADER);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            String userName = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            String role = JWT.require(Algorithm.HMAC256(secret)).build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getClaim("role").asString();



            if (userName != null) {
                UserDetails userDetails  = userDetailsServiceImpl.loadUserByUsername(userName);

             Set<SimpleGrantedAuthority> simpleGrantedAuthority = Collections.singleton(new SimpleGrantedAuthority(role));


                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, simpleGrantedAuthority);
            }
        }
        return null;
    }
}
