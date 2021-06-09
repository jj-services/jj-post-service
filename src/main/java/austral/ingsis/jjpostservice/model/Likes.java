package austral.ingsis.jjpostservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "post")
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
}
