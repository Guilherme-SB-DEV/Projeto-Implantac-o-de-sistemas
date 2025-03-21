// package br.com.example.wallpark.security;

// import java.io.IOException;
// import java.util.Collection;
// import java.util.Collections;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.security.oauth2.jwt.Jwt;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.JwtException;
// import org.springframework.web.filter.OncePerRequestFilter;
// import com.nimbusds.jwt.JWT;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// public class JwtFilter extends OncePerRequestFilter {
//     private final JwtDecoder decoder;

//     public JwtFilter(JwtDecoder decoder) {
//         this.decoder = decoder;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//     throws ServletException, IOException {
//         String token = getToken(request);
//         if(token != null){
//             try {
//                 Jwt jwt = decoder.decode(token);
//                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                     jwt.getSubject(), null, Collections.emptyList()
//                 );
//                 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             } catch (Exception e) {
//                 SecurityContextHolder.getContext();
//             }
//         }
        
//     }

//     public String getToken(HttpServletRequest request) {
//         String token = request.getHeader("Authorization");
//         if (token != null && token.startsWith("Bearer ")) {
//             return token.substring(7);
//         }
//         return null;

//     }

// }
