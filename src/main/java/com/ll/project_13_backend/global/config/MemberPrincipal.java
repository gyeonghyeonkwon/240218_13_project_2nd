package com.ll.project_13_backend.global.config;


import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberPrincipal implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) {
        Member member = memberRepository.findByUserName(userName).orElse(null);
        if (member == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new MemberAdapter(member);
    }
}
