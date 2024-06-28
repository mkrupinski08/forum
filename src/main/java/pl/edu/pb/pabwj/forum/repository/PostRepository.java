package pl.edu.pb.pabwj.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pb.pabwj.forum.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {



}
