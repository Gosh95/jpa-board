package jpa.board.domain.dto;

import jpa.board.domain.Board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardEditDto {
    private Long id;

    @NotBlank
    @Size(min=2)
    private String title;

    @NotBlank
    @Size(min=4)
    private String content;

    public static BoardEditDto convertToBoardEditDto(Board board) {
        BoardEditDto boardEditDto = new BoardEditDto();
        boardEditDto.setId(board.getId());
        boardEditDto.setTitle(board.getTitle());
        boardEditDto.setContent(board.getContent());

        return boardEditDto;
    }
}
