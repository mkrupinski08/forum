package pl.edu.pb.pabwj.forum.dto.request;

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
import pl.edu.pb.pabwj.forum.dto.response.UserAccountResponseDto;
import pl.edu.pb.pabwj.forum.model.UserAccount;

import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountRequestDto {

    @Size(message = "Should has size between 3 and 20 characters", min = 3, max = 20)
    @Pattern(message = "Should start with uppercase", regexp = "[A-Z][a-z]+")
    @NotEmpty(message = "Should not be empty")
    private String firstname;
    @NotEmpty(message = "Should not be empty")
    @Size(message = "Should has size between 3 and 50 characters", min = 3, max = 50)
    @Pattern(message = "Should start with uppercase", regexp = "[A-Z][a-z]+")
    private String lastname;
    @Size(message = "Should has size between 3 and 20 characters", min = 3, max = 20)
    @Pattern(message = "Should contains only lowercase", regexp = "[a-z]+")
    @NotEmpty(message = "Should not be empty")
    private String username;
    @Size(message = "Should be at least 5 characters long", min = 5)
    @NotEmpty(message = "Should not be empty")
    private String password;
    @NotNull(message = "Should not be null")
    @NotEmpty(message = "Should not be empty")
    @NotBlank(message = "Should not be blank")
    @Email(message = "Not a valid email")
    private String email;
    @NotNull(message = "Should not be null")
    @Min(message = "Should be at least 18 years old", value = 18)
    private Integer age;

    public UserAccountRequestDto(UserAccountResponseDto userAccountResponseDto) {
        this.firstname = userAccountResponseDto.getFirstname();
        this.lastname = userAccountResponseDto.getLastname();
        this.username = userAccountResponseDto.getUsername();
        this.email = userAccountResponseDto.getEmail();
        this.age = userAccountResponseDto.getAge();
    }

}
