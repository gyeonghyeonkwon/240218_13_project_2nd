package com.ll.project_13_backend.post.repository;

import com.ll.project_13_backend.post.dto.PageRequestDto;
import com.ll.project_13_backend.post.entity.Post;
import org.springframework.data.domain.Page;

public interface PostSearch {

    Page<Post> search(PageRequestDto pageRequestDto);
}
