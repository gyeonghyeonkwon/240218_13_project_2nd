package com.ll.project_13_backend.post.repository;

import com.ll.project_13_backend.post.entity.Post;

import com.ll.project_13_backend.post.entity.QPost;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


public class PostSearchImpl extends QuerydslRepositorySupport implements  PostSearch {

    public PostSearchImpl() {
        super(Post.class);
    }

    @Override
    public Page<Post> search() {

        QPost post = QPost.post;

        JPQLQuery<Post> query = from(post);

        query.where(post.title.contains("1")); // 검색에 1인 값을 찾는다.

        Pageable pageable = PageRequest.of(1,10, Sort.by("id").descending());

        this.getQuerydsl().applyPagination(pageable ,query) ;

        query.fetch(); //목록 데이터

        query.fetchCount();


        return null;
    }
}
