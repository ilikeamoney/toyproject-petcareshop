package hello.petshop.repository;

import hello.petshop.domain.*;
import hello.petshop.exception.DuplicateNameException;
import hello.petshop.exception.NoSupportAnimal;
import hello.petshop.exception.NoVisitDateException;
import hello.petshop.service.DesignerService;
import hello.petshop.service.MemberPetService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class IntegratedTest {

    @Autowired
    MemberPetService memberPetService;

    @Autowired
    DesignerService designerService;

    @Test
    public void nameDuplicateTest() throws Exception {
        //given
        Member memberA = Member.createMember("memberA", "000");
        Member memberB = Member.createMember("memberA", "000");

        Pet petA = Pet.createPet(memberA, null, 0, null);
        Pet petB = Pet.createPet(memberA, null, 0, null);

        //when
        memberPetService.joinMemberPet(memberA, petA);

        //then
        Assertions.assertThatThrownBy(() -> memberPetService.joinMemberPet(memberB, petB))
                .isInstanceOf(DuplicateNameException.class);
    }

    @Test
    public void noSetVisitDate() throws Exception {
        //given
        Member memberA = Member.createMember("memberA", "000");
        AnimalType animalType = new AnimalType();
        animalType.setAnimalType("dog");
        animalType.setDetailType("bull dog");
        Pet petA = Pet.createPet(memberA, null, 0, animalType);

        memberA.setVisitDate(null);

        Designer designer = Designer.createDesigner("designerA", "000", "cut");

        //when
        memberPetService.joinMemberPet(memberA, petA);
        Long findDes = designerService.joinDesigner(designer);

        Designer findDesigner = designerService.findDesigner(findDes);

        //then
        Assertions.assertThatThrownBy(() -> designerService.memberReservation(memberA, findDesigner))
                .isInstanceOf(NoVisitDateException.class);
    }

    @Test
    public void noSupportAnimal() throws Exception {
        //given
        Member memberA = Member.createMember("memberA", "000");
        AnimalType animalType = new AnimalType();
        animalType.setAnimalType("fish");
        animalType.setDetailType("wow");
        Pet petA = Pet.createPet(memberA, null, 0, null);

        memberA.setVisitDate(LocalDateTime.of(2023, 4, 28, 17, 00));

        Designer designer = Designer.createDesigner("designerA", "000", "cut");

        //when
        memberPetService.joinMemberPet(memberA, petA);
        Long findDes = designerService.joinDesigner(designer);

        Designer findDesigner = designerService.findDesigner(findDes);

        //then
        Assertions.assertThatThrownBy(() -> designerService.memberReservation(memberA, findDesigner))
                .isInstanceOf(NoSupportAnimal.class);
    }

    @Test
    public void increaseCount() throws Exception {
        //given
        Member userA = Member.createMember("userA", "000");
        Member userB = Member.createMember("userB", "000");
        AnimalType animalType = new AnimalType();
        animalType.setAnimalType("dog");
        animalType.setDetailType("Golden retriever");
        Pet petA = Pet.createPet(userA, null, 0, animalType);
        Pet petB = Pet.createPet(userA, null, 0, animalType);

        userA.setVisitDate(LocalDateTime.of(2023, 4, 25, 13, 50));
        userB.setVisitDate(LocalDateTime.of(2023, 4, 25, 13, 50));

        memberPetService.joinMemberPet(userA, petA);
        memberPetService.joinMemberPet(userB, petB);

        Designer designer = Designer.createDesigner("designerA", "000", "cut");
        Long designerId = designerService.joinDesigner(designer);

        //when
        Designer findDes = designerService.findDesigner(designerId);

        designerService.memberReservation(userA, findDes);
        designerService.memberReservation(userA, findDes);
        designerService.memberReservation(userB, findDes);

        //then
        System.out.println("userA.getVisitCount() = " + userA.getVisitCount());
        System.out.println("userA.getVisitCount() = " + userB.getVisitCount());
        Assertions.assertThat(userA.getVisitCount()).isEqualTo(2);
        Assertions.assertThat(userB.getVisitCount()).isEqualTo(findDes.getVisitCount());
    }

    @Test
    public void checkGrade() throws Exception {
        //given
        Member userB = Member.createMember("userA", "000");
        AnimalType animalType = new AnimalType();
        animalType.setAnimalType("dog");
        animalType.setDetailType("Golden retriever");
        Pet petA = Pet.createPet(userB, null, 0, animalType);

        userB.setVisitDate(LocalDateTime.of(2023, 4, 25, 13, 50));

        userB.setVisitCount(29);

        memberPetService.joinMemberPet(userB, petA);

        Designer designer = Designer.createDesigner("designerA", "000", "cut");
        Long designerId = designerService.joinDesigner(designer);

        //when
        Designer findDes = designerService.findDesigner(designerId);
        designerService.memberReservation(userB, findDes);

        //then
        System.out.println("userB.getVisitCount() = " + userB.getVisitCount());
        Assertions.assertThat(userB.getMemberGrade()).isEqualTo(MemberGrade.GOLD);
    }

}
