package org.springframework.samples.petclinic.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.feature.model.FeatureBlacklist;

public interface FeatureBlacklistRepository extends JpaRepository<FeatureBlacklist, Long> {

	boolean existsByFeatureKeyAndUserId(String featureKey, Long userId);

}