package com.ll.project_13_backend.comment.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.project_13_backend.comment.dto.request.CreateCommentRequest;
import com.ll.project_13_backend.comment.dto.request.UpdateCommentRequest;
import com.ll.project_13_backend.comment.entity.Comment;
import com.ll.project_13_backend.comment.repository.CommentRepository;
import com.ll.project_13_backend.global.config.MemberPrincipal;
import com.ll.project_13_backend.global.exception.EntityNotFoundException;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberPrincipal memberPrincipal;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager em;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        commentRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();

        em.createNativeQuery("ALTER TABLE comment AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE post AUTO_INCREMENT 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE member AUTO_INCREMENT 1").executeUpdate();
    }

    @DisplayName("존재하지 않는 게시글에서 댓글 작성 시도 시 예외가 발생합니다.")
    @Test
    public void createCommentNotExistsPostException() throws Exception {
        //given
        final Member member = Member.builder()
                .userName("user")
                .password("password")
                .build();
        memberRepository.save(member);
        final CreateCommentRequest createCommentRequest = CreateCommentRequest.builder()
                .content("testContent")
                .build();

        final UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when & then
        mockMvc.perform(post("/comment/{postId}", 99999999L)
                        .content(objectMapper.writeValueAsString(createCommentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.code").value("C_001"),
                        jsonPath("$.message").value("지정한 Entity를 찾을 수 없습니다.")
                );
    }

    @DisplayName("댓글 내용은 반드시 입력해야한다.")
    @Test
    public void createCommentNotInputContentException() throws Exception {
        //given
        Member member = Member.builder()
                .userName("user")
                .password("pass")
                .build();
        memberRepository.save(member);
        final CreateCommentRequest createCommentRequest = CreateCommentRequest.builder()
                .build();
        UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when & then
        mockMvc.perform(post("/comment/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommentRequest))
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.code").value("C_002"),
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다."),
                        jsonPath("$.errors[0].field").value("content"),
                        jsonPath("$.errors[0].value").isEmpty(),
                        jsonPath("$.errors[0].message").value("댓글 내용을 반드시 입력해주세요.")
                );
    }

    @DisplayName("댓글을 작성한다.")
    @Test
    public void createComment() throws Exception {
        //given
        Member member = Member.builder()
                .userName("user")
                .password("pass")
                .build();
        memberRepository.save(member);
        Post post = Post.builder()
                .member(member)
                .build();
        postRepository.save(post);
        final CreateCommentRequest createCommentRequest = CreateCommentRequest.builder()
                .content("testContent")
                .build();
        UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when & then
        mockMvc.perform(post("/comment/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommentRequest))
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "")
                );
    }

    @DisplayName("존재하지 않는 게시글의 댓글은 조회할 수 없다.")
    @Test
    public void findCommentNotExistsPostException() throws Exception {
        //given & when & then
        mockMvc.perform(get("/comment/{postId}", 9999999999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.code").value("C_001"),
                        jsonPath("$.message").value("지정한 Entity를 찾을 수 없습니다.")
                );
    }

    @DisplayName("댓글을 조회한다.")
    @Test
    public void findComment() throws Exception {
        //given
        Member member = Member.builder()
                .nickName("testNickName")
                .build();
        memberRepository.save(member);

        Post post = Post.builder()
                .build();
        postRepository.save(post);

        Comment comment1 = Comment.builder()
                .post(post)
                .member(member)
                .content("testComment1")
                .build();
        Comment comment2 = Comment.builder()
                .post(post)
                .member(member)
                .content("testComment2")
                .build();
        Comment comment3 = Comment.builder()
                .post(post)
                .member(member)
                .content("testComment3")
                .build();
        commentRepository.saveAll(List.of(comment1, comment2, comment3));

        //when & then
        mockMvc.perform(get("/comment/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.size()").value(3),
                        jsonPath("$[*].content", everyItem(matchesPattern("testComment.*"))),
                        jsonPath("$[*].nickName", everyItem(matchesPattern("testNickName"))),
                        jsonPath("$[*].createdDate").isNotEmpty()
                );
    }

    @DisplayName("권한없는 사용자는 댓글을 수정할 수 없다.")
    @Test
    public void updateCommentUnAuthorizedException() throws Exception {
        //given
        final Member authorizedMember = Member.builder()
                .build();

        final Member unAuthorizedMember = Member.builder()
                .userName("user")
                .password("pass")
                .build();

        memberRepository.saveAll(List.of(authorizedMember, unAuthorizedMember));

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        final Post post = Post.builder()
                .build();
        postRepository.save(post);

        final Comment comment = Comment.builder()
                .post(post)
                .member(authorizedMember)
                .content("testComment1")
                .build();
        commentRepository.save(comment);

        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
                .content("testContent")
                .build();

        //when & then
        mockMvc.perform(put("/comment/update/{commentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentRequest))
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.code").value("AU_002"),
                        jsonPath("$.message").value("권한이 없는 사용자입니다.")
                );
    }

    @DisplayName("댓글 수정은 반드시 내용을 입력해야한다.")
    @Test
    public void updateCommentNotInputContentException() throws Exception {
        //given
        final Member authorizedMember = Member.builder()
                .userName("user")
                .password("pass")
                .build();

        memberRepository.save(authorizedMember);

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
                .build();

        //when & then
        mockMvc.perform(put("/comment/update/{commentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentRequest))
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.code").value("C_002"),
                        jsonPath("$.message").value("적절하지 않은 요청 값입니다."),
                        jsonPath("$.errors[0].field").value("content"),
                        jsonPath("$.errors[0].value").isEmpty(),
                        jsonPath("$.errors[0].message").value("댓글 내용을 반드시 입력해주세요.")
                );
    }

    @DisplayName("존재하지 않는 댓글 수정 시 예외가 발생한다.")
    @Test
    public void updateCommentNotExistsCommentException() throws Exception {
        //given
        Member member = Member.builder()
                .userName("user")
                .password("pass")
                .build();
        memberRepository.save(member);
        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
                .content("testContent")
                .build();

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        //when & then
        mockMvc.perform(put("/comment/update/{commentId}", 99999999999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentRequest))
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.code").value("C_001"),
                        jsonPath("$.message").value("지정한 Entity를 찾을 수 없습니다.")
                );
    }

    @DisplayName("댓글을 수정한다.")
    @Test
    public void updateComment() throws Exception {
        //given
        final Member authorizedMember = Member.builder()
                .userName("user")
                .password("pass")
                .build();

        memberRepository.save(authorizedMember);

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        final Post post = Post.builder()
                .build();
        postRepository.save(post);

        final Comment comment = Comment.builder()
                .post(post)
                .member(authorizedMember)
                .content("testComment")
                .build();
        commentRepository.save(comment);

        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
                .content("testUpdateContent")
                .build();

        //when & then
        mockMvc.perform(put("/comment/update/{commentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentRequest))
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

    @DisplayName("존재하지않는 댓글을 삭제 시도 시 예외가 발생한다.")
    @Test
    public void deleteCommentNotExistsCommentException() throws Exception {
        //given
        final Member authorizedMember = Member.builder()
                .userName("user")
                .password("pass")
                .build();

        memberRepository.save(authorizedMember);

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        final Post post = Post.builder()
                .build();
        postRepository.save(post);

        final Comment comment = Comment.builder()
                .post(post)
                .member(authorizedMember)
                .content("testComment")
                .build();
        commentRepository.save(comment);

        //when & then
        mockMvc.perform(delete("/comment/delete/{commentId}", 9999999L)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.code").value("C_001"),
                        jsonPath("$.message").value("지정한 Entity를 찾을 수 없습니다.")
                );
    }

    @DisplayName("권한없는 사용자가 댓글 삭제 시도 시 예외가 발생한다.")
    @Test
    public void deleteCommentUnAuthorizedException() throws Exception {
        //given
        final Member authorizedMember = Member.builder()
                .build();
        final Member unAuthorizedMember = Member.builder()
                .userName("user")
                .password("pass")
                .build();

        memberRepository.saveAll(List.of(authorizedMember, unAuthorizedMember));

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        final Post post = Post.builder()
                .build();
        postRepository.save(post);

        final Comment comment = Comment.builder()
                .post(post)
                .member(authorizedMember)
                .content("testComment")
                .build();
        commentRepository.save(comment);

        //when & then
        mockMvc.perform(delete("/comment/delete/{commentId}", 1L)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.code").value("AU_002"),
                        jsonPath("$.message").value("권한이 없는 사용자입니다.")
                );
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    public void deleteComment() throws Exception {
        //given
        final Member authorizedMember = Member.builder()
                .userName("user")
                .password("pass")
                .build();

        memberRepository.save(authorizedMember);

        UserDetails user = memberPrincipal.loadUserByUsername("user");

        final Post post = Post.builder()
                .build();
        postRepository.save(post);

        final Comment comment = Comment.builder()
                .post(post)
                .member(authorizedMember)
                .content("testComment")
                .build();
        commentRepository.save(comment);

        //when & then
        mockMvc.perform(delete("/comment/delete/{commentId}", 1L)
                        .with(SecurityMockMvcRequestPostProcessors.user(user)))
                .andDo(print())
                .andExpectAll(
                        status().isNoContent()
                );
        //todo 여기서 정상적으로  삭제가 되었는지 repository에서 조회하여 추가 검증해야하는가?나머지CRU도 마찬가지인가?
        assertThat(commentRepository.findById(1L).isEmpty())
                .isEqualTo(true);
    }
}