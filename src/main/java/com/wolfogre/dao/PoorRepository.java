package com.wolfogre.dao;

import com.wolfogre.domain.PoorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by wolfogre on 10/17/16.
 */
public interface PoorRepository extends JpaRepository<PoorEntity, String> {
}
