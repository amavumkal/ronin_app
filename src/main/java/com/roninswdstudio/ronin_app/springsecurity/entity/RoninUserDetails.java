package com.roninswdstudio.ronin_app.springsecurity.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class RoninUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean enabled;
    private boolean locked;
    private List<SimpleGrantedAuthority> authorities;

    public RoninUserDetails (User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.locked = user.isLocked();
        this.authorities = user.getRoles()
                .parallelStream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toList());
    }

    public RoninUserDetails(String userName, String password, ArrayList<LinkedHashMap<String, String>> authorities) {
        this.userName = userName;
        this.password = password;
        this.authorities = authorities.parallelStream()
                .map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { return !locked; }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
