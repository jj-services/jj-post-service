package austral.ingsis.jjpostservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UpdatePostDto {

    @NotEmpty
    @Size(max= 140, min =1, message = "post content should not exceed 140 characters")
    private String text;

    @NotEmpty
    private Long postId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
