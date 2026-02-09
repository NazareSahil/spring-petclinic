package org.springframework.samples.petclinic.feature.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.feature.model.FeatureFlag;

public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, Long> {

	Optional<FeatureFlag> findByFeatureKey(String featureKey);

}