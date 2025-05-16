package com.mycom.myapp.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeResponseDto {
    private boolean liked;
    private int likesCount;

    public static LikeResponseDto of(boolean liked, int likesCount) {
        return LikeResponseDto.builder()
            .liked(liked)
            .likesCount(likesCount)
            .build();
    }
}
