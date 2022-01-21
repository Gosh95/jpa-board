package jpa.board.repository;

import jpa.board.domain.Board;
import jpa.board.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CommentRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void test_01() throws Exception {
        Board board = Board.builder().content("content").title("title").build();
        Comment comment1 = Comment.builder().content("content").build();
        Comment comment2 = Comment.builder().content("content").build();
        Comment comment3 = Comment.builder().content("content").build();

        boardRepository.save(board);

        comment1.setBoard(board);
        comment2.setBoard(board);
        comment3.setBoard(board);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
    }
}