package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.dto.PageRequestDto;
import com.ll.project_13_backend.post.dto.PageResponseDto;
import com.ll.project_13_backend.post.dto.PostDto;
import com.ll.project_13_backend.post.entity.Post;

public interface PostService {
    //생성
    Long createPost(final PostDto postDto , final Member member );
    //조회
    PostDto findPost(final Long postId );
    //수정
    void updatePost(final PostDto postDto , final Long postId );
    //삭제
    void deletePost(final Long postId);
    //목록
    PageResponseDto<PostDto> listPost(PageRequestDto pageRequestDto);


    // 엔티티 → dto
    default PostDto toDto(final Post post ) {

        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .memberId(post.getMember().getId())
                .memberName(post.getMember().getUserName())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }

    // dto → 엔티티
    default Post toEntity(final PostDto postDto ) {

        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
    }
}