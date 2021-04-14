package ShopAppBackend.Security;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

public class FilterJwt extends BasicAuthenticationFilter {



    private static Logger logger = LoggerFactory.getLogger(FilterJwt.class);


    public static void SaveToFile(String save) throws IOException{
        try{
            Files.createFile(Paths.get("C:/ZdjęciaBaza/logi.txt"));
        } catch (IOException e){
            logger.info("ERROR");
        } finally {

            LocalDateTime localDateTime = LocalDateTime.now();
            String date = localDateTime.toString();
            String date1 = date.replace("T"," ");

            StringBuilder sb = new StringBuilder(date1);
            String date2 =  sb.delete(19,29).toString();


            FileWriter fw = new FileWriter("C:/ZdjęciaBaza/logi.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(date2);
            bw.append("  "+save);
            bw.newLine();
            bw.close();

        }
    }



    public FilterJwt(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        UsernamePasswordAuthenticationToken authResult = getAuthenticationByToken(header);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header)throws IOException {


        if (header == null) {

            logger.info("Empty header");
        }
        else {
            try {

                Jws<Claims> claimsJws = Jwts.parser().setSigningKey("GenToken1234".getBytes())
                        .parseClaimsJws(header.replace("Bearer ", ""));

                String username = claimsJws.getBody().get("sub").toString();

                String role = claimsJws.getBody().get("role").toString();
                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = Collections.singleton(new SimpleGrantedAuthority(role));

                return new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
            }catch (SignatureException e)
            {

                String s = "Wrong JWT";
                logger.info(s);
                SaveToFile(s);

            }catch (ExpiredJwtException e)
            {
                String s = "Session is expired";
                logger.error("Session is expired");
                SaveToFile(s);

            }
        }
        return null;
    }
}


