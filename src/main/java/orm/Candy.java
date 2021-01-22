package orm;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table (name = "candy")
public class Candy  extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @OneToOne
    @JoinColumn(name = "prompt")
    public Prompt prompt;

    @OneToOne
    @JoinColumn(name = "provider")
    public Provider provider;

    @Column(name = "is_deleted")
    public boolean isDeleted;
}




