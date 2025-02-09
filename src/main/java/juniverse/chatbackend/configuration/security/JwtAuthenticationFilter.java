package juniverse.chatbackend.configuration.security;


import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import juniverse.chatbackend.domain.services.security.JwtService;
import juniverse.chatbackend.domain.services.security.SysUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final SysUserDetailsService sysUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        //check if header valid
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {

            //passes the request and response to the next filter in the chain after the JWT FILTER
            filterChain.doFilter(request, response);
            return;
        }


        //extract the following from header
        jwt = authHeader.substring(7); //token
        //extract the username from the JWT (which was issued earlier during login).
        userName = jwtService.extractUserName(jwt);



        //Checking if the User is Not Already Authenticated
        if (StringUtils.isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) { // second condition means user is not logged in yet otherwise the context will hold user details and won't be null

            //Loading User Details from Database
            UserDetails userDetails = sysUserDetailsService.userDetailsService().loadUserByUsername(userName);

            // Validating the JWT Token
            if(jwtService.isTokenValid(jwt, userDetails)){

                //Creates a new empty security context
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                //Creating an Authentication Token
                UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //Setting Authentication Details  --  additional request details (like IP address, session ID) to the authentication token.
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Storing the Authentication in Security Context
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }

        }

        //pass to next chain filter
        filterChain.doFilter(request, response);

    }
}
