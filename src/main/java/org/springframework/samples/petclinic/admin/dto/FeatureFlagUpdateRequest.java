package org.springframework.samples.petclinic.admin.dto;

public record FeatureFlagUpdateRequest(boolean enabled, int rolloutPercentage) {
}