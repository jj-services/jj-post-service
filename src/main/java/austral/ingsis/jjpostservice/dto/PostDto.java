package austral.ingsis.jjpostservice.dto;

public class PostDto {
    //dto returned for post (example get post by post id)
    private Long postId;
    private String text;
    private Long userId;

    public PostDto(Long postId, String text, Long userId) {
        this.postId = postId;
        this.text = text;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
