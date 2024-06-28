package pl.edu.pb.pabwj.forum.controller.operation;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pb.pabwj.forum.dto.request.CategoryRequestDto;

@RequestMapping("/category")
public interface CategoryOperation {

    @GetMapping("/add")
    String addCategory(Model model);

    @PostMapping("/add")
    String submitAddCategory(
            @Valid
            @ModelAttribute("categoryRequestDto")
            CategoryRequestDto categoryRequestDto,
            BindingResult result,
            Model model);

    @GetMapping("/edit")
    String editCategory(Model model);

    @PostMapping("/edit")
    String submitEditCategory(
            @Valid
            @ModelAttribute("categoryRequestDto")
            CategoryRequestDto categoryRequestDto,
            BindingResult result,
            Model model);

    @GetMapping
    String findAll(Model model);

    @GetMapping("/delete/{name}")
    String delete(
            @PathVariable
            String name,
            Model model);

}
