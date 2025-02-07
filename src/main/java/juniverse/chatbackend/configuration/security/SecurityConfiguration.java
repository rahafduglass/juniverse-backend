package juniverse.chatbackend.configuration.security;


import juniverse.chatbackend.application.handlers.CustomAccessDeniedHandler;
import juniverse.chatbackend.application.handlers.CustomAuthenticationEntryPoint;
import juniverse.chatbackend.domain.enums.UserRole;
import juniverse.chatbackend.domain.services.security.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final SysUserService sysUserService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Disables Cross-Site Request Forgery (CSRF) protection.
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                //permission & access control
                .authorizeHttpRequests(request -> request


                        //accessible without authentication
                        .requestMatchers(
                                //authentication controller
                                "/api/v1/auth/**",
                                //swagger access
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()
                        // THERAPIST endpoints
                        .requestMatchers(
                                //private-chat
                                "/api/v1/private-chat/{chatId}/allMessages",
                                "/api/v1/private-chat/allTherapistChats",
                                "/api/v1/private-chat/messageFromTherapist"

                        ).hasAnyAuthority(UserRole.THERAPIST.name())
                        // COMMON MODERATOR, STUDENT, ADMIN endpoints
                        .requestMatchers(
                                //private-chat
                                "/api/v1/private-chat/allMessages",
                                "/api/v1/private-chat/messageToTherapist",
                                "/api/v1/private-chat"

                        ).hasAnyAuthority(UserRole.STUDENT.name(),UserRole.MODERATOR.name(),UserRole.ADMIN.name())

                        //COMMON THERAPIST, MODERATOR, STUDENT, ADMIN endpoints
                        .requestMatchers(
                                //private-chat
                                "/api/v1/private-chat/{chatId}/read"
                        ).hasAnyAuthority(UserRole.STUDENT.name(),UserRole.MODERATOR.name(),UserRole.ADMIN.name(),UserRole.THERAPIST.name())

                        .anyRequest().authenticated())

                // application will not use HTTP sessions to store authentication details -- we'll use JWT
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Adds a custom filter (jwtAuthenticationFilter) before the UsernamePasswordAuthenticationFilter
                // (which is the default filter for authentication in Spring Security).
                // This filter will be responsible for handling JWT-based authentication.
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );
        //
        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        //set the UserDetailsService to be used by the DaoAuthenticationProvider
        authenticationProvider.setUserDetailsService(sysUserService.userDetailsService());

        //set the password encoder to be used by the authentication provider
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        //returning the provider
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
