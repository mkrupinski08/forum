package pl.edu.pb.pabwj.forum.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.pb.pabwj.forum.model.Role;
import pl.edu.pb.pabwj.forum.repository.UserAccountRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(String.format("security looking for user with username: %s", username));
        var userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("security did not found user with username: %s", username)));
        return User.builder()
                .username(userAccount.getUsername())
                .password(userAccount.getPassword())
                .roles(userAccount.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toArray(String[]::new))
                .build();
    }

}
