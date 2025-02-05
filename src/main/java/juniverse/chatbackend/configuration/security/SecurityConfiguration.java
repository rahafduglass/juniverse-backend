package juniverse.chatbackend.configuration.security;


import juniverse.chatbackend.domain.enums.UserRole;
import juniverse.chatbackend.domain.services.SysUserService;
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

                //permission & access control
                .authorizeHttpRequests(request -> request
                        //auth endpoints access control
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()  // Allow authentication controller access without authentication


                         //swagger access control
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()  // Allow Swagger access without authentication


                        //therapist endpoints access control
                        .requestMatchers(
                                "/api/v1/private-chat/therapists/{therapistId}/chats"
                        ).hasAuthority(UserRole.THERAPIST.name())


                        //common Juniverse users endpoints access control
                        .requestMatchers(
                                //private-chat
                                "/api/v1/private-chat/{chatId}",
                                "/api/v1/private-chat/{userId}/messages",
                                "/api/v1/private-chat/message",
                                "/api/v1/private-chat/{chatId}/{userId}/mark-as-read"
                        ).hasAnyAuthority(UserRole.THERAPIST.name(), UserRole.ADMIN.name(),UserRole.STUDENT.name(),UserRole.MODERATOR.name())
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
