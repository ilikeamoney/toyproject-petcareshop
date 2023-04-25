package hello.petshop.repository;

import hello.petshop.domain.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PetRepository {

    private final EntityManager em;

    public Long save(Pet pet) {
        em.persist(pet);
        return pet.getId();
    }

    public Pet findOne(Long id) {
        return em.find(Pet.class, id);
    }

    public List<Pet> findAll() {
        return em.createQuery("select p from Pet p", Pet.class)
                .getResultList();
    }
}
