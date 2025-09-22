package com.expense.tracker.auth.security;

import com.expense.tracker.user.internal.entity.User;
import com.expense.tracker.user.internal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OAuth2SuccessHandler(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        //String picture = (String) oidcUser.getClaims().get("picture");
        String googleId = oidcUser.getSubject();

        User user = userService.createOrUpdateUser(googleId, email, name);

        String token = jwtProvider.createToken(user.getEmail(), user.getUserId());

        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(Map.of(
                "userId", user.getUserId(),
                "token", token,
                "expiresIn", 3600,
                "email", user.getEmail(),
                "name", user.getName()
        )));
        response.getWriter().flush();
    }
}
