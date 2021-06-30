package austral.ingsis.jjpostservice.controller;

import austral.ingsis.jjpostservice.dto.LikeDto;
import austral.ingsis.jjpostservice.dto.PostDto;
import austral.ingsis.jjpostservice.exception.AuthenticationException;
import austral.ingsis.jjpostservice.exception.PostNotFoundException;
import austral.ingsis.jjpostservice.model.Post;
import austral.ingsis.jjpostservice.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/likes")
public class LikesController {

    private final LikesService likesService;

    @Autowired
    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    //like
    @PostMapping(value = "")
    public ResponseEntity<String> likePost(@RequestBody LikeDto likeData) {
        try {
            //TODO try getting user or else unauthorized, hardcodear
            LikeDto resultDto = this.likesService.likePost(likeData.getPostId(), likeData.getLikedByUserId());
            //de gede nomas pero podria ser void
            return new ResponseEntity<>("Post of id: " + resultDto.getPostId() + " liked by user of id: " + resultDto.getLikedByUserId(), HttpStatus.CREATED);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "")
    public ResponseEntity<String> unlikePost(@RequestBody LikeDto likeData) {
        try {
            this.likesService.unlikePostByBothIds(likeData.getPostId(), likeData.getLikedByUserId());
            return new ResponseEntity<>("Deleted relation of id: " + likeData.getPostId(), HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<PostDto>> getMyLikedPosts(@PathVariable Long userId) {
        try {
            List<PostDto> myLikedPosts = this.likesService.getMyLikedPosts(userId);
            return new ResponseEntity<>(myLikedPosts, HttpStatus.OK);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (PostNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
