package org.springframework.samples.petclinic.feature.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "feature_blacklist")
public class FeatureBlacklist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "feature_key", nullable = false)
	private String featureKey;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	protected FeatureBlacklist() {
	}

	public FeatureBlacklist(String featureKey, Long userId) {
		this.featureKey = featureKey;
		this.userId = userId;
	}

	public String getFeatureKey() {
		return featureKey;
	}

	public Long getUserId() {
		return userId;
	}

}