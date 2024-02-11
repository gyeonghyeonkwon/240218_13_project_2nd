package com.ll.project_13_backend.comment.service;

import com.ll.project_13_backend.comment.dto.request.UpdateCommentRequest;
import com.ll.project_13_backend.comment.dto.service.CreateCommentDto;
import com.ll.project_13_backend.comment.dto.service.FindCommentDto;
import com.ll.project_13_backend.comment.dto.service.UpdateCommentDto;
import com.ll.project_13_backend.member.entity.Member;
import java.util.List;

public interface CommentService {
    void createComment(final Long postId, final CreateCommentDto createCommentDto, final Member member);
    List<FindCommentDto> findAllCommentByPostId(final Long postId);
    void updateComment(final Long commentId, final UpdateCommentDto updateCommentRequest, final Member member);
    void deleteComment(final Long commentId, final Member member);

}
