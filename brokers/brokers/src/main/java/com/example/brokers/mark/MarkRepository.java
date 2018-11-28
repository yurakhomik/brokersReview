package com.example.brokers.mark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    Optional<Mark> findByUserIdAndBrokerId(Long userId, Long brokerId);

    List<Mark> findByBrokerId(Long brokerId);
}
