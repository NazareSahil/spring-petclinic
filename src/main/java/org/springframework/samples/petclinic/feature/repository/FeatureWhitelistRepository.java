package org.springframework.samples.petclinic.feature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.feature.model.FeatureWhitelist;

public interface FeatureWhitelistRepository extends JpaRepository<FeatureWhitelist, Long> {

	boolean existsByFeatureKeyAndUserId(String featureKey, Long userId);

}