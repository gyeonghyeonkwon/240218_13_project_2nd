package com.ll.project_13_backend.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ll.project_13_backend.comment.dto.service.CreateCommentDto;
import com.ll.project_13_backend.comment.dto.service.FindCommentDto;
import com.ll.project_13_backend.comment.dto.service.UpdateCommentDto;
import com.ll.project_13_backend.comment.entity.Comment;
import com.ll.project_13_backend.comment.repository.CommentRepository;
import com.ll.project_13_backend.global.exception.AuthException;
import com.ll.project_13_backend.global.exception.EntityNotFoundException;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentServiceImplTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUpTest() {
        commentRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();

        em.createNativeQuery("ALTER TABLE comment AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE post AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE member AUTO_INCREMENT 1").executeUpdate();
    }

    @DisplayName("존재하지 않는 게시글에 댓글을 작성할수없다.")
    @Test
    void createCommentPostEntityNotFoundException() {
        //given
        final Member member = Member.builder()
                .nickName("TestNickName")
                .build();
        final CreateCommentDto createCommentDto = CreateCommentDto.builder()
                .content("TestCommentContent")
                .build();


        //when & then
       assertThatThrownBy(() -> commentService.createComment(1L,createCommentDto, member))
               .isInstanceOf(EntityNotFoundException.class)
               .hasMessage("지정한 Entity를 찾을 수 없습니다.");
    }

    @DisplayName("댓글을 작성한다.")
    @Test
    void createComment() {
        //given
        final Member member = Member.builder()
                .build();
        final Post post = Post.builder()
                .build();
        postRepository.save(post);
        final CreateCommentDto createCommentDto = CreateCommentDto.builder()
                .content("TestCommentContent")
                .build();
        //when
        commentService.createComment(1L, createCommentDto, member);

        //then
        Comment comment = commentRepository.findById(1L).get();
        assertThat(comment)
                .extracting("content")
                .isEqualTo("TestCommentContent");
    }

    @DisplayName("존재하지 않는 게시글의 댓글을 조회하면 예외가 발생한다.")
    @Test
    void findCommentPostEntityNotFoundException() {
        //given & when & then
        assertThatThrownBy(() -> commentService.findAllCommentByPostId(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("지정한 Entity를 찾을 수 없습니다.");
    }

    @DisplayName("게시글의 모든 댓글을 조회한다.")
    @Test
    void finAllComment() {
        //given
        final Member member = Member.builder()
                .nickName("testMember")
                .build();
        memberRepository.save(member);
        final Post post = Post.builder()
                .id(1L)
                .build();
        postRepository.save(post);
        final Comment comment1 = Comment.builder()
                .post(post)
                .member(member)
                .build();
        final Comment comment2 = Comment.builder()
                .post(post)
                .member(member)
                .build();
        final Comment comment3 = Comment.builder()
                .post(post)
                .member(member)
                .build();
        commentRepository.saveAll(List.of(comment1, comment2, comment3));

        //when
        List<FindCommentDto> findCommentDtos = commentService.findAllCommentByPostId(1L);

        //then
        assertThat(findCommentDtos.size()).isEqualTo(3);
    }

    @DisplayName("권한없는 사람이 댓글을 수정시도 시 예외가 발생한다.")
    @Test
    void updateCommentUnAuthorizationException() {
        //given
        final Member member1 = Member.builder()
                .id(1L)
                .build();
        final Member member2 = Member.builder()
                .id(2L)
                .build();
        memberRepository.saveAll(List.of(member1,member2));
        final Comment comment = Comment.builder()
                .member(member1)
                .build();
        commentRepository.save(comment);

        final UpdateCommentDto updateCommentDto = UpdateCommentDto.builder()
                .build();

        //when & then
        assertThatThrownBy(() -> commentService.updateComment(1L, updateCommentDto, member2))
                .isInstanceOf(AuthException.class)
                .hasMessage("권한이 없는 사용자입니다.");
    }

    @DisplayName("존재하지 않는 댓글을 업데이트 시도 시 예외가 발생한다.")
    @Test
    void updateCommentNotExists() {
        //given & when & then
        assertThatThrownBy(() -> commentService.updateComment(9999999999L, null, null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("지정한 Entity를 찾을 수 없습니다.");
    }

    @DisplayName("댓글을 업데이트한다.")
    @Test
    void updateComment() {
        //given
        final Member member = Member.builder()
                .id(1L)
                .build();
        memberRepository.save(member);
        final Comment comment = Comment.builder()
                .member(member)
                .content("CommentTest")
                .build();
        commentRepository.save(comment);

        final UpdateCommentDto updateCommentDto = UpdateCommentDto.builder()
                .content("updateCommentTest")
                .build();

        //when
        commentService.updateComment(1L, updateCommentDto, member);

        //then
        Comment comment1 = commentRepository.findById(1L).get();
        assertThat(comment1.getContent())
                .isEqualTo("updateCommentTest");
    }

    @DisplayName("존재하지 않는 댓글을 삭제시도 시 예외가 발생한다.")
    @Test
    void deleteCommentNotExistsCommentException() {
        //given & when & then
        assertThatThrownBy(() -> commentService.deleteComment(99999999L, null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("지정한 Entity를 찾을 수 없습니다.");
    }

    @DisplayName("권한없는 유저가 댓글삭제 시도 시 예외가 발생한다.")
    @Test
    void deleteCommentUnAuthorizationException() {
        //given
        final Member authorizationMember = Member.builder()
                                .build();
        memberRepository.save(authorizationMember);
        final Member unAuthorizationMember = Member.builder()
                        .id(2L)
                                .build();
        final Comment comment = Comment.builder()
                        .member(authorizationMember)
                                .build();
        commentRepository.save(comment);

        //when & then
        assertThatThrownBy(() -> commentService.deleteComment(1L, unAuthorizationMember))
                .isInstanceOf(AuthException.class)
                .hasMessage("권한이 없는 사용자입니다.");
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    void deleteComment() {
        //given
        final Member member = Member.builder()
                .build();
        memberRepository.save(member);

        final Comment comment = Comment.builder()
                .member(member)
                .build();
        commentRepository.save(comment);

        //when
        commentService.deleteComment(1L, member);

        //then
        assertThat(commentRepository.existsById(1L))
                .isEqualTo(false);
    }
}