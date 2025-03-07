package austral.ingsis.jjpostservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CreatePostDto {
    @NotEmpty
    @Size(max= 140, min =1, message = "post content should not exceed 140 characters")
    private String text;

    //@NotEmpty todo
    private Long userId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
