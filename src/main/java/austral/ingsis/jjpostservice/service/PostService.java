package austral.ingsis.jjpostservice.service;

import austral.ingsis.jjpostservice.dto.PostDto;
import austral.ingsis.jjpostservice.model.Post;
import austral.ingsis.jjpostservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @PersistenceContext
    private EntityManager entityManager; //might not be needed, can be used for bulk save here?
    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //get all posts should be done by user id
    public List<PostDto> getAllPosts() {
//        List<Post> posts = this.postRepository.findAll(); TODO check mapping to dto when get is done to users
//        return posts.stream().map(p -> p.toPostDto()).collect(Collectors.toList());
        return new ArrayList<>();
    }

    public Post savePost(Post post) {
        return this.postRepository.save(post); //add mapping to dto later
    }


}
