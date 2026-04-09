package com.safwantech.hms_backend.security;

import com.safwantech.hms_backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserUtil {

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication is required");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User user)) {
            throw new AccessDeniedException("Authenticated user could not be resolved");
        }

        return user;
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public String getCurrentUserRole() {
        User user = getCurrentUser();
        return user.getRole() != null ? user.getRole().name() : null;
    }

    public Long getCurrentClinicId() {
        User user = getCurrentUser();

        if (user.getClinic() == null || user.getClinic().getId() == null) {
            throw new AccessDeniedException("This endpoint requires a clinic-scoped user");
        }

        return user.getClinic().getId();
    }
}
