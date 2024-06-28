package pl.edu.pb.pabwj.forum.controller.operation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.pb.pabwj.forum.dto.request.UserAccountRequestDto;

public interface AuthOperation {

    @GetMapping("/login")
    String login();

    @GetMapping("/index")
    String index();

    @GetMapping("/home")
    String home(
            HttpServletRequest httpServletRequest,
            Model model);

    @GetMapping("/register")
    String register(Model model);

    @PostMapping("/register")
    String submitRegistration(
            @Valid
            @ModelAttribute("userAccountRequestDto")
            UserAccountRequestDto userAccountRequestDto,
            BindingResult result,
            Model model);

}
