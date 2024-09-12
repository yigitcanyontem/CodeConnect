package org.yigitcanyontem.user.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.user.domain.Users;
import org.yigitcanyontem.user.repository.UsersRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersDetailsService implements UserDetailsService {

  private final UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws BadCredentialsException, AccessDeniedException {
    Users user = usersRepository.findByEmail(email);
    if (user == null) {
        log.info("User not found with email: {}", email);
      return null;
    }

    log.info("User logged in with email: {}", email);
    return new UsersPrincipal(user);
  }
}
