package ru.edu.FirstSecurityApp.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.edu.FirstSecurityApp.security.JWTUtil;
import ru.edu.FirstSecurityApp.services.PersonDetailsService;

@Component
// OncePerRequestFilter - проверка на каждый запрос
public class JWTFilter extends OncePerRequestFilter {

  private final JWTUtil jwtUtil;

  private final PersonDetailsService personDetailsService;

  @Autowired
  public JWTFilter(JWTUtil jwtUtil, PersonDetailsService personDetailsService) {
    this.jwtUtil = jwtUtil;
    this.personDetailsService = personDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
      String jwt = authHeader.substring(7); // все после Bearer

      if (jwt.isBlank()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer header");
      } else {
        try {
          String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);

          // авторизуем пользователя
          UserDetails userDetails = personDetailsService.loadUserByUsername(username);
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

          // если не в контексте, то добавляем
          if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
          }
        } catch (JWTVerificationException e) {
          response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
        }
      }
    }

    // передаем фильтр дальше по цепочке
    filterChain.doFilter(request, response);
  }

}
