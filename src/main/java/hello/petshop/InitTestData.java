package hello.petshop;

import hello.petshop.domain.AnimalType;
import hello.petshop.domain.Designer;
import hello.petshop.domain.Member;
import hello.petshop.domain.Pet;
import hello.petshop.service.DesignerService;
import hello.petshop.service.MemberPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitTestData {

    private final RuntimeTestData runtimeTestData;

    @PostConstruct
    public void initData() {
        runtimeTestData.run();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class RuntimeTestData {
        private final MemberPetService mps;

        private final DesignerService ds;

        public void run() {
            // create Member, Pet
            Member memberLim = Member.createMember("MemberLim", "010-xxxx-xxxx");
            AnimalType animalTypeA = AnimalType.createAnimalType("Dog", "Bull Dog");
            Pet petA = Pet.createPet(memberLim, "John", 3, animalTypeA);

            memberLim.setVisitDate(LocalDateTime.of(2023, 4, 28, 17, 50));
            mps.joinMemberPet(memberLim, petA);

            // create Designer
            Designer designerPark = Designer.createDesigner("DesignerPark", "010-XXXX-XXXX", "cut");
            Long designerKId = ds.joinDesigner(designerPark);
            Designer designerK = ds.findDesigner(designerKId);

            // create Reservation
            ds.memberReservation(memberLim, designerK);


            // create Member, Pet
            Member memberKim = Member.createMember("MemberKim", "010-xxxx-xxxx");
            AnimalType animalTypeB = AnimalType.createAnimalType("Cat", "Sam");
            Pet petB = Pet.createPet(memberKim, "Lily", 8, animalTypeB);

            memberKim.setVisitDate(LocalDateTime.of(2023, 6, 25, 12, 30));
            memberKim.setVisitCount(29);
            mps.joinMemberPet(memberKim, petB);

            // create Designer
            Designer designerAhn = Designer.createDesigner("DesignerAhn", "010-XXXX-XXXX", "skin care");
            Long designerAId = ds.joinDesigner(designerAhn);
            Designer designerA = ds.findDesigner(designerAId);

            // create Reservation
            ds.memberReservation(memberKim, designerA);

        }

    }

}
