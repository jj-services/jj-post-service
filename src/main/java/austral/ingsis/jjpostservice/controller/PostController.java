package austral.ingsis.jjpostservice.controller;

import austral.ingsis.jjpostservice.dto.CreatePostDto;
import austral.ingsis.jjpostservice.dto.PostDto;
import austral.ingsis.jjpostservice.model.Post;
import austral.ingsis.jjpostservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/posts") //todo check
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "") //change after mapping is solved
    public ResponseEntity<Post> createPost(@RequestBody CreatePostDto createPostDto) {
        Post post = this.postService.savePost(this.mapDtoToModel(createPostDto));
        //TODO response headers
        //TODO add more validations like bad request
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping(value = "") //later add path variable id when getallposts by id is done
    public ResponseEntity<List<PostDto>> getAllPosts( ) {
        List<PostDto> users = this.postService.getAllPosts();
        //TODO response headers
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    private Post mapDtoToModel(CreatePostDto postDto) {
        Post post = new Post();
//        post.setId(postDto.getPostId());
        post.setText(postDto.getText());
//        post.setUserId(postDto.getUserDto().getUserId());
        return post;
    }


    private Post mapDtoToModel(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getPostId());
        post.setText(postDto.getText());
        post.setUserId(postDto.getUserDto().getUserId());
        return post;
    }
}
