package pl.edu.pb.pabwj.forum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount userAccount;
    private ZonedDateTime created;
    @OneToMany(mappedBy = "topic")
    private Set<Post> posts = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public void addPost(Post post) {
        this.posts.add(post);
        log.info(String.format("added post with content: %s to topic with title: %s", post.getContent(), this.getTitle()));
    }

    public void removePost(Post post) {
        this.posts.remove(post);
        log.info(String.format("removed post with content: %s from topic with title: %s", post.getContent(), this.getTitle()));
    }

}
