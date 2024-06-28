package pl.edu.pb.pabwj.forum.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountResponseDto {

    private Long id;
    @NotNull(message = "Should not be null")
    @Size(message = "Should has size between 3 and 20 characters", min = 3, max = 20)
    @Pattern(message = "Should start with uppercase", regexp = "[A-Z][a-z]+")
    @NotEmpty(message = "Should not be empty")
    @NotBlank(message = "Should not be blank")
    private String firstname;
    @NotNull(message = "Should not be null")
    @Size(message = "Should has size between 3 and 50 characters", min = 3, max = 50)
    @Pattern(message = "Should start with uppercase", regexp = "[A-Z][a-z]+")
    @NotEmpty(message = "Should not be empty")
    @NotBlank(message = "Should not be blank")
    private String lastname;
    @NotNull(message = "Should not be null")
    @Size(message = "Should has size between 3 and 20 characters", min = 3, max = 20)
    @Pattern(message = "Should contains only lowercase", regexp = "[a-z]+")
    @NotEmpty(message = "Should not be empty")
    @NotBlank(message = "Should not be blank")
    private String username;
    @NotNull(message = "Should not be null")
    @NotEmpty(message = "Should not be empty")
    @NotBlank(message = "Should not be blank")
    @Email(message = "Not a valid email")
    private String email;
    @NotNull(message = "Should not be null")
    @Min(message = "Should be at least 18 years old", value = 18)
    private Integer age;
    private Set<RoleResponseDto> roleResponseDtos;
    private boolean isFull;
    private boolean isAdmin;

}

