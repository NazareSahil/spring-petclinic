package org.springframework.samples.petclinic.admin.controller;

import org.springframework.samples.petclinic.feature.model.FeatureWhitelist;
import org.springframework.samples.petclinic.feature.repository.FeatureWhitelistRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/flags/whitelist")
public class AdminWhitelistController {

	private final FeatureWhitelistRepository whitelistRepository;

	public AdminWhitelistController(FeatureWhitelistRepository whitelistRepository) {
		this.whitelistRepository = whitelistRepository;
	}

	@PostMapping("/{featureKey}/{userKey}")
	public void addToWhitelist(@PathVariable String featureKey, @PathVariable Long userKey) {

		whitelistRepository.save(new FeatureWhitelist(featureKey, userKey));
	}

	@DeleteMapping("/{featureKey}/{userKey}")
	public void removeFromWhitelist(@PathVariable String featureKey, @PathVariable Long userKey) {

		whitelistRepository.deleteAll(whitelistRepository.findAll()
			.stream()
			.filter(w -> w.getFeatureKey().equals(featureKey) && w.getUserId().equals(userKey))
			.toList());
	}

}