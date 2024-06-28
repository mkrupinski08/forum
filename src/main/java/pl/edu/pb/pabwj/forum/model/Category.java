package pl.edu.pb.pabwj.forum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Getter
@Setter
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Should not be null")
    @NotEmpty(message = "Should not be empty")
    @Size(message = "Should has size between 3 and 20 characters", min = 3, max = 20)
    @Pattern(message = "Should only has lowercase letters", regexp = "[a-z]+")
    @NotBlank(message = "Should not be blank")
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    private Set<Topic> topics = new HashSet<>();

    public void addTopic(Topic topic) {
        this.topics.add(topic);
        log.info(String.format("added topic with title: %s to category with name: %s", topic.getTitle(), this.getName()));
    }

    public void removeTopic(Topic topic) {
        this.topics.remove(topic);
        log.info(String.format("removed topic with title: %s from category with name: %s", topic.getTitle(), this.getName()));
    }

}
