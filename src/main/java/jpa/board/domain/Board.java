package jpa.board.domain;

import jpa.board.domain.dto.BoardCreateDto;
import jpa.board.domain.dto.BoardEditDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
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
    @Column(length = 300)
    private String content;
    private Long likes;
    private Long views;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @BatchSize(size = 200)
    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    public void addBoard(Member member) {
        this.member = member;
        this.member.getBoards().add(this);
    }

    @PrePersist
    public void prePersist() {
        this.likes = 0L;
        this.views = 0L;
    }

    public Board createBoard(BoardCreateDto boardCreateDto) {
        this.title = boardCreateDto.getTitle();
        this.content = boardCreateDto.getContent();
        this.likes = 0L;
        this.views = 0L;

        return this;
    }

    public Board editBoard(BoardEditDto boardEditDto) {
        this.title = boardEditDto.getTitle();
        this.content = boardEditDto.getContent();

        return this;
    }

    public void addViews() {
        this.views++;
    }

    public void addLikes() {
        this.likes++;
    }
}
