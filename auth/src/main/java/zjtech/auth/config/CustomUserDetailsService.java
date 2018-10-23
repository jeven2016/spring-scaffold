package zjtech.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {


  private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

  @Override
  public UserDetails loadUserByUsername(String input) {
    User user = new User();
    user.setUsername("user");
    user.setId(1111l);
    user.setEnabled(true);
    detailsChecker.check(user);
    return user;
  }
}

