package com.builtinaweekendapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.builtinaweekendapi.model.enums.Permission.*;
import static com.builtinaweekendapi.model.enums.Role.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] WHITE_LISTED_URLs = {
            "/api/v1/auth/**",
            "/api/v1/home"
    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* Note: Always include both the general _CRUD permission and the specific permission related to the HTTP method.
         * For instance, for the GET method, include both USER_CRUD and USER_READ, or POST_CRUD and POST_READ.
         * This approach accommodates users with either complete _CRUD permissions or specific CRUD permissions (_CREATE, _READ, _UPDATE, _DELETE).
         * This ensures that both User_A with POST_CRUD and User_B with POST_READ can access the GET endpoint.
         */
        /* Note: Defining the path in both requestMatcher and PreAuthorize at the method-level will lead to a 403 Forbidden error.
         * It is advised to define it EITHER in requestMatcher OR at the method-level using PreAuthorize, but NOT BOTH.
         * For example, using @PreAuthorize("hasAuthority('RESOURCE_READ')") and .requestMatchers(...hasAuthority(RESOURCE_READ.name())
         * simultaneously will result in a 403 Forbidden error.
         * Additionally, utilizing both class-level PreAuthorize and requestMatcher appears to function correctly.
         * However, in cases of conflicting values, a 403 Forbidden error will be triggered.
         */
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                    req.requestMatchers(WHITE_LISTED_URLs)
                            .permitAll()
                            .requestMatchers("/api/v1/management/**").hasAnyRole(SUPER_ADMIN.name(), EDITOR.name(), REVIEWER.name(), MODERATOR.name())
                            .requestMatchers(HttpMethod.GET, "/api/v1/management/**").hasAnyAuthority(USER_CRUD.name(), USER_READ.name(), POST_CRUD.name(), POST_READ.name(), COMMENT_CRUD.name())
                            .requestMatchers(HttpMethod.POST, "/api/v1/management/**").hasAnyAuthority(USER_CRUD.name(), USER_CREATE.name(), POST_CRUD.name(), POST_CREATE.name(), COMMENT_CRUD.name())
                            .requestMatchers(HttpMethod.PUT, "/api/v1/management/**").hasAnyAuthority(USER_CRUD.name(), USER_UPDATE.name(), POST_CRUD.name(), POST_UPDATE.name(), COMMENT_CRUD.name())
                            .requestMatchers(HttpMethod.PATCH, "/api/v1/management/**").hasAnyAuthority(USER_CRUD.name(), USER_UPDATE.name(), POST_CRUD.name(), POST_UPDATE.name(), COMMENT_CRUD.name())
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/management/**").hasAnyAuthority(USER_CRUD.name(), USER_DELETE.name(), POST_CRUD.name(), POST_DELETE.name(), COMMENT_CRUD.name())

                            .requestMatchers("/api/v1/admin/**").hasRole(SUPER_ADMIN.name())
                            .requestMatchers(HttpMethod.GET, "/api/v1/admin/**").hasAuthority(RESOURCE_READ.name())
                            .requestMatchers(HttpMethod.POST, "/api/v1/admin/**").hasAuthority(RESOURCE_CREATE.name())
                            .requestMatchers(HttpMethod.PUT, "/api/v1/admin/**").hasAuthority(RESOURCE_UPDATE.name())
                            .requestMatchers(HttpMethod.PATCH, "/api/v1/admin/**").hasAuthority(RESOURCE_UPDATE.name())
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/admin/**").hasAuthority(RESOURCE_DELETE.name())
                            .anyRequest()
                            .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                );

        return http.build();
    }

}
