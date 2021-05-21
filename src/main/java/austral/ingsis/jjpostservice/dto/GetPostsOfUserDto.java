package austral.ingsis.jjpostservice.dto;

import java.util.List;

public class GetPostsOfUserDto {
    //get all posts made by a user, id passed as query parameter
    private List<PostDto> posts;
    private UserDto userDto;
}
