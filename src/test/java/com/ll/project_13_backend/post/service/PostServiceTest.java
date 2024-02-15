package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.post.dto.PostDto;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@Log4j2
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

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

        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("안녕")
                .content("하세요")
                .memberName("홍길동")
                .build();


        postService.createPost(postDto );

        assertThat(postDto.getTitle()).isEqualTo("안녕");
        assertThat(postDto.getContent()).isEqualTo("하세요");


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
                .id(1L)
                .title("안녕")
                .content("하세요")
                .build();
        Post savePost = postRepository.save(post);

        postService.deletePost(savePost.getId());

        assertEquals(0 , postRepository.count());
    }
}