package pl.edu.pb.pabwj.forum.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pb.pabwj.forum.dto.request.TopicRequestDto;
import pl.edu.pb.pabwj.forum.dto.response.TopicDetailsResponseDto;
import pl.edu.pb.pabwj.forum.dto.response.TopicListResponseDto;
import pl.edu.pb.pabwj.forum.model.Topic;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {CategoryMapper.class, PostMapper.class}, imports = {ZonedDateTime.class, ZoneId.class})
public interface TopicMapper {

    @Mapping(target = "user", expression = "java(topic.getUserAccount().getUsername())")
    @Mapping(target = "cnt", expression = "java(topic.getPosts().size())")
    TopicListResponseDto fromEntityToResponse(Topic topic);

    Set<TopicListResponseDto> fromEntityToResponse(Set<Topic> topics);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", expression = "java( ZonedDateTime.now(java.time.ZoneId.of(\"Europe/Warsaw\")) )")
    Topic fromRequestToEntity(TopicRequestDto topicRequestDto);

    @Mapping(target = "postResponseDtoSet", source = "posts")
    TopicDetailsResponseDto fromEntityToDetailsResponse(Topic topic);

}
