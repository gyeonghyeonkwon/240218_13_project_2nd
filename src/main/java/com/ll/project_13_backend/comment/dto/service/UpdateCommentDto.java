package com.ll.project_13_backend.comment.dto.service;

import lombok.Builder;

@Builder
public record UpdateCommentDto(String content) {
}