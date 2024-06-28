package pl.edu.pb.pabwj.forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicDetailsResponseDto {

    private Long id;
    private String title;
    private Set<PostResponseDto> postResponseDtoSet;

}
