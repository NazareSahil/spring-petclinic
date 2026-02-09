package org.springframework.samples.petclinic.admin.controller;

import org.springframework.samples.petclinic.feature.model.FeatureBlacklist;
import org.springframework.samples.petclinic.feature.repository.FeatureBlacklistRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/flags/blacklist")
public class AdminBlacklistController {

	private final FeatureBlacklistRepository blacklistRepository;

	public AdminBlacklistController(FeatureBlacklistRepository blacklistRepository) {
		this.blacklistRepository = blacklistRepository;
	}

	@PostMapping("/{featureKey}/{userKey}")
	public void addToBlacklist(@PathVariable String featureKey, @PathVariable Long userKey) {

		blacklistRepository.save(new FeatureBlacklist(featureKey, userKey));
	}

	@DeleteMapping("/{featureKey}/{userKey}")
	public void removeFromBlacklist(@PathVariable String featureKey, @PathVariable Long userKey) {

		blacklistRepository.deleteAll(blacklistRepository.findAll()
			.stream()
			.filter(b -> b.getFeatureKey().equals(featureKey) && b.getUserId().equals(userKey))
			.toList());
	}

}