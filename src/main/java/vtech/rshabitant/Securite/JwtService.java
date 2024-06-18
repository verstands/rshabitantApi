package vtech.rshabitant.Securite;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import vtech.rshabitant.Models.Agent;
import vtech.rshabitant.Services.CustomUserDetailsService;

@AllArgsConstructor
@Service
public class JwtService {
    private static final String ENCRYPTION_KEY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+[]{}|;:,.<>?"; // 64 caractères
    private final CustomUserDetailsService customUserDetailsService;

    public Map<String, String> generate(String username) {
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
        if (!(userDetails instanceof Agent)) {
            throw new UsernameNotFoundException("L'utilisateur n'est pas un agent");
        }
        Agent agent = (Agent) userDetails;
        
        String token = generateTokenForAgent(agent);
        return Map.of("Bearer", token);
    }

    private String generateTokenForAgent(Agent agent) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("nom", agent.getNom());
        claims.put("email", agent.getEmail());

        final long currentTimeMillis = System.currentTimeMillis();
        final Date now = new Date(currentTimeMillis);
        final Date expiryDate = new Date(currentTimeMillis + 1000 * 60 * 60 * 10); // 10 heures de validité

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(agent.getEmail())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(getKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    private Key getKey() {
        final byte[] decodedKey = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decodedKey);
    }
}
