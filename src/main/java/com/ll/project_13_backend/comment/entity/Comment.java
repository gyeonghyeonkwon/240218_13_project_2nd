package com.ll.project_13_backend.comment.entity;

import com.ll.project_13_backend.comment.dto.service.UpdateCommentDto;
import com.ll.project_13_backend.global.BaseEntity;
import com.ll.project_13_backend.global.exception.AuthException;
import com.ll.project_13_backend.global.exception.ErrorCode;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    public String getNickname() {
        String nickName = member.getNickName();
        return nickName;
    }

    public void checkAuthorization(Member member) {
        if (this.member.getId() != member.getId()) {
            throw new AuthException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    public void updateComment(final UpdateCommentDto updateCommentDto) {
        this.content = updateCommentDto.content();
    }
}
