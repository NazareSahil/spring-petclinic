package org.springframework.samples.petclinic.admin.dto;

import org.springframework.samples.petclinic.feature.model.FeatureFlag;

public record FeatureFlagResponse(String featureKey, boolean enabled, int rolloutPercentage) {

	public static FeatureFlagResponse from(FeatureFlag flag) {
		return new FeatureFlagResponse(flag.getFeatureKey(), flag.isEnabled(), flag.getRolloutPercentage());
	}
}