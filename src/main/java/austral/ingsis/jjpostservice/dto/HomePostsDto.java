package austral.ingsis.jjpostservice.dto;


public class HomePostsDto {
    private UserDto userDto;
    private PostDto postDto;

    public HomePostsDto() {
    }

    public HomePostsDto(PostDto postDto, UserDto userDto) {
        this.postDto = postDto;
        this.userDto = userDto;
    }

    public PostDto getPostDto() {
        return postDto;
    }

    public void setPosts(PostDto postDto) {
        this.postDto = postDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
