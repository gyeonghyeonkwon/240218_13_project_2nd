package com.ll.project_13_backend.post.entity;

import com.ll.project_13_backend.global.exception.ErrorCode;
import com.ll.project_13_backend.global.exception.InvalidValueException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Category {

    KOR,
    ENG,
    MATH;

    public static Category match(String category) {
        return Arrays.stream(values())
                .filter(c -> c.name().equals(category))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE));
    }
}
