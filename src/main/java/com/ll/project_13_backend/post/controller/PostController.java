package com.ll.project_13_backend.post.controller;

import com.ll.project_13_backend.global.rsData.RsData;
import com.ll.project_13_backend.member.entity.Member;
import com.ll.project_13_backend.member.service.MemberService;
import com.ll.project_13_backend.post.dto.PageRequestDto;
import com.ll.project_13_backend.post.dto.PageResponseDto;
import com.ll.project_13_backend.post.dto.PostDto;
import com.ll.project_13_backend.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/post")
@CrossOrigin("*")
@Log4j2
@ToString
public class PostController {

    private final MemberService memberService;
    private final PostService postService;

//컨트롤러 - DTO - 서비스 - Entity - 레포지터리
//    @Getter
//    public static class GetPostsResponseBody {
//        List<PostDto> items;
//        public GetPostsResponseBody(List<PostDto> items) {
//           this.items = items;
//        }
//    }



    //글 목록
    @GetMapping("/list")
    public RsData<PageResponseDto<PostDto>> getPostList(PageRequestDto pageRequestDto) {

        return RsData.of("200" , "게시글 목록 조회에 성공 하였습니다" ,postService.listPost(pageRequestDto));
    }

    //글 생성

    @PostMapping("/create")
    public RsData<Long> creatPost(@Valid @RequestBody final PostDto postDto) {

        Member member = memberService.findMember(postDto.getMemberId()); //멤버를 찾는다

        log.info("DTO : " + postDto.toString().toString());

        return RsData.of("200" , "게시글 등록에 성공 하였습니다." , postService.createPost(postDto , member));

    }
    //글 상세
    @GetMapping("/detail/{id}")
    public RsData<PostDto> showUpdatePost(@PathVariable final Long id) {

        return RsData.of("200" , "성공" , postService.findPost(id ));
    }
    //글 수정
    @PutMapping("/update/{id}")
    public RsData<PostDto> updatePost(@Valid @RequestBody final PostDto postDto , @PathVariable final Long id) {

        postService.updatePost(postDto, id);

        return RsData.of("200" , "수정 성공" , postService.findPost(id));

    }
    //글 삭제
    @DeleteMapping("/delete/{id}")
    public RsData<PostDto> deletePost(@PathVariable final Long id) {

        PostDto postDto = postService.findPost(id);

        postService.deletePost(id);

        return RsData.of("200" , "삭제 성공" , postDto);
    }

}