package pl.edu.pb.pabwj.forum.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.edu.pb.pabwj.forum.aop.LogExecutionTime;
import pl.edu.pb.pabwj.forum.controller.operation.AuthOperation;
import pl.edu.pb.pabwj.forum.dto.request.UserAccountRequestDto;
import pl.edu.pb.pabwj.forum.model.Role;
import pl.edu.pb.pabwj.forum.service.AuthService;
import pl.edu.pb.pabwj.forum.service.CategoryService;
import pl.edu.pb.pabwj.forum.service.UserAccountService;

import java.util.stream.Collectors;

@Log4j2
@Controller
@RequiredArgsConstructor
public class AuthController implements AuthOperation {

    private static final String ERROR_MSG = "errorMsg";
    private static final String SIGN_IN_FIRST = "sign in first!";

    private final AuthService authService;
    private final UserAccountService userAccountService;
    private final CategoryService categoryService;

    @Override
    public String login() {
        log.info("redirecting to login page");
        return "login";
    }

    @Override
    public String index() {
        log.info("redirecting to welcome page");
        return "index";
    }

    @Override
    public String home(HttpServletRequest httpServletRequest, Model model) {
        boolean isAdmin = httpServletRequest.isUserInRole(Role.ADMIN);
        if (isAdmin) {
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
        } else {
            var categoryResponseDtos = categoryService.findAllWithCnt();
            model.addAttribute("categories", categoryResponseDtos);
            return "home";
        }
    }

    @Override
    public String register(Model model) {
        model.addAttribute("userAccountRequestDto", new UserAccountRequestDto());
        log.info("redirecting to registration page");
        return "register";
    }

    @Override
    @LogExecutionTime
    public String submitRegistration(UserAccountRequestDto userAccountRequestDto, BindingResult result, Model model) {
        var userAccountResponseDto = authService.findByUsernameOrNull(userAccountRequestDto.getUsername());
        if (userAccountResponseDto != null) {
            log.info(String.format("found user account with username: %s", userAccountRequestDto.getUsername()));
            result.rejectValue("username", null, "There is already an account registered with that username");
        }
        if (result.hasErrors()) {
            model.addAttribute("userAccountRequestDto", userAccountRequestDto);
            log.info("registration failed");
            return "register";
        }
        try {
            authService.save(userAccountRequestDto);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        }
        log.info("registration success");
        return "redirect:/home";
    }

}
