package org.example.rakkenishokran.Enums;

import org.example.rakkenishokran.Authorization.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// Ensure Lombok is properly set up in the project by adding it as a dependency in your build file.
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.example.rakkenishokran.Authorization.Permission.*;

@RequiredArgsConstructor
public enum Role {
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE)),

    LOT_MANAGER(Set.of(
            LOT_MANAGER_READ,
            LOT_MANAGER_UPDATE,
            LOT_MANAGER_DELETE,
            LOT_MANAGER_CREATE)),

    DRIVER(Set.of(
            DRIVER_READ,
            DRIVER_UPDATE,
            DRIVER_DELETE,
            DRIVER_CREATE)),



    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
