package com.emmadev.bungalows.repository;

import com.emmadev.bungalows.entity.Confidencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfidencialRepository extends JpaRepository<Confidencial, Long> {
}
