package com.ll.project_13_backend.comment.controller;

import com.ll.project_13_backend.comment.dto.request.CreateCommentRequest;
import com.ll.project_13_backend.comment.dto.request.UpdateCommentRequest;
import com.ll.project_13_backend.comment.dto.response.FindCommentResponse;
import com.ll.project_13_backend.comment.dto.service.CreateCommentDto;
import com.ll.project_13_backend.comment.dto.service.FindCommentDto;
import com.ll.project_13_backend.comment.dto.service.UpdateCommentDto;
import com.ll.project_13_backend.comment.service.CommentService;
import com.ll.project_13_backend.global.config.CurrentMember;
import com.ll.project_13_backend.member.entity.Member;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}")
    public ResponseEntity<Void> createComment(@PathVariable(name = "postId") Long postId,
                                              @Valid @RequestBody CreateCommentRequest createCommentRequest,
                                              @CurrentMember Member member) {

        CreateCommentDto createCommentDto = createCommentRequest.toCreateCommentDto();
        commentService.createComment(postId, createCommentDto, member);
        return ResponseEntity.created(URI.create("")).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<FindCommentResponse>> findCommentAllByPostId(@PathVariable(name = "postId") Long postId) {

        List<FindCommentDto> findCommentDto = commentService.findAllCommentByPostId(postId);
        List<FindCommentResponse> findCommentResponse = FindCommentResponse.of(findCommentDto);
        return ResponseEntity.ok().body(findCommentResponse);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable(name = "commentId") Long commentId,
                                              @Valid @RequestBody UpdateCommentRequest updateCommentRequest,
                                              @CurrentMember Member member) {

        UpdateCommentDto updateCommentDto = updateCommentRequest.toUpdateCommentDto();
        commentService.updateComment(commentId, updateCommentDto, member);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "commentId") Long commentId,
                                              @CurrentMember Member member) {

        commentService.deleteComment(commentId, member);
        return ResponseEntity.noContent().build();
    }
}
