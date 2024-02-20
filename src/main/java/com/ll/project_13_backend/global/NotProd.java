package com.ll.project_13_backend.global;

import com.ll.project_13_backend.comment.repository.CommentRepository;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.member.service.MemberService;
import com.ll.project_13_backend.post.dto.PostDto;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import com.ll.project_13_backend.post.service.PostService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NotProd {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final PostService postService;

    @PostConstruct
    @Transactional
    public void init() {


       Member  member =  memberService.create("user1" , "nickname1" , "1234");

        Post post = Post.builder()
                .title("하하")
                .content("호호")
                .member(member)
                .build();
        PostDto postDto = postService.toDto(post);

        postService.createPost(postDto , member);

    }
}
