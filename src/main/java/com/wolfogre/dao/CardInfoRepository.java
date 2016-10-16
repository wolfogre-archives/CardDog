package com.wolfogre.dao;

import com.wolfogre.domain.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wolfogre on 10/16/16.
 */
public interface CardInfoRepository extends JpaRepository<TransactionEntity, String> {
}
