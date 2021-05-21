package austral.ingsis.jjpostservice.repository;

import austral.ingsis.jjpostservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    //TODO implement different gets
}
