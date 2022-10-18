package com.company.config.details;

import com.company.entity.UserEntity;
import com.company.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userEntity != null) {
            return Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().name()));
        }
        return null;
    }

    @Override
    public String getPassword() {
        System.out.println("Current password =====================================" + userEntity.getPassword());
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        if (userEntity != null) {
            return userEntity.getEmail();
        }
        return null;
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
        if (!userEntity.getStatus().equals(UserStatus.ACTIVE)) {
            return false;
        }

        if (userEntity != null) {
            return userEntity.getVisible().equals(true);
        }

        return false;
    }

    public UserEntity getUser() {
        if (Optional.ofNullable(userEntity).isPresent()) return userEntity;
        return null;
    }

}
