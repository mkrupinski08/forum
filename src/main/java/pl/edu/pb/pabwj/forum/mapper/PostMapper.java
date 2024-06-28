package pl.edu.pb.pabwj.forum.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.pb.pabwj.forum.dto.response.PostResponseDto;
import pl.edu.pb.pabwj.forum.model.Post;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PostMapper {

    @Mapping(target = "username", expression = "java(post.getUserAccount().getUsername())")
    PostResponseDto fromEntityToResponse(Post post);

}
