package juniverse.configuration.security;


import juniverse.application.handlers.CustomAccessDeniedHandler;
import juniverse.application.handlers.CustomAuthenticationEntryPoint;
import juniverse.domain.enums.UserRole;
import juniverse.domain.services.security.SysUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final SysUserDetailsService sysUserDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors
                        .configurationSource(request -> {
                            org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
                            config.setAllowedOrigins(List.of("*"));
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            config.setAllowedHeaders(List.of("*"));
                            config.setAllowCredentials(false);
                            return config;
                        })
                )
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .authorizeHttpRequests(request -> request
                        //no authentication needed
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()


                        //therapist
                        .requestMatchers(
                                //chats
                                "/api/v1/private-chat/{chatId}/allMessages",
                                "/api/v1/private-chat/allTherapistChats",
                                "/api/v1/private-chat/messageFromTherapist",
                                "/api/v1/therapist-chat/**"
                        ).hasAnyAuthority(UserRole.THERAPIST.name())


                        //admin
                        .requestMatchers(
                                HttpMethod.POST
                                , "api/v1/folder"
                                , "api/v1/news"
                                , "api/v1/events"

                        ).hasAnyAuthority(UserRole.ADMIN.name())
                        .requestMatchers(
                                HttpMethod.PUT
                                , "api/v1/folder/{folderId}"
                                , "api/v1/folder/{folderId}/description"
                                , "api/v1/folder/{folderId}/name"
                                , "api/v1/news/{newsId}"
                        ).hasAnyAuthority(UserRole.ADMIN.name())
                        .requestMatchers(
                                HttpMethod.DELETE
                                , "api/v1/folder/{folderId}"
                                , "api/v1/news/{newsId}"
                                , "api/v1/events/{eventId}"
                        ).hasAnyAuthority(UserRole.ADMIN.name())


                        //moderator
                        .requestMatchers(
                                "api/v1/files/pending/{folderId}",
                                "api/v1/files/{fileId}/reject",
                                "api/v1/files/{fileId}/accept"

                        ).hasAnyAuthority(UserRole.MODERATOR.name())


                        //admin, mod only
                        .requestMatchers(
                                HttpMethod.DELETE
                                , "api/v1/files/file/{fileId}"
                        ).hasAnyAuthority(UserRole.ADMIN.name(), UserRole.MODERATOR.name())


                        //admin, mod, student only
                        .requestMatchers(
                                //chats
                                "/api/v1/private-chat/allMessages",
                                "/api/v1/private-chat/messageToTherapist",
                                "/api/v1/private-chat"
                        ).hasAnyAuthority(UserRole.STUDENT.name(), UserRole.MODERATOR.name(), UserRole.ADMIN.name())


                        //all users
                        .requestMatchers(
                                HttpMethod.GET
                                , "api/v1/news"
                                , "api/v1/events"
                        )
                        .hasAnyAuthority(UserRole.STUDENT.name(), UserRole.MODERATOR.name(), UserRole.ADMIN.name(), UserRole.THERAPIST.name())
                        .requestMatchers(
                                HttpMethod.PUT
                                , "/api/v1/public-chat/{messageId}")
                        .hasAnyAuthority(UserRole.STUDENT.name(), UserRole.MODERATOR.name(), UserRole.ADMIN.name(), UserRole.THERAPIST.name())
                        .requestMatchers(
                                //chats
                                "/api/v1/private-chat/{chatId}/read",
                                "/api/v1/public-chat/message",
                                "/api/v1/public-chat/messages",
                                "/api/v1/public-chat/{messageId}",

                                //profile
                                "/api/v1/sys-user/profile-picture",
                                "/api/v1/sys-user/cover-picture",
                                "/api/v1/sys-user/bio",
                                "/api/v1/sys-user/profile",
                                "/api/v1/sys-user/profile-and-cover-picture",

                                //files
                                "/api/v1/files/file/{fileId}",
                                "/api/v1/files/{folderId}",
                                "/api/v1/files"


                        ).hasAnyAuthority(UserRole.STUDENT.name(), UserRole.MODERATOR.name(), UserRole.ADMIN.name(), UserRole.THERAPIST.name())

                        .requestMatchers(
                                HttpMethod.GET
                                , "/api/v1/folder")
                        .hasAnyAuthority(UserRole.STUDENT.name(), UserRole.MODERATOR.name(), UserRole.ADMIN.name(), UserRole.THERAPIST.name())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        //set the UserDetailsService to be used by the DaoAuthenticationProvider
        authenticationProvider.setUserDetailsService(sysUserDetailsService.userDetailsService());

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
