package jpa.board.dto;

import jpa.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    @NotEmpty
    @NotBlank
    @Size(min=2)
    private String title;

    @NotEmpty
    @NotBlank
    @Size(min=4)
    private String content;

    public static BoardDto convertToBoardDto(Board board) {
        return new BoardDto(board.getTitle(), board.getContent());
    }
}
