package com.ll.project_13_backend.comment.dto.service;

import com.ll.project_13_backend.comment.entity.Comment;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record FindCommentDto(Long commentId,
                             String content,
                             LocalDateTime createdDate,
                             String nickName) {

    public static FindCommentDto of(Comment comment) {
        FindCommentDto findCommentDto = FindCommentDto.builder()
                .commentId(comment.getId())
                .nickName(comment.getNickname())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .build();
        return findCommentDto;
    }

    public static List<FindCommentDto> ofList(List<Comment> comments) {
        List<FindCommentDto> findCommentDtos = comments
                .stream()
                .map(FindCommentDto::of)
                .toList();
        return findCommentDtos;
    }
}
