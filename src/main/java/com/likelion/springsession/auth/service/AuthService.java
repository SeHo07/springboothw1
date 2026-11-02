package com.likelion.springsession.auth.service;

import com.likelion.springsession.auth.dto.LoginRequest;
import com.likelion.springsession.auth.dto.SignUpRequest;
import com.likelion.springsession.auth.dto.SignUpResponse;
import com.likelion.springsession.auth.dto.TokenResponse;
import com.likelion.springsession.auth.exception.DuplicateEmailException;
import com.likelion.springsession.auth.exception.InvalidCredentialsException;
import com.likelion.springsession.security.JwtProvider;
import com.likelion.springsession.user.entity.User;
import com.likelion.springsession.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = userRepository.save(
                new User(request.getEmail(), encodedPassword, request.getNickname()));

        return new SignUpResponse(user.getId(), user.getEmail(), user.getNickname());
    }
    private final JwtProvider jwtProvider;   // ← 필드 추가

    @Transactional
    public TokenResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtProvider.createAccessToken(user.getId());

        return new TokenResponse(accessToken);
    }
}

