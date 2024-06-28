package pl.edu.pb.pabwj.forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHomeResponseDto {

    private Long id;
    private String name;
    private String description;
    private int cnt;

}
