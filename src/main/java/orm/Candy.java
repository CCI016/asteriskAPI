package orm;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@Entity
@Table (name = "candy")
public class Candy extends PanacheEntity {

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




