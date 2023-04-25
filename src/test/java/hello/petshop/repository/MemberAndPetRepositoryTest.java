package hello.petshop.repository;

import hello.petshop.domain.AnimalType;
import hello.petshop.domain.Member;
import hello.petshop.domain.Pet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class MemberAndPetRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PetRepository petRepository;

    @Test
    public void saveFind() throws Exception {
        //given
        Member memberA = Member.createMember("memberA", "010-XXXX-XXXX");
        Long memberAId = memberRepository.save(memberA);

        AnimalType animalType = new AnimalType();
        animalType.setAnimalType("dog");
        animalType.setDetailType("Golden Retriever");
        Pet petA = Pet.createPet(memberA, "petA", 4, animalType);
        Long petAId = petRepository.save(petA);

        //when
        Member findMember = memberRepository.findOne(memberAId);
        Pet findPetA = petRepository.findOne(petAId);

        //then
        Assertions.assertThat(memberA).isEqualTo(findMember);
        Assertions.assertThat(petA).isEqualTo(findPetA);
    }

    @Test
    @Rollback(value = false)
    public void findAllTest() throws Exception {
        //given
        Member memberA = Member.createMember("memberA", "010-XXXX-XXXX");
        Member memberB = Member.createMember("memberB", "010-XXXX-XXXX");

        AnimalType animalType = new AnimalType();
        animalType.setAnimalType("dog");
        animalType.setDetailType("Golden Retriever");
        Pet petA = Pet.createPet(memberA, "petA", 4, animalType);
        Pet petB = Pet.createPet(memberB, "petA", 4, animalType);

        memberRepository.save(memberA);
        memberRepository.save(memberB);
        petRepository.save(petA);
        petRepository.save(petB);

        //when
        List<Member> members = memberRepository.findAll();
        List<Pet> pets = petRepository.findAll();

        //then
        Assertions.assertThat(members.size()).isEqualTo(2);
        Assertions.assertThat(pets.size()).isEqualTo(2);
    }
}