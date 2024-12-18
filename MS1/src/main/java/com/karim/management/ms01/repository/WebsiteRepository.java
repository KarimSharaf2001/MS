package com.karim.management.ms01.repository;

import com.karim.management.ms01.domain.Website;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Website entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long> {}
