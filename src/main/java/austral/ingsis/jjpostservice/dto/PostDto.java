package austral.ingsis.jjpostservice.dto;

public class PostDto {
    //dto returned for post (example get post by post id)
    private String postId;
    private String text;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    private UserDto userDto;
}
