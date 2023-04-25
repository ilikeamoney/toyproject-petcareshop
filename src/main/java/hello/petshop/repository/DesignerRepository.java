package hello.petshop.repository;

import hello.petshop.domain.Designer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DesignerRepository {

    private final EntityManager em;

    public Long save(Designer designer) {
        em.persist(designer);
        return designer.getId();
    }

    public Designer findOne(Long id) {
        return em.find(Designer.class, id);
    }

    public List<Designer> findAll() {
        return em.createQuery("select d from Designer d", Designer.class)
                .getResultList();
    }

}
