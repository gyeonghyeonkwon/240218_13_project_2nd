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


        for (int i = 1 ; i <= 100; i++) {

            Member  member =  memberService.create("user"+i , "nickname"+i , "1234");

            Post post = Post.builder()
                    .title("하하" +i)
                    .content("호호" +i)
                    .member(member)
                    .build();
            PostDto postDto = postService.toDto(post);

            postService.createPost(postDto, member);
        }
    }
}
