package com.ll.project_13_backend.post.controller;

import com.ll.project_13_backend.global.rsData.RsData;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.service.MemberService;
import com.ll.project_13_backend.post.dto.PostDto;
import com.ll.project_13_backend.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/post")
@CrossOrigin("*")
public class PostController {

    private final MemberService memberService;


    private final PostService postService;
    //글 목록
    @GetMapping("/list")
    public RsData<List<PostDto>> getPostList() {

        return RsData.of("200" , "성공" , postService.listPost());
    }

    //글 생성

    @PostMapping("/create")
    public RsData<Long> creatPost(@Valid @RequestBody final PostDto postDto ) {

        Member member = memberService.findMember(postDto.getMemberId()); //멤버를 찾는다

        return RsData.of("200" , "성공" , postService.createPost(postDto , member));

    }
    //글 상세
    @GetMapping("/detail/{id}")
    public RsData<PostDto> showUpdatePost(@PathVariable final Long id) {

        return RsData.of("200" , "성공" , postService.findPost(id ));
    }
    //글 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updatePost(@Valid @RequestBody final PostDto postDto , @PathVariable final Long id) {

        postService.updatePost(postDto, id);

        return ResponseEntity.ok().build();

    }
    //글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable final Long id) {

        postService.deletePost(id);

        return ResponseEntity.ok().build();
    }

}