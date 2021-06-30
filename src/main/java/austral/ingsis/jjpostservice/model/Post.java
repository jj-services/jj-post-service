package austral.ingsis.jjpostservice.model;

import austral.ingsis.jjpostservice.dto.HomePostsDto;
import austral.ingsis.jjpostservice.dto.PostDto;
import austral.ingsis.jjpostservice.dto.UserDto;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceUserGenerator")
    @GenericGenerator(
            name = "sequenceUserGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "social_network_user_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1000"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "user_id")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public PostDto toPostDto() {
        return new PostDto(this.id, this.text, this.userId);
    }

    public HomePostsDto toHomePostsDto(UserDto userDto){
        return new HomePostsDto(new PostDto(this.id,this.text, this.userId), userDto);
    }
}
