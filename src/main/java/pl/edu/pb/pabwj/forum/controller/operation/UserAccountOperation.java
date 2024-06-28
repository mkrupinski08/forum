package pl.edu.pb.pabwj.forum.controller.operation;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pb.pabwj.forum.dto.request.UserAccountRequestDto;

@RequestMapping("/user")
public interface UserAccountOperation {

    @GetMapping
    String getAllUsers(
            Model model);

    @GetMapping("/activate/{id}")
    String activateUser(
            @PathVariable("id")
            Long id,
            Model model);

    @GetMapping("/block/{id}")
    String blockUser(
            @PathVariable("id")
            Long id,
            Model model);

    @GetMapping("/admin/make/{id}")
    String makeAdmin(
            @PathVariable("id")
            Long id,
            Model model);

    @GetMapping("/admin/take/{id}")
    String takeAdmin(
            @PathVariable("id")
            Long id,
            Model model);

    @GetMapping("/delete")
    String deleteUser(
            Model model);

    @GetMapping("/details")
    String getUserDetails(
            Model model);

    @GetMapping("/edit")
    String editUser(
            Model model);

    @PostMapping("/edit")
    String submitEditUser(
            @Valid
            @ModelAttribute("userAccountRequestDto")
            UserAccountRequestDto userAccountRequestDto,
            BindingResult result,
            Model model);

}
