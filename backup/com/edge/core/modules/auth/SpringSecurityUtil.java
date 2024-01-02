package com.edge.core.modules.auth;

import com.edge.repositories.EdgeUserDetailsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Data
public class SpringSecurityUtil {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EdgeUserDetailsRepository edgeUserDetailsRepo;

    private static SpringSecurityUtil INSTANCE;

    public static String encodePassword(String password) {
        return INSTANCE.getPasswordEncoder().encode(password);
    }

    public SpringSecurityUtil() {
        INSTANCE = this;
    }

    public static boolean isAdmin() {
        boolean result = false;

        List<String> roles = getLoggedInRoles();

        if (roles != null) {
            for (String role : roles) {
                if (role.contains("ADMIN")) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    public static List<String> getLoggedInRoles() {
        List<String> roles = null;
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (authorities != null) {
            for (GrantedAuthority grantedAuthority : authorities) {
                roles.add(new String(grantedAuthority.getAuthority().getBytes()));
            }
        }
        return roles;
    }

    public static String getLoggedInInternalId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            return authentication.getName();
        }
        return null;
    }


}
