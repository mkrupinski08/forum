package pl.edu.pb.pabwj.forum.controller.operation;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pb.pabwj.forum.dto.request.PostRequestDto;

@RequestMapping("/post")
public interface PostOperation {

    @GetMapping("/add")
    String addPost(
            Model model,
            @RequestParam Long topicId);

    @PostMapping("/add")
    String submitAddPost(
            @Valid
            @ModelAttribute("post")
            PostRequestDto post,
            BindingResult result,
            Model model,
            @RequestParam Long topicId);

}
