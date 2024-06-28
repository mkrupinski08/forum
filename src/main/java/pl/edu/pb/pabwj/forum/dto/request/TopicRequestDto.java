package pl.edu.pb.pabwj.forum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicRequestDto {

    private String title;
    private String content;
    private String categoryResponseDto;

}
