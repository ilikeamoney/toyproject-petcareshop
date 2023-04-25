package hello.petshop.repository;

import hello.petshop.domain.Designer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class DesignerRepositoryTest {

    @Autowired
    DesignerRepository designerRepository;


    @Test
    public void saveFind() throws Exception {
        //given
        Designer designerA = Designer.createDesigner("designerA", "010-XXXX-XXXX", "cut");
        Long designerAId = designerRepository.save(designerA);

        //when
        Designer findDes = designerRepository.findOne(designerAId);

        //then
        Assertions.assertThat(designerA).isEqualTo(findDes);
    }

    @Test
    public void findAllTest() throws Exception {
        //given
        Designer designerA = Designer.createDesigner("designerA", "010-XXXX-XXXX", "cut");
        Designer designerB = Designer.createDesigner("designerB", "010-XXXX-XXXX", "nail");

        //when
        designerRepository.save(designerA);
        designerRepository.save(designerB);
        List<Designer> designers = designerRepository.findAll();

        //then
        Assertions.assertThat(designers.size()).isEqualTo(2);

    }

}