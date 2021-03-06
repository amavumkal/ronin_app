package com.roninswdstudio.ronin_app.springsecurity.filters;

import com.roninswdstudio.ronin_app.springsecurity.entity.RoninUserDetails;
import com.roninswdstudio.ronin_app.springsecurity.services.JwtUtil;
import com.roninswdstudio.ronin_app.springsecurity.services.RoninUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private RoninUserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    public JwtRequestFilter(RoninUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        UserDetails userDetails;
        UsernamePasswordAuthenticationToken userPassAuthToken;
        String jwt = authorizationHeader != null && authorizationHeader.length() > 7 ?
                authorizationHeader.substring(7) : JwtUtil.getJWTFromCookie(request);// jwt private member is also set so getToken will return token from bean
        if(jwt != null && jwtUtil.validateToken(jwt)) {
            userDetails = new RoninUserDetails(jwtUtil.extractUser(jwt));
            userPassAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);
        }

        chain.doFilter(request, response);
    }
}
