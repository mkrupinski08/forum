package pl.edu.pb.pabwj.forum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

@Entity
@Log4j2
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(unique = true)
    private String username;
    @NotNull(message = "Should not be null")
    @NotEmpty(message = "Should not be empty")
    @NotBlank(message = "Should not be blank")
    @Column(unique = true)
    @Email(message = "Not a valid email")
    private String email;
    @NotNull(message = "Should not be null")
    @Size(message = "Should be at least 5 characters long", min = 5)
    @NotEmpty(message = "Should not be empty")
    @NotBlank(message = "Should not be blank")
    private String password;
    @NotNull(message = "Should not be null")
    @Min(message = "Should be at least 18 years old", value = 18)
    private Integer age;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_accounts_roles",
            joinColumns = @JoinColumn(
                    name = "user_account_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "userAccount")
    private Set<Topic> topics = new HashSet<>();
    @OneToMany(mappedBy = "userAccount")
    private Set<Post> posts = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
        log.info(String.format("added role with name: %s to user with username: %s", role.getName(), this.getUsername()));
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        log.info(String.format("removed role with name: %s from user with username: %s", role.getName(), this.getUsername()));
    }

    public void addTopic(Topic topic) {
        this.topics.add(topic);
        log.info(String.format("added topic with title: %s to user with username: %s", topic.getTitle(), this.getUsername()));
    }

    public void removeTopic(Topic topic) {
        this.topics.remove(topic);
        log.info(String.format("removed topic with title: %s from user with username: %s", topic.getTitle(), this.getUsername()));
    }

    public void addPost(Post post) {
        this.posts.add(post);
        log.info(String.format("added post with content: %s to user with username: %s", post.getContent(), this.getUsername()));
    }

    public void removePost(Post post) {
        this.posts.remove(post);
        log.info(String.format("removed post with content: %s from user with username: %s", post.getContent(), this.getUsername()));
    }

}
