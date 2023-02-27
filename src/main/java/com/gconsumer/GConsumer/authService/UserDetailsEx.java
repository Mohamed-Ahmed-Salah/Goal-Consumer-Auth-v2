package com.gconsumer.GConsumer.authService;

import com.gconsumer.GConsumer.model.UserCredential;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//@Component
@AllArgsConstructor
class UserDetailsEx implements UserDetails {
    //    @Qualifier("user")
    private UserCredential userCredential;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        roles.add(userCredential.getRole());
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    @Override
    public String getPassword() {
        return this.userCredential.getPassword();
    }

    @Override
    public String getUsername() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return userCredential.isEnable();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userCredential.isLock();
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return userCredential.isEnable();
    }

    @Override
    public boolean isEnabled() {
        return userCredential.isEnable();
    }

}
