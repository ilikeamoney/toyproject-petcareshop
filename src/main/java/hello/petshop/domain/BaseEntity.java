package hello.petshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter @Setter
public class BaseEntity implements Serializable {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;


}
