package hello.petshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AttributeOverride(name = "id", column = @Column(name = "pet_id"))
public class Pet extends BaseEntity{

    private String name;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime birthDay;

    @Embedded
    private AnimalType animalType;

    public void setMember(Member member) {
        this.member = member;
        member.getPets().add(this);
    }

    public static Pet createPet(Member member, String name, int age, AnimalType animalType) {
        Pet pet = new Pet();
        pet.setMember(member);
        pet.setName(name);
        pet.setAge(age);
        LocalDateTime petBrithDay = LocalDateTime.now().minusYears(age);
        pet.setBirthDay(petBrithDay);
        pet.setAnimalType(animalType);
        return pet;
    }
}
