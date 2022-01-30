package jpa.board.initdata;

import jpa.board.domain.Address;
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

            Member test = Member.builder().name("kim").password("1234").email("test@qq.qq").loginId("test").address(new Address("korea", "jeju", "63099")).build();
            em.persist(test);

            for(int i = 0; i < 200; i++) {
                Member member = Member.builder().loginId("kim" + i).build();
                em.persist(member);

                Board board = Board.builder().content("내용" + i).title("제목" + i).build();
                board.setMember(member);
                member.getBoards().add(board);
                em.persist(board);


                for(int j = 0; j < 50; j++) {
                    Comment comment = Comment.builder().content("댓글댓글댓글댓글댓글댓글댓글댓글댓글댓글댓글댓글댓글" + j).build();
                    comment.setBoard(board);

                    em.persist(comment);
                }
            }

            em.persist(Member.builder().loginId("test1").password("1234").build());
        }
    }
}
