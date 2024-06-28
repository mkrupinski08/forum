package pl.edu.pb.pabwj.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pb.pabwj.forum.model.Category;
import pl.edu.pb.pabwj.forum.model.Topic;

import java.util.Set;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Set<Topic> findAllByCategoryEquals(Category category);

}
