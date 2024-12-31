package io.codeidea.apitemplate.admin.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

@ToString
public class AdminDetails implements UserDetails {

    private final Long id;
    private final String name;
    private final String loginId;
    private final AdminRole role;

    public AdminDetails(Map<String, Object> claims) {
        Assert.notNull(claims, "claims must not be null");
        Assert.notNull(claims.get("id"), "id must not be null");
        Assert.hasText((String) claims.get("name"), "name must not be empty");
        Assert.hasText((String) claims.get("loginId"), "loginId must not be empty");
        Assert.hasText((String) claims.get("role"), "role must not be empty");
        this.id = Long.parseLong(claims.get("id").toString());
        this.name = (String) claims.get("name");
        this.loginId = (String) claims.get("loginId");
        this.role = AdminRole.valueOf((String) claims.get("role"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
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
