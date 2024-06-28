package pl.edu.pb.pabwj.forum.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.edu.pb.pabwj.forum.aop.LogExecutionTime;
import pl.edu.pb.pabwj.forum.controller.operation.CategoryOperation;
import pl.edu.pb.pabwj.forum.dto.request.CategoryRequestDto;
import pl.edu.pb.pabwj.forum.service.CategoryService;

@Log4j2
@Controller
@RequiredArgsConstructor
public class CategoryController implements CategoryOperation {

    private final CategoryService categoryService;

    @Override
    public String addCategory(Model model) {
        model.addAttribute("categoryRequestDto", new CategoryRequestDto());
        log.info("redirecting to add category page");
        return "add_category";
    }

    @Override
    @LogExecutionTime
    public String submitAddCategory(CategoryRequestDto categoryRequestDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categoryRequestDto", categoryRequestDto);
            log.info("redirecting to add category page with errors");
            return "add_category";
        }
        try {
            var categoryResponseDtos = categoryService.add(categoryRequestDto);
            model.addAttribute("categories", categoryResponseDtos);
            log.info("added category, redirect to categories");
            return "categories";
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        }
    }

    @Override
    public String editCategory(Model model) {
        return null;
    }

    @Override
    public String submitEditCategory(CategoryRequestDto categoryRequestDto, BindingResult result, Model model) {
        return null;
    }

    @Override
    @LogExecutionTime
    public String findAll(Model model) {
        var categoryResponseDtos = categoryService.findAll();
        model.addAttribute("categories", categoryResponseDtos);
        log.info("redirect to categories");
        return "categories";
    }

    @Override
    @LogExecutionTime
    public String delete(String name, Model model) {
        try {
            var categoryResponseDtos = categoryService.deleteByName(name);
            model.addAttribute("categories", categoryResponseDtos);
            log.info("redirect to categories");
            return "categories";
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        }
    }

}
