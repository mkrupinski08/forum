package pl.edu.pb.pabwj.forum.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDto {

    private Long id;
    @NotNull(message = "Should not be null")
    @NotEmpty(message = "Should not be empty")
    @NotBlank(message = "Should not be blank")
    private String name;

}
