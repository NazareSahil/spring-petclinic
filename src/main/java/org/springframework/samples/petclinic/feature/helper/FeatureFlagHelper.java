package org.springframework.samples.petclinic.feature.helper;

import org.springframework.samples.petclinic.feature.service.FeatureFlagService;
import org.springframework.stereotype.Component;

@Component
public class FeatureFlagHelper {

	private final FeatureFlagService featureFlagService;

	public FeatureFlagHelper(FeatureFlagService featureFlagService) {
		this.featureFlagService = featureFlagService;
	}

	public boolean isEnabled(String flagKey, Long userId) {
		return featureFlagService.isEnabled(flagKey, userId);
	}

}