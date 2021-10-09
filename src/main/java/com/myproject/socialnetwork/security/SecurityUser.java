package com.myproject.socialnetwork.security;

import com.myproject.socialnetwork.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class SecurityUser implements UserDetails {

    @Getter(onMethod_ = {@Override})
    private final String username;

    @Getter(onMethod_ = {@Override})
    private final String password;

    private final List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserDetails fromUser(User user) {

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(),
                true,
                true,
                true,
                true,
                user.getRole().getName().getAuthority()
        );
    }

}
