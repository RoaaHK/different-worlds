package com.example.DifferentWorlds.UserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Component("accessChecker")
public class AccessChecker {

    private static final String ADMIN = "ADMIN";
    private static final String CUSTOMER = "CUSTOMER";
    private static final String AUTHOR = "AUTHOR";

    public boolean canAccessCustomer(Integer customerId) {
        return canAccess(CUSTOMER, user -> Objects.equals(user.getId(), customerId));
    }

    public boolean canAccessAuthor(Integer authorId) {
        return canAccess(AUTHOR, user -> Objects.equals(user.getId(), authorId));
    }

    public boolean canAccessCustomerByUserName(String userName) {
        return canAccess(CUSTOMER, user -> Objects.equals(user.getUsername(), userName));
    }

    public boolean canAccessAuthorByUserName(String userName) {
        return canAccess(AUTHOR, user -> Objects.equals(user.getUsername(), userName));
    }

    private boolean canAccess(String ownerType, Predicate<CustomUserDetails> isOwner) {
        return currentUser()
                .map(user -> ADMIN.equals(user.getUserType())
                        || (ownerType.equals(user.getUserType()) && isOwner.test(user)))
                .orElse(false);
    }

    private Optional<CustomUserDetails> currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}