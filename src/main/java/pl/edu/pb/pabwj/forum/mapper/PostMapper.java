package pl.edu.pb.pabwj.forum.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pb.pabwj.forum.dto.request.PostRequestDto;
import pl.edu.pb.pabwj.forum.dto.response.PostResponseDto;
import pl.edu.pb.pabwj.forum.model.Post;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = {ZonedDateTime.class, ZoneId.class})
public interface PostMapper {

    @Mapping(target = "username", expression = "java(post.getUserAccount().getUsername())")
    PostResponseDto fromEntityToResponse(Post post);

    @Mapping(target = "topic", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", expression = "java( ZonedDateTime.now(java.time.ZoneId.of(\"Europe/Warsaw\")) )")
    Post fromRequestToResponse(PostRequestDto postRequestDto);

}
