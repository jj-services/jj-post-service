package austral.ingsis.jjpostservice.model;

import austral.ingsis.jjpostservice.dto.PostDto;

import javax.persistence.*;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    //relation to user table
    @Column(name = "user_id")
    private String userId;

    @Column(name = "text")
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


//    public PostDto toPostDto() {
//        return new PostDto(this.id, this.text, this.userId);
//    }
}
