package com.learn.productservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.learn.productservice.model.User;
import com.learn.productservice.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String email = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        email = jwtUtil.getEmail(token);
    }
    if (email != null && jwtUtil.validateToken(token)) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String role = jwtUtil.getRole(token); // e.g., "ADMIN"
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    user.get(), null, authorities);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
    filterChain.doFilter(request, response);
}
}
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.MalformedJwtException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

// import java.io.IOException;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtService jwtService;
//     private final UserDetailsService userDetailsService;

//     public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
//         this.jwtService = jwtService;
//         this.userDetailsService = userDetailsService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//             throws ServletException, IOException {
//         String token = request.getHeader("Authorization");

//         if (token != null && token.startsWith("Bearer ")) {
//             token = token.substring(7);
//             try {
//                 String username = jwtService.extractUsername(token);
//                 if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                     UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                     if (jwtService.isTokenValid(token, userDetails)) {
//                         UsernamePasswordAuthenticationToken authToken =
//                                 new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                         authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                         SecurityContextHolder.getContext().setAuthentication(authToken);
//                     }
//                 }
//             } catch (ExpiredJwtException | MalformedJwtException e) {
//                 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
//                 return;
//             }
//         }
//         chain.doFilter(request, response);
//     }
// }
