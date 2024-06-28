package pl.edu.pb.pabwj.forum.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pb.pabwj.forum.dto.request.UserAccountRequestDto;
import pl.edu.pb.pabwj.forum.dto.response.UserAccountResponseDto;
import pl.edu.pb.pabwj.forum.mapper.UserAccountMapper;
import pl.edu.pb.pabwj.forum.model.Role;
import pl.edu.pb.pabwj.forum.repository.RoleRepository;
import pl.edu.pb.pabwj.forum.repository.UserAccountRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountResponseDto findByUsernameOrNull(String username) {
        log.info(String.format("looking for user with username: %s", username));
        return userAccountMapper.fromEntityToResponse(
                userAccountRepository.findByUsername(username)
                        .orElse(null));
    }

    public void save(UserAccountRequestDto userAccountRequestDto) {
        log.info(String.format("start registering user with username: %s", userAccountRequestDto.getUsername()));
        var userAccount = userAccountMapper.fromRequestToEntity(userAccountRequestDto);
        userAccount.addRole(
                roleRepository.findByName(Role.LIMITED)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Role with name: %s not found!", Role.LIMITED))));
        log.info(String.format("encoding password for user with username: %s", userAccountRequestDto.getUsername()));
        userAccount.setPassword(passwordEncoder.encode(userAccountRequestDto.getPassword()));
        log.info(String.format("saving user with username: %s", userAccountRequestDto.getUsername()));
        userAccountRepository.save(userAccount);
    }

}
