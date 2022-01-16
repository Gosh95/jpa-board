package jpa.board.dto;

import jpa.board.domain.Board;
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
    @NotEmpty(message = "제목을 작성해주세요.")
    @Size(min=2, message = "제목을 2글자 이상 작성해주세요.")
    private String title;

    @NotEmpty(message = "내용을 작성해주세요.")
    @Size(min=4, message = "내용을 조금 더 작성해주세요.(4글자 이상)")
    private String content;

    public static BoardDto convertToBoardDto(Board board) {
        return new BoardDto(board.getTitle(), board.getContent());
    }
}
