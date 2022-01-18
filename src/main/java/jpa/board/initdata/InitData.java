package jpa.board.initdata;

import jpa.board.domain.Board;
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
            for(int i = 0; i < 40; i++) {
                em.persist(Board.builder().title("title" + i).content("content" + i).build());
            }
        }
    }
}
