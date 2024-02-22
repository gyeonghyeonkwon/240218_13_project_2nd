package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.global.exception.EntityNotFoundException;
import com.ll.project_13_backend.global.exception.ErrorCode;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.repository.MemberRepository;
import com.ll.project_13_backend.post.dto.PageRequestDto;
import com.ll.project_13_backend.post.dto.PageResponseDto;
import com.ll.project_13_backend.post.dto.PostDto;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Long createPost(final PostDto postDto , final Member member) {

        Post post = toEntity(postDto); //@service 메서드 에 선언

        post.setMember(member); //회원 저장

        Post postCreate = postRepository.save(post); // 게시글 엔티티 저장

        return  postCreate.getId(); //id 값 반환
    }

    public PostDto findPost(final Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        return toDto(post); //엔티티 DTO 로 반환
    }

    @Transactional
    public void updatePost(final PostDto postDto , final Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND)); // 글 못 찾으면 예외

//        post.setTitle(postDto.getTitle()); //제목 수정
//        post.setContent(postDto.getContent()); //내용 수정
        /**
         * DTO -> Entity 로 변환하여 DB 저장
         */
        post = Post.builder()
                .id(post.getId()) // 기존 ID 유지
                .title(postDto.getTitle()) // 제목 수정
                .content(postDto.getContent()) //내용 수정
                .member(post.getMember()) // 멤버 정보는 변경하지 않음
                .build();

    postRepository.save(post);
    }

    @Transactional
    public void deletePost(final Long postId) {

        postRepository.deleteById(postId);
    }

    public PageResponseDto<PostDto> listPost(PageRequestDto pageRequestDto) {

        Page<Post> pagePostList = postRepository.search(pageRequestDto);

          List<PostDto> dtoList =   pagePostList.get().map(
                post -> toDto(post)).collect(Collectors.toList());

         PageResponseDto<PostDto> responseDto =
                  PageResponseDto.<PostDto>withAll()
                          .dtoList(dtoList)
                          .pageRequestDto(pageRequestDto)
                          .totalCount(pagePostList.getTotalElements())
                    .build();

         return responseDto;
    }


}
