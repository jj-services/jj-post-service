package austral.ingsis.jjpostservice.service;

import austral.ingsis.jjpostservice.dto.LikeDto;
import austral.ingsis.jjpostservice.exception.PostNotFoundException;
import austral.ingsis.jjpostservice.model.Likes;
import austral.ingsis.jjpostservice.model.Post;
import austral.ingsis.jjpostservice.repository.LikesRepository;
import austral.ingsis.jjpostservice.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;

    public LikesService(LikesRepository likesRepository, PostRepository postRepository) {
        this.likesRepository = likesRepository;
        this.postRepository = postRepository;
    }


    public LikeDto likePost(Long postId, Long likedByUserId) {
        Optional<Post> post = this.postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new PostNotFoundException("Post of id: " + postId + "not found.");
        }

        Likes likeRelation = new Likes();
        likeRelation.setPostId(postId);
        likeRelation.setLikedByUserId(likedByUserId);

        return this.likesRepository.save(likeRelation).toLikeDto();
    }

    public void unlikePostByBothIds(Long postId, Long likedByUserId) {
        Optional<Likes> likeRelation = this.likesRepository.findAll()
                .stream().filter(relation -> (relation.getPostId().equals(postId) && relation.getLikedByUserId().equals(likedByUserId))).findFirst();

        likeRelation.ifPresentOrElse(likes -> this.likesRepository.deleteById(likes.getId()), () -> {
            throw new PostNotFoundException("Post not found with given params");
        });
    }

}