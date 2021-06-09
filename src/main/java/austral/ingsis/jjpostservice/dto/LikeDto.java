package austral.ingsis.jjpostservice.dto;

import javax.validation.constraints.NotEmpty;

public class LikeDto {

    @NotEmpty
    private Long postId;
    @NotEmpty
    private Long likedByUserId;

    public LikeDto(Long postId, Long likedByUserId) {
        this.postId = postId;
        this.likedByUserId = likedByUserId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getLikedByUserId() {
        return likedByUserId;
    }

    public void setLikedByUserId(Long likedByUserId) {
        this.likedByUserId = likedByUserId;
    }
}
