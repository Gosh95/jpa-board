package jpa.board.initdata;

import jpa.board.domain.Board;
import jpa.board.domain.Comment;
import jpa.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("local")
@RequiredArgsConstructor
@Component
public class InitData {
    private final InitService initService;

    @PostConstruct
    public void addInitData() {

        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static private class InitService {
        private final EntityManager em;

        public void init() {
            for(int i = 0; i < 200; i++) {
                Board board = Board.builder().content("내용" + i).title("제목" + i).build();
                em.persist(board);

                for(int j = 0; j < 50; j++) {
                    Comment comment = Comment.builder().content("댓글댓글댓글댓글댓글댓글댓글댓글댓글댓글댓글댓글댓글" + j).build();
                    comment.setBoard(board);

                    em.persist(comment);
                }
            }
        }
    }
}
