package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // 스프링이랑 엮어서 실행한다는 뜻
@SpringBootTest // 스프링부트를 띄운 상태로 테스트를 돌리겠다.
@Transactional // 트랜잭션을 걸고, 테스트 클래스면 종료시 롤백
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepositoryOld memberRepositoryOld;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepositoryOld.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class) // 해당 Exception이 발생하면 정상 종료
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다.
        /*
        이 아래 문장을 Test 어노테이션에서 짧게 끝낼 수 있다.
        
        try {
            memberService.join(member2); // 예외가 발생해야 한다.
        } catch (IllegalStateException e) {
            return;
        }
        */

        // then
        fail("예외가 발생해야 한다."); // 코드가 돌다가 여기까지 오면 문제가 발생했다는 것이다. 강제로 Exception 발생시킴
    }

}