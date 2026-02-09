package org.springframework.samples.petclinic.feature.service;

import org.springframework.samples.petclinic.feature.model.FeatureFlag;
import org.springframework.samples.petclinic.feature.repository.FeatureBlacklistRepository;
import org.springframework.samples.petclinic.feature.repository.FeatureFlagRepository;
import org.springframework.samples.petclinic.feature.repository.FeatureWhitelistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeatureFlagService {

	private final FeatureFlagRepository flagRepository;

	private final FeatureWhitelistRepository whitelistRepository;

	private final FeatureBlacklistRepository blacklistRepository;

	public FeatureFlagService(FeatureFlagRepository flagRepository, FeatureWhitelistRepository whitelistRepository,
			FeatureBlacklistRepository blacklistRepository) {
		this.flagRepository = flagRepository;
		this.whitelistRepository = whitelistRepository;
		this.blacklistRepository = blacklistRepository;
	}

	/**
	 * FINAL evaluation logic
	 */
	@Transactional(readOnly = true)
	public boolean isEnabled(String featureKey, Long userId) {

		FeatureFlag flag = flagRepository.findByFeatureKey(featureKey)
			.orElseThrow(() -> new IllegalStateException("Feature not found: " + featureKey));

		// 1️⃣ Global kill switch
		if (!flag.isEnabled()) {
			return false;
		}

		// 2️⃣ Blacklist (hard deny)
		if (blacklistRepository.existsByFeatureKeyAndUserId(featureKey, userId)) {
			return false;
		}

		// 3️⃣ Whitelist (hard allow)
		if (whitelistRepository.existsByFeatureKeyAndUserId(featureKey, userId)) {
			return true;
		}

		// 4️⃣ Percentage rollout
		return passesRollout(flag.getRolloutPercentage(), userId);
	}

	private boolean passesRollout(int percentage, Long userId) {
		if (percentage >= 100) {
			return true;
		}
		if (percentage <= 0) {
			return false;
		}
		return Math.abs(userId.hashCode() % 100) < percentage;
	}

	/* ================= ADMIN CONTROLS ================= */

	public void enableGlobally(String featureKey) {
		update(featureKey, true, 100);
	}

	public void disableGlobally(String featureKey) {
		update(featureKey, false, 0);
	}

	public void updateRollout(String featureKey, int percentage) {
		update(featureKey, true, percentage);
	}

	private void update(String featureKey, boolean enabled, int rollout) {
		FeatureFlag flag = flagRepository.findByFeatureKey(featureKey).orElseThrow();
		flag.setEnabled(enabled);
		flag.setRolloutPercentage(rollout);
		flagRepository.save(flag);
	}

}