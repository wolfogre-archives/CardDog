package com.wolfogre.dao;

import com.wolfogre.domain.EatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wolfogre on 10/16/16.
 */
public interface EatRepository extends JpaRepository<EatEntity, String> {
}
