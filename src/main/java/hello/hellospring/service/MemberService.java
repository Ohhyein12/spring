package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    // 직접 생성하는게아니라 외부에서 넣어주도록 코드 변경 -> service에서 생성하는 인스턴스 와 test코드 작성시 생성하는 인스턴스때문에 문제가 생길수도 있어서
    // MemberService입장에선 직접 생성하지 않고 외부에서 memberRepository를 넣어줌 : DI
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
     * 회원 가입
     **/
    public Long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        // null이 아니라 값이 있으면 (optional method)
        // optional.get() -> 바로 꺼내는거 권장 x
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
