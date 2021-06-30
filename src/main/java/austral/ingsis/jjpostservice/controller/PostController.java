package austral.ingsis.jjpostservice.controller;

import austral.ingsis.jjpostservice.dto.CreatePostDto;
import austral.ingsis.jjpostservice.dto.HomePostsDto;
import austral.ingsis.jjpostservice.dto.PostDto;
import austral.ingsis.jjpostservice.dto.UpdatePostDto;
import austral.ingsis.jjpostservice.exception.PostNotFoundException;
import austral.ingsis.jjpostservice.model.Post;
import austral.ingsis.jjpostservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "")
    public ResponseEntity<PostDto> createPost(@RequestBody CreatePostDto createPostDto) {
        PostDto post = this.postService.savePost(this.mapDtoToModel(createPostDto));
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping(value = "/home/{userId}")
    public ResponseEntity<List<HomePostsDto>> getHomePostsForUser(@PathVariable Long userId) throws URISyntaxException {
        List<HomePostsDto> posts = this.postService.getHomePostsForUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = this.postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        try {
            PostDto postDto = this.postService.getPostById(id);
            return new ResponseEntity<>(postDto, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "")
    public ResponseEntity<PostDto> updatePost(@RequestBody UpdatePostDto dto) {
        try {
            PostDto updated = this.postService.updatePost(dto.getPostId(), dto.getText());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        try {
            this.postService.deletePost(id);
            return new ResponseEntity<>("Deleted post of id: " + id + ".", HttpStatus.OK);
        } catch (PostNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    private Post mapDtoToModel(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getPostId());
        post.setText(postDto.getText());
        post.setUserId(postDto.getUserId());
        return post;
    }

    private Post mapDtoToModel(CreatePostDto postDto) {
        Post post = new Post();
        post.setText(postDto.getText());
        post.setUserId(postDto.getUserId());
        return post;
    }

}
