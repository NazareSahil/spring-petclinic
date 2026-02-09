package org.springframework.samples.petclinic.admin.controller;

import java.util.List;

import org.springframework.samples.petclinic.admin.dto.FeatureFlagResponse;
import org.springframework.samples.petclinic.admin.dto.FeatureFlagUpdateRequest;
import org.springframework.samples.petclinic.feature.repository.FeatureFlagRepository;
import org.springframework.samples.petclinic.feature.service.FeatureFlagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/flags")
public class AdminFeatureFlagController {

	private final FeatureFlagService featureFlagService;

	private final FeatureFlagRepository featureFlagRepository;

	public AdminFeatureFlagController(FeatureFlagService featureFlagService,
			FeatureFlagRepository featureFlagRepository) {
		this.featureFlagService = featureFlagService;
		this.featureFlagRepository = featureFlagRepository;
	}

	/**
	 * View all feature flags
	 */
	@GetMapping
	public List<FeatureFlagResponse> getAllFlags() {
		return featureFlagRepository.findAll().stream().map(FeatureFlagResponse::from).toList();
	}

	/**
	 * Enable / disable / rollout update
	 */
	@PutMapping("/{featureKey}")
	public void updateFlag(@PathVariable String featureKey, @RequestBody FeatureFlagUpdateRequest request) {

		if (!request.enabled()) {
			featureFlagService.disableGlobally(featureKey);
			return;
		}

		featureFlagService.updateRollout(featureKey, request.rolloutPercentage());
	}

}