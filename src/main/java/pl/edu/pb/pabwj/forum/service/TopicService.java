package pl.edu.pb.pabwj.forum.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.edu.pb.pabwj.forum.dto.request.TopicRequestDto;
import pl.edu.pb.pabwj.forum.dto.response.TopicDetailsResponseDto;
import pl.edu.pb.pabwj.forum.dto.response.TopicListResponseDto;
import pl.edu.pb.pabwj.forum.mapper.TopicMapper;
import pl.edu.pb.pabwj.forum.model.Post;
import pl.edu.pb.pabwj.forum.repository.CategoryRepository;
import pl.edu.pb.pabwj.forum.repository.PostRepository;
import pl.edu.pb.pabwj.forum.repository.TopicRepository;
import pl.edu.pb.pabwj.forum.repository.UserAccountRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@Service
@Log4j2
@RequiredArgsConstructor
public class TopicService {

    private static final String USER_ACCOUNT_WITH_USERNAME_NOT_FOUND = "User account with username: %s not found!";

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final UserAccountRepository userAccountRepository;

    public Set<TopicListResponseDto> findAllWithCategory(Long categoryId) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id: %d not found", categoryId)));
        return topicMapper.fromEntityToResponse(
                topicRepository.findAllByCategoryEquals(category));
    }

    public Set<TopicListResponseDto> add(TopicRequestDto topicRequestDto, String username) {
        var topic = topicMapper.fromRequestToEntity(topicRequestDto);
        var userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ACCOUNT_WITH_USERNAME_NOT_FOUND, username)));
        topic.setUserAccount(userAccount);
        var category = categoryRepository.findByName(topicRequestDto.getCategoryResponseDto())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with name: %s not found", topicRequestDto.getCategoryResponseDto())));
        topic.setCategory(category);
        var post = Post.builder()
                .content(topicRequestDto.getContent())
                .userAccount(userAccount)
                .topic(topic)
                .created(ZonedDateTime.now(ZoneId.systemDefault()))
                .build();
        topic.addPost(post);
        category.addTopic(topic);
        userAccount.addTopic(topic);
        topicRepository.save(topic);
        postRepository.save(post);
        return findAllWithCategory(category.getId());
    }

    public TopicDetailsResponseDto findById(Long id) {
        return topicMapper.fromEntityToDetailsResponse(
                topicRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Topic with id %d not found", id))));
    }

}
