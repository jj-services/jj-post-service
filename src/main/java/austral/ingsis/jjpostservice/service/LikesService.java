package austral.ingsis.jjpostservice.service;

import austral.ingsis.jjpostservice.connection.UserConnectionHandler;
import austral.ingsis.jjpostservice.dto.LikeDto;
import austral.ingsis.jjpostservice.dto.PostDto;
import austral.ingsis.jjpostservice.dto.UserDto;
import austral.ingsis.jjpostservice.exception.PostNotFoundException;
import austral.ingsis.jjpostservice.model.Likes;
import austral.ingsis.jjpostservice.model.Post;
import austral.ingsis.jjpostservice.repository.LikesRepository;
import austral.ingsis.jjpostservice.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final UserConnectionHandler userConnectionHandler;

    public LikesService(LikesRepository likesRepository, PostRepository postRepository, UserConnectionHandler userConnectionHandler) {
        this.likesRepository = likesRepository;
        this.postRepository = postRepository;
        this.userConnectionHandler = userConnectionHandler;
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

    public void unlikePostByRelationId(Long id) {
        Optional<Likes> likeRelation = this.likesRepository.findById(id);

        if (likeRelation.isEmpty()) {
            throw new PostNotFoundException("Like relation of id: " + id + "not found.");
        }

        this.likesRepository.deleteById(id);
    }

    //TODO FIX ME es un asco esta api (ahora mira lo lindo que es ese codigo)
    public void unlikePostByBothIds(Long postId, Long likedByUserId) {
        Optional<Likes> likeRelation = this.likesRepository.findAll()
                .stream().filter(relation -> (relation.getPostId().equals(postId) && relation.getLikedByUserId().equals(likedByUserId))).findFirst();

        likeRelation.ifPresentOrElse(likes -> this.likesRepository.deleteById(likes.getId()), () -> {
            throw new PostNotFoundException("Post not found with given params");
        });
    }

    public List<PostDto> getMyLikedPosts(Long userId) {
        //throws exception if not 200
        this.userConnectionHandler.getUserInfoFromId(userId);

        return this.likesRepository.findAll().stream().filter(rel -> rel.getLikedByUserId().equals(userId)).map(rel -> {
            Optional<Post> postOptional = this.postRepository.findById(rel.getPostId());
            if(postOptional.isPresent()) {
                return postOptional.get().toPostDto();
            }
            throw new PostNotFoundException("Post of id:" + rel.getPostId() + "not found");
        }).collect(Collectors.toList());
    }
}
