package hello.petshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
public class Member extends BaseEntity {

    private String name;

    private String phone;

    private LocalDateTime visitDate;

    private int visitCount;

    @OneToMany(mappedBy = "member")
    private List<Pet> pets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designer_id")
    private Designer designer;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade;

    public void setDesigner(Designer designer) {
        this.designer = designer;
        designer.getMembers().add(this);
    }

    public static Member createMember(String name, String phone) {
        Member member = new Member();
        member.setName(name);
        member.setPhone(phone);
        member.setMemberGrade(MemberGrade.BRONZE);

        return member;
    }

    public int visitCount() {
        int visitCount = getVisitCount();
        this.setVisitCount(++visitCount);
        return visitCount;
    }
}
