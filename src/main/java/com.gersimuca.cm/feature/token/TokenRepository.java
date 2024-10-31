package com.gersimuca.cm.feature.token;

import com.gersimuca.cm.common.repository.BaseRepository;
import com.gersimuca.cm.feature.user.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends BaseRepository<TokenEntity, Long> {
    @Query("SELECT t FROM TokenEntity t JOIN t.user u " +
            "WHERE u.id = :id AND (t.expired = false OR t.revoked = false)")
    List<TokenEntity> findAllValidTokenByUser(Long id);

    Optional<TokenEntity> findByToken(String token);

    void deleteTokensByUser(UserEntity user);
}
