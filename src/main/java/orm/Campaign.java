package orm;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table (name = "Campaign")
public class Campaign extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @OneToOne
    @JoinColumn(name = "contentProvider")
    public ContentProvider contentProvider;

    @Column(name = "is_deleted")
    public boolean isDeleted;
}




