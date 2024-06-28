package pl.edu.pb.pabwj.forum.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.edu.pb.pabwj.forum.dto.request.UserAccountRequestDto;
import pl.edu.pb.pabwj.forum.dto.response.RoleResponseDto;
import pl.edu.pb.pabwj.forum.dto.response.UserAccountResponseDto;
import pl.edu.pb.pabwj.forum.mapper.UserAccountMapper;
import pl.edu.pb.pabwj.forum.model.Role;
import pl.edu.pb.pabwj.forum.repository.RoleRepository;
import pl.edu.pb.pabwj.forum.repository.UserAccountRepository;

import java.util.Optional;
import java.util.Set;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserAccountService {

    private static final String USER_ACCOUNT_WITH_USERNAME_NOT_FOUND = "User account with username: %s not found!";
    private static final String USER_ACCOUNT_WITH_ID_NOT_FOUND = "User account with id: %d not found!";
    private static final String ROLE_NOT_FOUND = "Role with name: %s not found!";

    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;
    private final RoleRepository roleRepository;

    public UserAccountResponseDto findByUsernameOrEx(String username) {
        log.info(String.format("looking for user with username: %s", username));
        return userAccountMapper.fromEntityToResponse(
                userAccountRepository.findByUsername(username)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ACCOUNT_WITH_USERNAME_NOT_FOUND, username))));
    }

    public Set<UserAccountResponseDto> findAll() {
        var userAccountResponseDtoSet = userAccountMapper.fromEntityToResponse(
                userAccountRepository.findAll());
        userAccountResponseDtoSet.forEach(userAccountResponseDto -> {
            userAccountResponseDto.setFull(userAccountResponseDto.getRoleResponseDtos()
                    .stream()
                    .map(RoleResponseDto::getName)
                    .anyMatch(name -> name.equals("full")));
            userAccountResponseDto.setAdmin(userAccountResponseDto.getRoleResponseDtos()
                    .stream()
                    .map(RoleResponseDto::getName)
                    .anyMatch(name -> name.equals("admin")));
        });
        return userAccountResponseDtoSet;
    }

    public Set<UserAccountResponseDto> activateUser(Long id) {
        var userAccount = userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ACCOUNT_WITH_ID_NOT_FOUND, id)));
        Role admin = roleRepository.findByName(Role.ADMIN)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.ADMIN)));
        if (userAccount.getRoles().contains(admin)) {
            userAccount.removeRole(admin);
        }
        Role limited = roleRepository.findByName(Role.LIMITED)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.LIMITED)));
        if (userAccount.getRoles().contains(limited)) {
            userAccount.removeRole(limited);
        }
        Role blocked = roleRepository.findByName(Role.BLOCKED)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.BLOCKED)));
        if (userAccount.getRoles().contains(blocked)) {
            userAccount.removeRole(blocked);
        }
        Role full = roleRepository.findByName(Role.FULL)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.FULL)));
        if (!userAccount.getRoles().contains(full)) {
            userAccount.addRole(full);
        }
        userAccountRepository.save(userAccount);
        return findAll();
    }

    public Set<UserAccountResponseDto> blockUser(Long id) {
        var userAccount = userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ACCOUNT_WITH_ID_NOT_FOUND, id)));
        Role admin = roleRepository.findByName(Role.ADMIN)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.ADMIN)));
        if (userAccount.getRoles().contains(admin)) {
            userAccount.removeRole(admin);
        }
        Role limited = roleRepository.findByName(Role.LIMITED)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.LIMITED)));
        if (userAccount.getRoles().contains(limited)) {
            userAccount.removeRole(limited);
        }
        Role full = roleRepository.findByName(Role.FULL)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.FULL)));
        if (userAccount.getRoles().contains(full)) {
            userAccount.removeRole(full);
        }
        Role blocked = roleRepository.findByName(Role.BLOCKED)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.BLOCKED)));
        if (!userAccount.getRoles().contains(blocked)) {
            userAccount.addRole(blocked);
        }
        userAccountRepository.save(userAccount);
        return findAll();
    }

    public Set<UserAccountResponseDto> makeAdmin(Long id) {
        var userAccount = userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ACCOUNT_WITH_ID_NOT_FOUND, id)));
        Role blocked = roleRepository.findByName(Role.BLOCKED)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.BLOCKED)));
        if (userAccount.getRoles().contains(blocked)) {
            userAccount.removeRole(blocked);
        }
        Role limited = roleRepository.findByName(Role.LIMITED)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.LIMITED)));
        if (userAccount.getRoles().contains(limited)) {
            userAccount.removeRole(limited);
        }
        Role full = roleRepository.findByName(Role.FULL)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.FULL)));
        if (userAccount.getRoles().contains(full)) {
            userAccount.removeRole(full);
        }
        Role admin = roleRepository.findByName(Role.ADMIN)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.ADMIN)));
        if (!userAccount.getRoles().contains(admin)) {
            userAccount.addRole(admin);
        }
        userAccountRepository.save(userAccount);
        return findAll();
    }

    public Set<UserAccountResponseDto> takeAdmin(Long id) {
        var userAccount = userAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ACCOUNT_WITH_ID_NOT_FOUND, id)));
        Role admin = roleRepository.findByName(Role.ADMIN)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.ADMIN)));
        if (userAccount.getRoles().contains(admin)) {
            userAccount.removeRole(admin);
        }
        Role limited = roleRepository.findByName(Role.LIMITED)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.LIMITED)));
        if (userAccount.getRoles().contains(limited)) {
            userAccount.removeRole(limited);
        }
        Role blocked = roleRepository.findByName(Role.BLOCKED)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.BLOCKED)));
        if (userAccount.getRoles().contains(blocked)) {
            userAccount.removeRole(blocked);
        }
        Role full = roleRepository.findByName(Role.FULL)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ROLE_NOT_FOUND, Role.FULL)));
        if (!userAccount.getRoles().contains(full)) {
            userAccount.addRole(full);
        }
        userAccountRepository.save(userAccount);
        return findAll();
    }

    public void deleteByUsername(String username) {
        userAccountRepository.delete(
                userAccountRepository.findByUsername(username)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ACCOUNT_WITH_USERNAME_NOT_FOUND, username))));
    }

    public UserAccountResponseDto update(String username, UserAccountRequestDto userAccountRequestDto) {
        var user = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ACCOUNT_WITH_USERNAME_NOT_FOUND, username)));
        Optional.ofNullable(userAccountRequestDto.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(userAccountRequestDto.getUsername()).ifPresent(user::setUsername);
        Optional.ofNullable(userAccountRequestDto.getAge()).ifPresent(user::setAge);
        Optional.ofNullable(userAccountRequestDto.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(userAccountRequestDto.getUsername()).ifPresent(user::setUsername);
        return userAccountMapper.fromEntityToResponse(userAccountRepository.save(user));
    }

}
