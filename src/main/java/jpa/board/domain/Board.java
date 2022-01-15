package jpa.board.domain;

import jpa.board.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board extends TimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long recommendation;

    @BatchSize(size = 200)
    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    public Board createBoard(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();

        return this;
    }
}
