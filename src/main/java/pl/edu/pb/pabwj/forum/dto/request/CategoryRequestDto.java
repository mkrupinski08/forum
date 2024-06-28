package pl.edu.pb.pabwj.forum.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    @NotEmpty(message = "Should not be empty")
    @Size(message = "Should has size between 3 and 20 characters", min = 3, max = 20)
    @Pattern(message = "Should only has lowercase letters", regexp = "[a-z]+")
    private String name;
    @NotEmpty(message = "Should not be empty")
    private String description;


}
