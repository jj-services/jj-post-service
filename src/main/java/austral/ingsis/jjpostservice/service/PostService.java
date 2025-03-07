package austral.ingsis.jjpostservice.service;

import austral.ingsis.jjpostservice.connection.UserConnectionHandler;
import austral.ingsis.jjpostservice.dto.HomePostsDto;
import austral.ingsis.jjpostservice.dto.PostDto;
import austral.ingsis.jjpostservice.dto.UserDto;
import austral.ingsis.jjpostservice.exception.PostNotFoundException;
import austral.ingsis.jjpostservice.model.Post;
import austral.ingsis.jjpostservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostService {

    @PersistenceContext
    private EntityManager entityManager; //might not be needed, can be used for bulk save here?
    private PostRepository postRepository;
    private final UserConnectionHandler userConnectionHandler;

    @Autowired
    public PostService(PostRepository postRepository, UserConnectionHandler userConnectionHandler) {
        this.postRepository = postRepository;
        this.userConnectionHandler = userConnectionHandler;
    }

    public List<PostDto> getAllPosts() {
        return this.postRepository.findAll().stream()
                .map(Post::toPostDto).collect(Collectors.toList());
    }

    public List<PostDto> getAllPostsByUserId(Long userId) {
        return this.postRepository.findAll().stream()
                .filter(p -> p.getUserId().equals(userId))
                .map(Post::toPostDto).collect(Collectors.toList());
    }

    public PostDto savePost(Post post) {
        return this.postRepository.save(post).toPostDto();
    }


    public PostDto getPostById(Long id) {
        Optional<Post> post = this.postRepository.findById(id);

        if (post.isPresent()) {
            return post.get().toPostDto();
        }

        throw new PostNotFoundException("Post of id: " + id + "not found.");
    }

    public PostDto updatePost(Long postId, String text) {
        Optional<Post> post = this.postRepository.findById(postId);

        if (post.isEmpty()) {
            throw new PostNotFoundException("Post of id: " + postId + "not found.");
        }

        Post toSave = post.get();
        toSave.setText(text);

        return this.postRepository.save(toSave).toPostDto();
    }

    public void deletePost(Long id) {
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isEmpty()) {
            throw new PostNotFoundException("Post of id: " + id  + "not found.");
        }
        this.postRepository.deleteById(id);
    }

    //JAJA que negrada
    public List<HomePostsDto> getHomePostsForUser(Long userId) throws URISyntaxException {
        List<HomePostsDto> temp = new ArrayList<>();
        List<UserDto> users = this.userConnectionHandler.getFollowedUsersById(userId);
        users.add(this.userConnectionHandler.getUserInfoFromId(userId));
        List<Post> allPosts = this.postRepository.findAll();
        users.forEach(userDto ->
                temp.addAll(allPosts.stream()
                        .filter(post -> post.getUserId().equals(userDto.getId()))
                            .map(post -> post.toHomePostsDto(userDto)).collect(Collectors.toList())));
        temp.sort(Comparator.comparing(p -> p.getPostDto().getPostId()));
        return temp;
    }
}
