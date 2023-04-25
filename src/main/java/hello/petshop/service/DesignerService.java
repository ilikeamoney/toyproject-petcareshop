package hello.petshop.service;

import hello.petshop.domain.Designer;
import hello.petshop.domain.Member;
import hello.petshop.domain.Pet;
import hello.petshop.exception.NoSupportAnimal;
import hello.petshop.exception.NoVisitDateException;
import hello.petshop.repository.DesignerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DesignerService {

    private final DesignerRepository designerRepository;

    // add designer
    @Transactional
    public Long joinDesigner(Designer designer) {
        return designerRepository.save(designer);
    }

    // add Reservation
    @Transactional
    public void memberReservation(Member member, Designer designer) {
        if (validAnimalType(member.getPets())) {
            throw new NoSupportAnimal("저희 가게에는 포유류 케어만 지원합니다.");
        } else if (member.getVisitDate() == null) {
            throw new NoVisitDateException("방문 날짜를 입력해 주세요");
        }
        designer.createReservation(designer, member);
    }

    // find one
    public Designer findDesigner(Long designerId) {
        return designerRepository.findOne(designerId);
    }

    // find All
    public List<Designer> findDesigners() {
        return designerRepository.findAll();
    }

    // validate Pet Type
    private boolean validAnimalType(List<Pet> pets) {
        for (Pet pet : pets) {
            if (pet.getAnimalType() == null) {
                return true;
            } else if (pet.getAnimalType().getAnimalType().equalsIgnoreCase("reptile")) {
                return true;
            } else if (pet.getAnimalType().getAnimalType().equalsIgnoreCase("fish")) {
                return true;
            }
        }
        return false;
    }
}
