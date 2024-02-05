package com.ll.project_13_backend.global.config;

import com.ll.project_13_backend.member.entity.Member;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class MemberAdapter extends User {

    private Member member;
    public MemberAdapter(Member member) {
        super(member.getUserName(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
    }

}
