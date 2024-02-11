package com.ll.project_13_backend.comment.dto.request;

import com.ll.project_13_backend.comment.dto.service.CreateCommentDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateCommentRequest(
        @NotBlank(message = "댓글 내용을 반드시 입력해주세요.") String content) {
    public CreateCommentDto toCreateCommentDto() {
        return CreateCommentDto.builder().content(content).build();
    }
}
