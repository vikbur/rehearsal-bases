package org.vikbur.services;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.vikbur.jwt.JwtAuthentication;
import org.vikbur.jwt.JwtProvider;
import org.vikbur.models.User;
import org.vikbur.models.requests.AuthRequest;
import org.vikbur.models.responses.AuthResponse;
import org.vikbur.repositories.UserCrudRepository;
import org.vikbur.utils.HashUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserCrudRepository userCrudRepository;
    private final JwtProvider jwtProvider;
    private final Gson gson = new Gson();

    public ResponseEntity<String> login(AuthRequest authRequest) {
        try {
            User user = userCrudRepository.findByLogin(authRequest.getLogin()).orElse(null);
            if (user == null){
                throw new IllegalArgumentException(String.format("User '%s' not found", authRequest.getLogin()));
            }

            if (user.getPassword().equals(HashUtil.getHashString(authRequest.getPassword()+user.getSalt()))){
                AuthResponse response = new AuthResponse();
                response.setAccessToken(jwtProvider.generateAccessToken(user));
                response.setRefreshToken(jwtProvider.generateRefreshToken(user));
                return ResponseEntity.ok(gson.toJson(response));
            } else {
                throw new IllegalArgumentException("Incorrect password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
