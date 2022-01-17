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
    @NotNull
    private Long id;

    @NotEmpty
    @NotBlank
    @Size(min=2)
    private String title;

    @NotEmpty
    @NotBlank
    @Size(min=4)
    private String content;

    public static BoardEditDto convertToBoardEditDto(Board board) {
        return new BoardEditDto(board.getId(), board.getTitle(), board.getContent());
    }
}
