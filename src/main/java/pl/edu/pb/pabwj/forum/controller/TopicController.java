package pl.edu.pb.pabwj.forum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.edu.pb.pabwj.forum.aop.LogExecutionTime;
import pl.edu.pb.pabwj.forum.controller.operation.TopicOperation;
import pl.edu.pb.pabwj.forum.dto.request.TopicRequestDto;
import pl.edu.pb.pabwj.forum.service.CategoryService;
import pl.edu.pb.pabwj.forum.service.TopicService;

import java.util.Set;

@Log4j2
@Controller
@RequiredArgsConstructor
public class TopicController implements TopicOperation {

    private static final String ERROR_MSG = "errorMsg";
    private static final String SIGN_IN_FIRST = "sign in first!";

    private final TopicService topicService;
    private final CategoryService categoryService;

    @Override
    @LogExecutionTime
    public String findAllWithCategory(Model model, Long categoryId) {
        var topics = topicService.findAllWithCategory(categoryId);
        model.addAttribute("topics", topics);
        log.info("redirect to topics");
        return "topics";
    }

    @Override
    public String addTopic(Model model) {
        var categoryResponseDtos = categoryService.findAll();
        model.addAttribute("topic", new TopicRequestDto());
        model.addAttribute("categories", categoryResponseDtos);
        log.info("redirecting to add topic page");
        return "add_topic";
    }

    @Override
    @LogExecutionTime
    public String submitAddTopic(TopicRequestDto topic, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("topic", topic);
            var categoryResponseDtos = categoryService.findAll();
            model.addAttribute("categories", categoryResponseDtos);
            log.info("redirecting to add topic page with errors");
            return "add_topic";
        }
        try {
            var loggedUsername = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            var topics = topicService.add(topic, loggedUsername);
            model.addAttribute("topics", topics);
            log.info("added topic, redirect to topics");
            return "topics";
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

    @Override
    public String findById(Long id, Model model) {
        var topic = topicService.findById(id);
        model.addAttribute("topic", topic);
        log.info("redirecting to topic");
        return "topic";
    }

}
