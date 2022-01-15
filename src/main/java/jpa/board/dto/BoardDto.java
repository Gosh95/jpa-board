package jpa.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    @NotEmpty(message = "제목을 채워주세요.")
    @Size(min=2, max=20, message = "제목을 2 - 20 글자로 조절해주세요.")
    private String title;

    @NotEmpty(message = "내용을 채워주세요.")
    @Size(min=4, message = "내용을 조금 더 작성해주세요.(4글자 이상)")
    private String content;
}
