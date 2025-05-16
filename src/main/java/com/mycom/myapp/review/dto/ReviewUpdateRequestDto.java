package com.mycom.myapp.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateRequestDto {
    private String title;
    private String content;

    public void validateForUpdate() {
        if (title == null && content == null) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        if (title != null && title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목을 빈 칸으로 수정할 수 없습니다.");
        }

        if (content != null && content.trim().isEmpty()) {
            throw new IllegalArgumentException("내용을 빈 칸으로 수정할 수 없습니다.");
        }
    }
}
