package com.gersimuca.cm.configuration;

import com.gersimuca.cm.common.AuthenticationProvider;
import com.gersimuca.cm.common.exception.AuthenticationNotSupportedException;
import com.gersimuca.cm.feature.user.UserEntity;
import com.gersimuca.cm.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Component("auditorProvider")
@RequiredArgsConstructor
public class AuditorProvider implements AuditorAware<UserEntity> {
    private final UserRepository userRepository;

    @Override
    @NonNull
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<UserEntity> getCurrentAuditor() {
        var authentication = AuthenticationProvider.getAuthenticationFromContext();
        if (authentication == null) {
            return userRepository.findByUsername("System");
        }
        var username = getUsername(authentication);
        return userRepository.findByUsername(username);
    }

    private String getUsername(final Authentication authentication) {
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("preferred_username");
        }
        throw new AuthenticationNotSupportedException();
    }
}
