package org.springframework.samples.petclinic.feature.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.samples.petclinic.feature.annotation.FeatureRequired;
import org.springframework.samples.petclinic.feature.service.FeatureFlagService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class FeatureGuardAspect {

	private final FeatureFlagService featureFlagService;

	public FeatureGuardAspect(FeatureFlagService featureFlagService) {
		this.featureFlagService = featureFlagService;
	}

	@Before("@annotation(featureRequired)")
	public Object enforceFeatureFlag(ProceedingJoinPoint joinPoint, FeatureRequired featureRequired) throws Throwable {

		String featureName = featureRequired.value();

		System.out.println(featureName);

		Long ownerId = extractOwnerIdFromRequest();

		if (ownerId == null) {
			throw new IllegalStateException("ownerId not found in request path");
		}

		boolean enabled = featureFlagService.isEnabled(featureName, ownerId);

		if (!enabled) {
			return "feature-disabled";
		}

		return joinPoint.proceed();
	}

	private Long extractOwnerIdFromRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attrs == null) {
			return null;
		}

		HttpServletRequest request = attrs.getRequest();

		// Example URI: /owners/5/pets/new
		String uri = request.getRequestURI();

		String[] parts = uri.split("/");

		for (int i = 0; i < parts.length; i++) {
			if ("owners".equals(parts[i]) && i + 1 < parts.length) {
				return Long.valueOf(parts[i + 1]);
			}
		}
		return null;
	}

}