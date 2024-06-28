package pl.edu.pb.pabwj.forum.controller.operation;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pb.pabwj.forum.dto.request.TopicRequestDto;

@RequestMapping("/topic")
public interface TopicOperation {

    @GetMapping
    String findAllWithCategory(
            Model model,
            @RequestParam Long categoryId);

    @GetMapping("/add")
    String addTopic(
            Model model);

    @PostMapping("/add")
    String submitAddTopic(
            @Valid
            @ModelAttribute("topic")
            TopicRequestDto topic,
            BindingResult result,
            Model model);

    @GetMapping("/{id}")
    String findById(
            @PathVariable("id") Long id,
            Model model);

}
