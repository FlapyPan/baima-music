package com.baima.music.entity;

import com.baima.music.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "[user]") // user是sql关键字，用[]括起来，jpa会根据数据库类型自动加上``或者""
public class User extends BaseEntity implements UserDetails {
    @Column(unique = true)
    private String username;
    private String nickname;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean locked = false;
    private Boolean enabled = true;
    // 系统不复杂，直接使用字符串存角色 "ROLE_USER, ROLE_ADMIN"
    private String roles;

    @ManyToMany
    @JoinTable(name = "user_favorite_music",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id"))
    private List<Music> favoriteMusics;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
