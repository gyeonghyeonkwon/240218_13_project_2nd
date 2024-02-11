package com.ll.project_13_backend.comment.dto.request;

import com.ll.project_13_backend.comment.dto.service.UpdateCommentDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdateCommentRequest(@NotBlank(message = "댓글 내용을 반드시 입력해주세요.") String content) {

    public UpdateCommentDto toUpdateCommentDto() {
        UpdateCommentDto updateCommentDto = UpdateCommentDto.builder()
                .content(content)
                .build();

        return updateCommentDto;
    }
}
