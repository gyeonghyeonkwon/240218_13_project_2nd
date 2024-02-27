package com.ll.project_13_backend.post.entity;

import com.ll.project_13_backend.comment.entity.Comment;
import com.ll.project_13_backend.global.BaseEntity;
import com.ll.project_13_backend.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
//@Builder(toBuilder = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter  //임시로 setter 허용
/**
 * 값이 변경 가능한 필드만 Setter 를 허용한다.
 */
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE )
    private List<Comment> commentList;
}
