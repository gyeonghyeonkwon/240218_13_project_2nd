package com.ll.project_13_backend.comment.dto.response;

import com.ll.project_13_backend.comment.dto.service.FindCommentDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record FindCommentResponse(Long commentId,
                                  String content,
                                  LocalDateTime createdDate,
                                  String nickName) {

    public static FindCommentResponse of(final FindCommentDto findCommentDto) {
        FindCommentResponse findCommentResponse = FindCommentResponse.builder()
                .commentId(findCommentDto.commentId())
                .content(findCommentDto.content())
                .createdDate(findCommentDto.createdDate())
                .nickName(findCommentDto.nickName())
                .build();

        return findCommentResponse;
    }

    public static List<FindCommentResponse> of(final List<FindCommentDto> findCommentDtos) {
        List<FindCommentResponse> findCommentResponses = findCommentDtos.stream()
                .map(FindCommentResponse::of)
                .toList();

        return findCommentResponses;
    }
}
