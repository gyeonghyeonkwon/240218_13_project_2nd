package com.ll.project_13_backend.member.service;

import com.ll.project_13_backend.member.entity.Member;

public interface MemberService {
    Member create(String userName , String nickName , String password);
    Member findMember(final Long id);
    void update();
    void delete();

}
