package jpa.board.domain;

import jpa.board.domain.dto.CommentCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.hibernate.metamodel.model.domain.IdentifiableDomainType;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Comment {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String writer;
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    public void setBoard(Board board) {
        this.board = board;
        board.getComments().add(this);
    }

    public Comment createComment(CommentCreateDto commentDto) {
        this.content = commentDto.getContent();

        return this;
    }
}
