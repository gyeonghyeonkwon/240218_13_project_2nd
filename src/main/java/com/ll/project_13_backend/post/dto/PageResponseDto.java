package com.ll.project_13_backend.post.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Data
public class PageResponseDto <E> { //제네릭

    private List<E> dtoList; //dto 목록

    private List <Integer> pageNumList;

    private PageRequestDto pageRequestDto;

    private boolean prev , next; //이전 , 다음

    private int totalCount , prevPage , nextPage , totalPage , current; //총 페이지 , 이전 페이지 , 다음 페이지 , 현재 페이지

   @Builder(builderMethodName = "withAll")
    public PageResponseDto(List<E> dtoList , PageRequestDto pageRequestDto , long totalCount) {

        this.dtoList = dtoList;

        this.pageRequestDto = pageRequestDto;

        this.totalCount = (int) totalCount ;

        // 시작 페이지 계산
        int end = (int) (Math.ceil(pageRequestDto.getPage() / 10.0))  * 10;

        //시작 페이지 결과
        int start = end - 9;

        //마지막 페이지
        int last = (int)(Math.ceil(totalCount/(double)pageRequestDto.getSize()));

        end = end > last ? last : end ;

        this.prev = start > 1 ;

        this.next = totalCount > end * pageRequestDto.getSize();

        this.pageNumList =  IntStream.rangeClosed(start , end ).boxed().collect(Collectors.toList()); //페이지 번호 들

        this.prevPage = prev ? start - 1 : 0 ; //이전페이지

        this.nextPage = next ? end + 1 : 0; //다음페이지

        this.totalPage = this.pageNumList.size();

        this.current = pageRequestDto.getPage(); //현재 페이지
    }
}
