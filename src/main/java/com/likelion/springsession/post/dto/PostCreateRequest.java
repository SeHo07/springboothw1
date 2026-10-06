package com.likelion.springsession.post.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "제목은 필수!")
    @Size(max=100, message = "제목은 100자 이하!")
    private String title;

    @NotBlank(message = "본문은 필수!")
    @Size(max = 2000, message = "본문은 2000자 이하!")
    private String content;

}
