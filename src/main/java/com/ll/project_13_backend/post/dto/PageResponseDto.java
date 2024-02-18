package com.ll.project_13_backend.post.dto;

import java.util.List;


public class PageResponseDto <E> { //제네릭

    private List<E> dtoList; //dto 목록

    private List <Integer> pageNumList;

    private PageRequestDto pageRequestDto;

    private boolean prev , next; //이전 , 다음

    private int totalCount , prevPage , nextPage , totalPage , current; //총 페이지 , 이전 페이지 , 다음 페이지 , 현재 페이지

    
}
