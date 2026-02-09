package org.springframework.samples.petclinic.feature.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "feature_flag")
public class FeatureFlag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "feature_key", nullable = false, unique = true)
	private String featureKey;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@Column(name = "rollout_percentage", nullable = false)
	private int rolloutPercentage;

	protected FeatureFlag() {
	}

	public FeatureFlag(String featureKey) {
		this.featureKey = featureKey;
		this.enabled = false;
		this.rolloutPercentage = 0;
	}

	public Long getId() {
		return id;
	}

	public String getFeatureKey() {
		return featureKey;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getRolloutPercentage() {
		return rolloutPercentage;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setRolloutPercentage(int rolloutPercentage) {
		this.rolloutPercentage = rolloutPercentage;
	}

}