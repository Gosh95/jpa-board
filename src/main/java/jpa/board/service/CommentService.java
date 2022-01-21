package jpa.board.service;

import jpa.board.domain.Board;
import jpa.board.domain.Comment;
import jpa.board.repository.BoardRepository;
import jpa.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long createComment(Long boardId, Comment comment) {
        Board board = boardRepository.findBoardById(boardId).orElse(null);
        comment.setBoard(board);

        return commentRepository.save(comment).getId();
    }

    @Transactional(readOnly = true)
    public Slice<Comment> findComments(Long boardId, Pageable pageable) {
        return commentRepository.findComments(boardId, pageable);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
