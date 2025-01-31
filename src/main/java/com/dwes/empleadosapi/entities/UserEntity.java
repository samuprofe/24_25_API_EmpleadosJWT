package com.dwes.empleadosapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class UserEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "El email no tiene el formato válido")
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private String foto;


    @Builder.Default    //Para que Lombok con el patrón builder cree el ArrayList
    @ElementCollection(fetch = FetchType.EAGER) // Indica que esta lista se almacena en una tabla separada, pero sin una relación
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> authorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
