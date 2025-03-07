package austral.ingsis.jjpostservice.model;

import austral.ingsis.jjpostservice.dto.LikeDto;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Likes {

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

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "liked_by_user_id")
    private Long likedByUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LikeDto toLikeDto() {
        return new LikeDto(this.postId, this.likedByUserId);
    }
}
