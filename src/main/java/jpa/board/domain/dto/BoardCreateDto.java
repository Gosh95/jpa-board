package jpa.board.domain.dto;

import jpa.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
public class BoardCreateDto {
    @NotEmpty
    @NotBlank
    @Size(min=2)
    private String title;

    @NotEmpty
    @NotBlank
    @Size(min=4)
    private String content;
}
