package pl.edu.pb.pabwj.forum.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.edu.pb.pabwj.forum.aop.LogExecutionTime;
import pl.edu.pb.pabwj.forum.controller.operation.UserAccountOperation;
import pl.edu.pb.pabwj.forum.dto.request.UserAccountRequestDto;
import pl.edu.pb.pabwj.forum.service.UserAccountService;

import java.util.stream.Collectors;

@Log4j2
@Controller
@RequiredArgsConstructor
public class UserAccountController implements UserAccountOperation {

    private static final String ERROR_MSG = "errorMsg";
    private static final String SIGN_IN_FIRST = "sign in first!";

    private final UserAccountService userAccountService;

    @Override
    @LogExecutionTime
    public String getAllUsers(Model model) {
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            var userAccountResponseDtos = userAccountService.findAll()
                    .stream()
                    .filter(user -> !user.getUsername().equals(loggedUsername))
                    .collect(Collectors.toSet());
            model.addAttribute("users", userAccountResponseDtos);
            return "users";
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, SIGN_IN_FIRST);
            return "error";
        }
    }

    @Override
    @LogExecutionTime
    public String activateUser(Long id, Model model) {
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            var userAccountResponseDtos = userAccountService.activateUser(id)
                    .stream()
                    .filter(user -> !user.getUsername().equals(loggedUsername))
                    .collect(Collectors.toSet());
            model.addAttribute("users", userAccountResponseDtos);
            return "users";
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, SIGN_IN_FIRST);
            return "error";
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, e.getMessage());
            return "error";
        }
    }

    @Override
    @LogExecutionTime
    public String blockUser(Long id, Model model) {
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            var userAccountResponseDtos = userAccountService.blockUser(id)
                    .stream()
                    .filter(user -> !user.getUsername().equals(loggedUsername))
                    .collect(Collectors.toSet());
            model.addAttribute("users", userAccountResponseDtos);
            return "users";
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, SIGN_IN_FIRST);
            return "error";
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, e.getMessage());
            return "error";
        }
    }

    @Override
    @LogExecutionTime
    public String makeAdmin(Long id, Model model) {
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            var userAccountResponseDtos = userAccountService.makeAdmin(id)
                    .stream()
                    .filter(user -> !user.getUsername().equals(loggedUsername))
                    .collect(Collectors.toSet());
            model.addAttribute("users", userAccountResponseDtos);
            return "users";
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, SIGN_IN_FIRST);
            return "error";
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, e.getMessage());
            return "error";
        }
    }

    @Override
    @LogExecutionTime
    public String takeAdmin(Long id, Model model) {
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            var userAccountResponseDtos = userAccountService.takeAdmin(id)
                    .stream()
                    .filter(user -> !user.getUsername().equals(loggedUsername))
                    .collect(Collectors.toSet());
            model.addAttribute("users", userAccountResponseDtos);
            return "users";
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, SIGN_IN_FIRST);
            return "error";
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, e.getMessage());
            return "error";
        }
    }

    @Override
    @LogExecutionTime
    public String deleteUser(Model model) {
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            userAccountService.deleteByUsername(loggedUsername);
            return "logout";
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, SIGN_IN_FIRST);
            return "error";
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, e.getMessage());
            return "error";
        }
    }

    @Override
    @LogExecutionTime
    public String getUserDetails(Model model) {
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            var loggedUser = userAccountService.findByUsernameOrEx(loggedUsername);
            model.addAttribute("user", loggedUser);
            return "user";
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, SIGN_IN_FIRST);
            return "error";
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, e.getMessage());
            return "error";
        }
    }

    @Override
    public String editUser(Model model) {
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            var loggedUser = userAccountService.findByUsernameOrEx(loggedUsername);
            var userAccountRequestDto = new UserAccountRequestDto(loggedUser);
            model.addAttribute("userAccountRequestDto", userAccountRequestDto);
            return "edit_user";
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, SIGN_IN_FIRST);
            return "error";
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, e.getMessage());
            return "error";
        }
    }

    @Override
    public String submitEditUser(UserAccountRequestDto userAccountRequestDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("userAccountRequestDto", userAccountRequestDto);
            return "edit_user";
        }
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            var user = userAccountService.update(loggedUsername, userAccountRequestDto);
            model.addAttribute("user", user);
            return "user";
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, SIGN_IN_FIRST);
            return "error";
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, e.getMessage());
            return "error";
        }
    }

}
