package austral.ingsis.jjpostservice.controller;

import austral.ingsis.jjpostservice.dto.LikeDto;
import austral.ingsis.jjpostservice.exception.AuthenticationException;
import austral.ingsis.jjpostservice.exception.PostNotFoundException;
import austral.ingsis.jjpostservice.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/likes")
public class LikesController {

    private final LikesService likesService;

    @Autowired
    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    //like
    @GetMapping(value = "/{id}")
    public ResponseEntity<String> likePost(@PathVariable Long id, @RequestBody Long userId) {
        try {
            //TODO try getting user or else unauthorized
            LikeDto resultDto = this.likesService.likePost(id, userId);
            //de gede nomas pero podria ser void
            return new ResponseEntity<>("Post of id: " + resultDto.getPostId() + " liked by user of id: " + resultDto.getLikedByUserId(), HttpStatus.CREATED);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> unlikePost(@PathVariable Long id) {
        try {
            this.likesService.unlikePostByRelationId(id);
            return new ResponseEntity<>("Deleted relation of id: " + id, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
