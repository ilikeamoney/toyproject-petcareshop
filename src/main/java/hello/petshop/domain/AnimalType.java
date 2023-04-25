package hello.petshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class AnimalType {

    private String animalType; // simple type ex) dog, cat, bird

    private String detailType; // detail type ex) dog = BullDog, Golden Retriever

    public static AnimalType createAnimalType(String animalType, String detailType) {
        AnimalType aT = new AnimalType();
        aT.setAnimalType(animalType);
        aT.setDetailType(detailType);
        return aT;
    }
}
