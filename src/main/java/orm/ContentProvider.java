package orm;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ContentProvider")
public class ContentProvider extends PanacheEntity {
    @Column(nullable = false)
    public String name;
}
