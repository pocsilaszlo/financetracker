package hu.elte.financetracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SupabaseAuthFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    private final String supabaseUrl;
    private final String supabaseAnonKey;

    public SupabaseAuthFilter(RestTemplate restTemplate, @Value("${supabase.url}") String supabaseUrl, @Value("${supabase.anon.key}") String supabaseAnonKey) {
        this.restTemplate = restTemplate;
        this.supabaseUrl = supabaseUrl;
        this.supabaseAnonKey = supabaseAnonKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromHeader(request);

        if (token != null) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                headers.set("apikey", supabaseAnonKey); // Fontos: Az anon kulcsot is be kell állítani
                HttpEntity<String> entity = new HttpEntity<>(headers);

                String url = supabaseUrl + "/auth/v1/user";

                ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                        url, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    Map<String, Object> userDetails = responseEntity.getBody();

                    // Szerepkörök kinyerése a user_metadata-ból
                    Map<String, Object> appMetadata = (Map<String, Object>) userDetails.get("app_metadata");
                    List<String> roles = (List<String>) appMetadata.getOrDefault("roles", List.of());

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList());

                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // Hibakezelés (pl. érvénytelen token)
                    System.err.println("Supabase authentication failed: " + responseEntity.getStatusCode());
                }

            } catch (Exception e) {
                // Hibakezelés (pl. hálózati hiba)
                e.printStackTrace();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }


}