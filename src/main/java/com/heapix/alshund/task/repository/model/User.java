package com.heapix.alshund.task.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(max = 100)
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private Role authority;

    @NotNull
    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @NotNull
    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @NotNull
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(authority);
    }
}
