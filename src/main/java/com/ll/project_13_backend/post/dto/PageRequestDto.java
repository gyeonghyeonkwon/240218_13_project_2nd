package com.ll.project_13_backend.post.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDto {

    @Builder.Default
    private int  page = 1;

    @Builder.Default
    private int size = 10;
}
