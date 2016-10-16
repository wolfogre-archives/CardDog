package com.wolfogre.dao;

import com.wolfogre.domain.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wolfogre on 10/16/16.
 */
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
    public List<TransactionEntity> findByTransdate(String transdate);
}
