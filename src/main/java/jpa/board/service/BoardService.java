package jpa.board.service;

import jpa.board.domain.Board;
import jpa.board.exception.NotExistException;
import jpa.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public Long upload(Board board) {
        boardRepository.save(board);

        return board.getId();
    }

    @Transactional(readOnly = true)
    public Board findBoard(Long id) throws NotExistException {
        Optional<Board> findBoard = boardRepository.findById(id);

        return findBoard.orElseThrow(() -> new NotExistException("존재하지 않는 게시글입니다."));
    }

    private List<Board> verifyBoard(Long id) throws NotExistException {
        List<Board> boards = boardRepository.findBoardsById(id);

        if(boards.isEmpty()) {
            throw new NotExistException("존재하지 않는 게시글입니다.");
        }

        return boards;
    }

    @Transactional(readOnly = true)
    public List<Board> findAllBoard() {
        return boardRepository.findAll();
    }

    public void deleteBoard(Long id) {
        boardRepository.delete(findBoard(id));
    }
}
