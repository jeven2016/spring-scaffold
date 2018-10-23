package zjtech.auth.config;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder.Default;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h2>User</h2>
 *
 * @author Kristijan Georgiev
 *
 * User entity
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User implements UserDetails {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected long id;

  @NotNull
  @Size(min = 4, max = 24)
  private String username;

  @NotNull
  private String password;

  @Email
  @NotNull
  private String email;

  private boolean enabled = true;

  @Column(name = "account_expired")
  private boolean accountNonExpired = true;

  @Column(name = "credentials_expired")
  private boolean credentialsNonExpired = true;

  @Column(name = "account_locked")
  private boolean accountNonLocked = true;

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  /*
   * Get roles and permissions and add them as a Set of GrantedAuthority
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("FOO_READ"));
    authorities.add(new SimpleGrantedAuthority("FOO_WRITE"));
    return authorities;
  }
}
