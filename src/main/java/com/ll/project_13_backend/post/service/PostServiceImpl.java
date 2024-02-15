package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.global.exception.EntityNotFoundException;
import com.ll.project_13_backend.global.exception.ErrorCode;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.dto.PostDto;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Long createPost(final PostDto postDto) {

        Post post = toEntity(postDto ); //@service 메서드 에 선언

        Post postCreate = postRepository.save(post); // 게시글 엔티티 저장

        return postCreate.getId(); //id 값 반환
    }
    @Override
    public PostDto findPost(final Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        return toDto(post); //엔티티 DTO 로 변환
    }

    @Transactional
    public void updatePost(final PostDto postDto , final Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND)); // 글 못 찾으면 예외

        post.setTitle(postDto.getTitle()); //제목 수정
        post.setContent(postDto.getContent()); //내용 수정

        postRepository.save(post);

    }

    @Transactional
    public void deletePost(final Long postId) {

        postRepository.deleteById(postId);
    }

    public List<PostDto> listPost() {

        return postRepository.findAll().stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .createdDate(post.getCreatedDate())
                        .modifiedDate(post.getModifiedDate())
                        .build())
                .collect(Collectors.toList());
    }

}
