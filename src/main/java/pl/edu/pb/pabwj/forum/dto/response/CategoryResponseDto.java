package pl.edu.pb.pabwj.forum.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {

    private Long id;
    @NotNull(message = "Should not be null")
    @NotEmpty(message = "Should not be empty")
    @Size(message = "Should has size between 3 and 20 characters", min = 3, max = 20)
    @Pattern(message = "Should only has lowercase letters", regexp = "[a-z]+")
    @NotBlank(message = "Should not be blank")
    private String name;
    private String description;

}
