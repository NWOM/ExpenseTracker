package org.example.repository;

import org.example.entities.RefreshTokens;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokens,Integer>
{
    Optional<RefreshTokens> findByToken(String token);

}
