package com.exam.quiz.security;

import com.exam.quiz.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
public class MyUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Set<GrantedAuthority> authoritySet;
    public MyUserDetails(User user){
        username = user.getUsername();
        password = user.getPassword();
        authoritySet = Arrays.stream(user.getRoles().split(", ")).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
}
