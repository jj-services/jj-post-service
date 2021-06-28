package austral.ingsis.jjpostservice.connection.auth;

import austral.ingsis.jjpostservice.dto.UserDto;
import austral.ingsis.jjpostservice.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpHeaders;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Value("${users_service.host}")
    private String usersServiceHost;

    @Value("${users_service.port}")
    private String usersServicePort;

    private final String url = "http://" + this.usersServiceHost + ":" + this.usersServicePort + "/api/users/authenticateUser";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getCookies() == null) throw new AuthenticationException("Cookies should not be null");
            Optional<Cookie> optCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(SecurityConstants.COOKIE_NAME)).findFirst();
            if (optCookie.isEmpty()) throw new AuthenticationException("Cookie should not be empty");
            UserDto userDto = this.validateUserAuthentication(optCookie.get().getValue());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDto, optCookie.get().getValue());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e){
            response.sendError(401, "Cannot set user authentication: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);

    }

    private UserDto validateUserAuthentication(String token) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI getUserUri = new URI(url);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserDto> response = restTemplate.exchange(getUserUri, HttpMethod.GET, httpEntity, UserDto.class);
        if (response.getStatusCodeValue() != 200) throw new AuthenticationException("Server could not authenticate request");
        return response.getBody();
    }
}

