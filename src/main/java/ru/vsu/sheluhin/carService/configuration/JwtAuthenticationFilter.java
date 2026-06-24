package ru.vsu.sheluhin.carService.configuration;

import io.jsonwebtoken.Header;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vsu.sheluhin.carService.entity.RefreshToken;
import ru.vsu.sheluhin.carService.entity.User;
import ru.vsu.sheluhin.carService.exeption.ValidationException;
import ru.vsu.sheluhin.carService.repository.UserRepository;
import ru.vsu.sheluhin.carService.service.CustomUserDetailsService;
import ru.vsu.sheluhin.carService.service.JwtService;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = extractAccessTokenFromCookie(request);

        if (accessToken == null) {
            String path = request.getServletPath();
            if (path.equals("/signup") ||
                    path.equals("/login") ||
                    path.equals("/brands?status=ACTIVE") ||
                    Pattern.compile("^/models/\\d+\\?status=ACTIVE$").matcher(path).matches() ||
                    Pattern.compile("^/services/\\d+\\?status=ACTIVE$").matcher(path).matches()) {
                filterChain.doFilter(request, response);
                return;
            }
            throw new ValidationException("NOT_ACCESS_TOKEN");
        }

        if(!jwtService.isTokenValid(accessToken)) {
            String phoneNumber = jwtService.extractPhoneNumber(accessToken);
            Optional<RefreshToken> refreshToken = jwtService.findRefreshTokenByPhoneNumber(phoneNumber);

            if (refreshToken.isEmpty())
                throw new ValidationException("NOT_REFRESH_TOKEN");

            if(!jwtService.isTokenValid(refreshToken.get().getToken())) {
                jwtService.deleteRefreshTokenByPhoneNumber(refreshToken.get().getPhoneNumber());
                throw new ValidationException("NOT_VALID_REFRESH_TOKEN");
            }
            Optional<User> user = userRepository.findUserByPhoneNumber(phoneNumber);
            accessToken = jwtService.generateToken(user.get());
        }

        Cookie cookie = new Cookie("token", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);

        UserDetails user = userDetailsService.loadUserByUsername(jwtService.extractPhoneNumber(accessToken));
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    private String extractAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
