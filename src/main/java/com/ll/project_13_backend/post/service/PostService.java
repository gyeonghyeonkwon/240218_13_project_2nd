package com.ll.project_13_backend.post.service;

import com.ll.project_13_backend.post.dto.PostDto;
import com.ll.project_13_backend.post.entity.Post;

import java.util.List;

public interface PostService {
    //생성
    Long createPost(final PostDto postDto);
    //조회
    PostDto findPost(final Long postId);
    //수정
    void updatePost(final PostDto postDto , final Long postId );
    //삭제
    void deletePost(final Long postId);
    //목록
    List<PostDto> listPost();


    // 엔티티 → dto
    default PostDto toDto(Post post) {

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
    default Post toEntity(PostDto postDto) {

        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
    }
}