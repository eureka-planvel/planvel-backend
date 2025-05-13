package com.mycom.myapp.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeResponseDto {
    private boolean liked;
    private int likesCount;
}
