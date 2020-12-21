package orm;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Prompt")
public class Prompt extends PanacheEntity {
    @Column(nullable = false)
    public String originalName;

    @Column(nullable = false)
    public String ariName;
}
