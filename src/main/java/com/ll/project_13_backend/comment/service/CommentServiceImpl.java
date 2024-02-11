package com.ll.project_13_backend.comment.service;

import com.ll.project_13_backend.comment.dto.service.CreateCommentDto;
import com.ll.project_13_backend.comment.dto.service.FindCommentDto;
import com.ll.project_13_backend.comment.dto.service.UpdateCommentDto;
import com.ll.project_13_backend.comment.entity.Comment;
import com.ll.project_13_backend.comment.repository.CommentRepository;
import com.ll.project_13_backend.global.exception.EntityNotFoundException;
import com.ll.project_13_backend.global.exception.ErrorCode;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(final Long postId, final CreateCommentDto createCommentDto, final Member member) {
        checkExistsByPostId(postId);
        Comment comment = Comment.builder()
                .member(member)
                .content(createCommentDto.content())
                .build();
        commentRepository.save(comment);
    }

    public List<FindCommentDto> findAllCommentByPostId(final Long postId) {
        checkExistsByPostId(postId);
        final List<Comment> comments = commentRepository.findAllByPostId(postId);
        final List<FindCommentDto> findCommentDtos = FindCommentDto.ofList(comments);
        return findCommentDtos;
    }

    @Transactional
    public void updateComment(final Long commentId, final UpdateCommentDto updateCommentDto, final Member member) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        comment.checkAuthorization(member);
        comment.updateComment(updateCommentDto);
    }

    @Transactional
    public void deleteComment(final Long commentId, final Member member) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        comment.checkAuthorization(member);

        commentRepository.delete(comment);
    }

    private void checkExistsByPostId(final Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND);
        }
    }

}
