package pl.edu.pb.pabwj.forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicListResponseDto {

    private Long id;
    private String title;
    private String user;
    private ZonedDateTime created;
    private int cnt;

}
