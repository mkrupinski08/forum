package pl.edu.pb.pabwj.forum.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.edu.pb.pabwj.forum.dto.request.PostRequestDto;
import pl.edu.pb.pabwj.forum.dto.response.PostResponseDto;
import pl.edu.pb.pabwj.forum.dto.response.TopicDetailsResponseDto;
import pl.edu.pb.pabwj.forum.mapper.PostMapper;
import pl.edu.pb.pabwj.forum.mapper.TopicMapper;
import pl.edu.pb.pabwj.forum.repository.PostRepository;
import pl.edu.pb.pabwj.forum.repository.TopicRepository;
import pl.edu.pb.pabwj.forum.repository.UserAccountRepository;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostService {

    private static final String USER_ACCOUNT_WITH_USERNAME_NOT_FOUND = "User account with username: %s not found!";

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final TopicRepository topicRepository;
    private final UserAccountRepository userAccountRepository;
    private final TopicMapper topicMapper;

    public TopicDetailsResponseDto addPost(PostRequestDto postRequestDto, Long topicId, String username) {
        var post = postMapper.fromRequestToResponse(postRequestDto);
        var topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Topic with id %d not found", topicId)));
        post.setTopic(topic);
        var userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_ACCOUNT_WITH_USERNAME_NOT_FOUND, username)));
        post.setUserAccount(userAccount);
        userAccount.addPost(post);
        topic.addPost(post);
        postRepository.save(post);
        topicRepository.save(topic);
        var topicDetailsResponseDto = topicMapper.fromEntityToDetailsResponse(
                topicRepository.findById(topicId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Topic with id %d not found", topicId))));
        Set<PostResponseDto> sorted = topicDetailsResponseDto.getPostResponseDtoSet()
                .stream()
                .sorted(Comparator.comparing(PostResponseDto::getCreated))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        topicDetailsResponseDto.setPostResponseDtoSet(sorted);
        return topicDetailsResponseDto;
    }

}
