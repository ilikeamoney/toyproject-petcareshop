package hello.petshop.service;

import hello.petshop.domain.AnimalType;
import hello.petshop.domain.Member;
import hello.petshop.domain.Pet;
import hello.petshop.exception.DuplicateNameException;
import hello.petshop.exception.NoSupportAnimal;
import hello.petshop.repository.MemberRepository;
import hello.petshop.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberPetService {

    private final MemberRepository memberRepository;

    private final PetRepository petRepository;

    @Transactional
    public void joinMemberPet(Member member, Pet pet) {
        if (validDuplicateName(member)) {
            throw new DuplicateNameException("중복된 회원 이름입니다.");
        }
        memberRepository.save(member);
        petRepository.save(pet);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public List<Pet> findPets() {
        return petRepository.findAll();
    }

    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public Pet findPet(Long PetId) {
        return petRepository.findOne(PetId);
    }

    // valid name
    private boolean validDuplicateName(Member member) {
        List<Member> members = memberRepository.findAll();
        for (Member findMember : members) {
            if (member.getName().equals(findMember.getName())) {
                return true;
            }
        }
        return false;
    }

}
