package org.example.nuiifo0d.repository;

import org.example.nuiifo0d.entity.RefreshTokensUsed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RTokensUsedRepository extends JpaRepository<RefreshTokensUsed, Long>{
    void deleteByUserId(Long userId);
}
