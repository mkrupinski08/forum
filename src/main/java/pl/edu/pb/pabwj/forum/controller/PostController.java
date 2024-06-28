package pl.edu.pb.pabwj.forum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.edu.pb.pabwj.forum.controller.operation.PostOperation;
import pl.edu.pb.pabwj.forum.dto.request.PostRequestDto;
import pl.edu.pb.pabwj.forum.service.PostService;
import pl.edu.pb.pabwj.forum.service.TopicService;

@Log4j2
@Controller
@RequiredArgsConstructor
public class PostController implements PostOperation {

    private static final String ERROR_MSG = "errorMsg";
    private static final String SIGN_IN_FIRST = "sign in first!";

    private final PostService postService;
    private final TopicService topicService;

    @Override
    public String addPost(Model model, Long topicId) {
        model.addAttribute("post", new PostRequestDto());
        var topic = topicService.findById(topicId);
        model.addAttribute("topic", topic);
        log.info("redirecting to add post page");
        return "add_post";
    }

    @Override
    public String submitAddPost(PostRequestDto post, BindingResult result, Model model, Long topicId) {
        if (result.hasErrors()) {
            model.addAttribute("post", post);
            var topic = topicService.findById(topicId);
            model.addAttribute("topic", topic);
            log.info("redirecting to add post page with errors");
            return "add_post";
        }
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            var topic = postService.addPost(post, topicId, loggedUsername);
            model.addAttribute("topic", topic);
            log.info("added post, redirect to topic");
            return "topic";
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        } catch (ClassCastException e) {
            log.error(e.getMessage());
            model.addAttribute(ERROR_MSG, SIGN_IN_FIRST);
            return "error";
        }
    }

}
