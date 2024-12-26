package org.example.rakkenishokran.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.rakkenishokran.Enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Getter
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
public class User implements UserDetails {

    private long id;
    private String email;
    private String password;
    private String username;
    private String phoneNumber;

    private Role role;

    public User(long id, String email, String password, String username, String phoneNumber) {
    } //TODO

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();  // Use the Role enum's getAuthorities method
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Change to return true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Change to return true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Change to return true
    }

    @Override
    public boolean isEnabled() {
        return true;  // Change to return true
    }
}
