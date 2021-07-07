package austral.ingsis.jjpostservice.repository;

import austral.ingsis.jjpostservice.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
