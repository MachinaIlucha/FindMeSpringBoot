package com.findme.findme.util;

import com.findme.findme.entity.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static long getAuthorizedUserId() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user instanceof UserPrincipal ? ((UserPrincipal) user).user().getId() : 0;
    }
}
