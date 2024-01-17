package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 스프링이 제공하는 것으로 사용하는 것을 권장
//@AllArgsConstructor // 모든 필드를 이용해 생성자 생성
@RequiredArgsConstructor // final 키워드가 있는 필드만 이용해 생성자 생성
public class MemberService {
    private final MemberRepositoryOld memberRepositoryOld; // 변경할 일이 없기 때문에 final 권장
    private final MemberRepository memberRepository;

    /*
    인젝션

    @Autowired // 필드 인젝션
    private final MemberRepository memberRepository; // 변경할 일이 없기 때문에 final 권장

    @Autowired // 생성자 인젝션. 스프링 부트 최신버전에서는 생성자가 단 하나만 있는 경우 어노테이션을 붙여주지 않아도 자동 생성 해준다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired // setter 인젝션
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

     */

    /** 회원 가입 */
    @Transactional // 클래스에 적용한 것보다 우선순위를 가지기 때문에 readOnly를 무시한다.
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /** 중복 회원 검증 */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /** 회원 전체 조회 */
//    @Transactional(readOnly = true) // 읽기에는 가급적 readOnly = true로 해주는게 좋다.
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /** 회원 단건 조회 */
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}
