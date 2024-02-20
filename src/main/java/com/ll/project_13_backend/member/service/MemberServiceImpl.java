package com.ll.project_13_backend.member.service;

import com.ll.project_13_backend.global.exception.EntityNotFoundException;
import com.ll.project_13_backend.global.exception.ErrorCode;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member create(String userName , String nickName , String password){

        Member member = Member.builder()

                .userName(userName)
                .nickName(nickName)
                .password(password)
                .build();
        memberRepository.save(member);

        return member;
    }
    public Member findMember(final Long id) {

    return   memberRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

    }
    public void update() {}
    public void delete() {}
}
