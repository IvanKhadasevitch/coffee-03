package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "Configuration")

@Data
@NoArgsConstructor
public class Configuration {
    @Id
    @Column(unique = true, nullable = false, length = 20)
    @Access(AccessType.PROPERTY)
    //id 		varchar(20) not null unique, -- pk, название свойства
    private String id;

    //`value`	varchar(30), -- значение
    @Column(name = "`value`", length = 30)
    @Access(AccessType.PROPERTY)
    private String value;

    public Configuration(@NonNull String id) {
        id = id != null
                ? id
                : "Not define";
        this.id = id;
    }
}
