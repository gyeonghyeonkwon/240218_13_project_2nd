package com.ll.project_13_backend.global;

import com.ll.project_13_backend.comment.entity.Comment;
import com.ll.project_13_backend.comment.repository.CommentRepository;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NotProd {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @PostConstruct
    @Transactional
    public void init() {

        List<Member> members = new ArrayList<>();
        for (long i = 1L; i <= 10L; i++) {
            Member member = Member.builder()
                    .userName("user" + i)
                    .password("password" + i)
                    .nickName("nickName" + i)
                    .build();
            members.add(member);
        }
        memberRepository.saveAll(members);

        List<Post> posts = new ArrayList<>();
        for (long i = 1L; i <= 100; i++) {
            Post post = Post.builder()
                    .member(members.get((int)i % 10))
                    .title("Test Post Title")
                    .content("Test Post Content" + i)
                    .build();
            posts.add(post);
        }
        postRepository.saveAll(posts);

        List<Comment> comments = new ArrayList<>();
        for (long i = 1L; i <= 1000; i++) {
            Comment comment = Comment.builder()
                    .member(members.get((int)i % 10))
                    .post(posts.get((int)i % 100))
                    .content("Test Comment Content" + i)
                    .build();
            comments.add(comment);
        }
        commentRepository.saveAll(comments);
    }
}
