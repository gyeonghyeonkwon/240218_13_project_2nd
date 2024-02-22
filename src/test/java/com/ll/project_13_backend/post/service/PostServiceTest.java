package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.dto.PageRequestDto;
import com.ll.project_13_backend.post.dto.PageResponseDto;
import com.ll.project_13_backend.post.dto.PostDto;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import groovy.transform.ToString;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Transactional
@ToString
@Log4j2
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

//    @Autowired
//    private PostSearch postSearch;

    @Test
    @DisplayName("게시글 찾기 ")
    void findPost() {

        Post post = Post.builder()
                .id(1L)
                .title("하하")
                .content("호호")

                .build();
        postRepository.save(post);

        PostDto postDto = postService.findPost(1L);

        assertThat(post.getId()).isEqualTo(postDto.getId());
//        assertThat(postDto.getTitle()).isEqualTo("하하");
    }

    @Test
    @DisplayName("글 등록")
    void createPost() {
        Member member = Member.builder()
                .id(1L)
                .userName("홍길동")
                .build();
        Member saveMember = memberRepository.save(member);


        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("안녕")
                .content("하세요")
                .memberId(1L)
                .memberName("홍길동")
                .build();

      Long post = postService.createPost(postDto , member);

        assertThat(postDto.getTitle()).isEqualTo("안녕");
        assertThat(postDto.getContent()).isEqualTo("하세요");
        assertThat(saveMember.getId()).isEqualTo(postDto.getMemberId());


    }

    @Test
    @DisplayName("글 수정")
    void updatePost() {
        // given
        PostDto postDto = PostDto.builder()
                .title("하하")
                .content("호호")
                .build();
        Post post = postRepository.save(postService.toEntity(postDto)); //repository 에 dto → 엔티티 변환 하여 저장


        post.setTitle("하하2"); // 제목 변경
        post.setContent("호호2"); //내용 변경
        PostDto postDto1 = postService.toDto(post); // 엔티티 → dto 로 변경

        postService.updatePost(postDto1 , post.getId()); // 실제 작성 메서드

        assertThat(postDto.getTitle()).isEqualTo("하하"); //기존 데이터
        assertThat(post.getTitle()).isEqualTo("하하2");
        assertThat(postDto.getTitle()).isNotEqualTo(postDto1.getTitle());
    }

    @Test
    @DisplayName("글 삭제")
    void deletePost() {
        Post post = Post.builder()
                .id(101L)
                .title("안녕")
                .content("하세요")
                .build();
        Post savePost = postRepository.save(post);

        postService.deletePost(savePost.getId());

        assertEquals(100 , postRepository.count());



    }

    @Test
    @DisplayName("글 작성자")
    void writeAuthor() {
        Member member = Member.builder()
                .id(1L)
                .userName("유저")
                .build();
        Member saveMember = memberRepository.save(member);

        PostDto postDto = PostDto.builder()
                .title("안녕")
                .content("호허")
                .memberId(saveMember.getId())
                .memberName(saveMember.getUserName())
                .build();

         Long savePost = postService.createPost(postDto , saveMember);




            assertThat(saveMember.getId()).isEqualTo(postDto.getMemberId());
        System.out.println("savePost = " + savePost);
            assertThat(saveMember).isEqualTo(2);
    }

//    @Test
//    @DisplayName("페이징")
//    void PageTest () {
//        //한 페이지 에  시작 번호는 0부터 10까지  이며 내림 차순
//        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
//
//        //db 에 저장 되어 있는 모든 데이터 들을 페이징 처리 한다.
//        Page<Post> posts =  postRepository.findAll(pageable);
//
//        assertThat(posts.getTotalElements()).isEqualTo(100L); // db 에 저장 되어 있는 전체 데이터
//        assertThat(posts.getSize()).isEqualTo(10L); //페이지당 보여줄 데이터의 갯수
//        assertThat(posts.getNumber()).isEqualTo(0L);
//
//    }
//
//    @Test
//    @DisplayName("검색")
//    void search() {
//
//       Page<Post> posts = postSearch.search();
//
//
//    }

    @Test
    @DisplayName("리스트 페이지 테스트 ")
    void list() {

        PageRequestDto pageRequestDto = PageRequestDto.builder()

                        .build();

      PageResponseDto<PostDto> pageResponseDto =  postService.listPost(pageRequestDto);


        assertThat(pageRequestDto.getPage()).isEqualTo(1);
        assertThat(pageRequestDto.getSize()).isEqualTo(10);
        assertThat(pageResponseDto.getTotalCount()).isEqualTo(100);
        assertThat(pageResponseDto.getPageNumList().size()).isEqualTo(10);

        log.info (postService.listPost(pageRequestDto));


    }
}