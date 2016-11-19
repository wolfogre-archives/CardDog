package com.wolfogre.dao;

import com.wolfogre.domain.TerminalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wolfogre on 11/20/16.
 */
public interface TerminalRepository extends JpaRepository<TerminalEntity, String> {
}
