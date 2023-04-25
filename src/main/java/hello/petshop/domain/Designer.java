package hello.petshop.domain;

import hello.petshop.exception.NoVisitDateException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AttributeOverride(name = "id", column = @Column(name = "designer_id"))
public class Designer extends BaseEntity {

    private String name;

    private String phone;

    private String major;

    private int visitCount;

    @OneToMany(mappedBy = "designer")
    private List<Member> members = new ArrayList<>();

    private LocalDateTime reservationData;

    public static Designer createDesigner(String name, String phone, String major) {
        Designer designer = new Designer();
        designer.setName(name);
        designer.setPhone(phone);
        designer.setMajor(major);
        return designer;
    }

    public void createReservation(Designer designer, Member member) {
        int count = member.visitCount();
        member.setDesigner(this);
        designer.setVisitCount(count);
        designer.setReservationData(member.getVisitDate());
        checkMemberGradle(member);
    }

    private static void checkMemberGradle(Member member) {
        switch (member.getVisitCount()) {
            case 15 :
                member.setMemberGrade(MemberGrade.SILVER);
                break;
            case 30 :
                member.setMemberGrade(MemberGrade.GOLD);
                break;
        }
    }
}
