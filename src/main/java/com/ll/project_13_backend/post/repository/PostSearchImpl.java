package com.ll.project_13_backend.post.repository;

import com.ll.project_13_backend.post.dto.PageRequestDto;
import com.ll.project_13_backend.post.entity.Post;
import com.ll.project_13_backend.post.entity.QPost;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


public class PostSearchImpl extends QuerydslRepositorySupport implements  PostSearch {

    public PostSearchImpl() {
        super(Post.class);
    }

    @Override
    public Page<Post> search(PageRequestDto pageRequestDto) {

        QPost post = QPost.post;

        JPQLQuery<Post> query = from(post);

//        query.where(post.title.contains("1")); // 검색에 1인 값을 찾는다.
        //페이지 번호 1부터 시작
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(), Sort.by("id").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<Post> list = query.fetch(); //목록 데이터

        long total = query.fetchCount();


        return new PageImpl<>(list, pageable, total);
    }
}
