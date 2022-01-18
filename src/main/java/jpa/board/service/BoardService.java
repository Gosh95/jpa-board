package jpa.board.service;

import jpa.board.domain.Board;
import jpa.board.domain.dto.BoardEditDto;
import jpa.board.exception.DuplicatedException;
import jpa.board.exception.NotExistException;
import jpa.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public Long createBoard(Board board) {
        Board newBoard = checkBoard(board);

        boardRepository.save(newBoard);

        return board.getId();
    }

    @Transactional(readOnly = true)
    public Board findBoard(Long id) {
        Optional<Board> board = boardRepository.findBoardById(id);

        return board.orElseThrow(() -> new NotExistException("존재하지 않는 게시글 입니다."));
    }

    private Board checkBoard(Board board) throws DuplicatedException{
        List<Board> boards = boardRepository.findBoardsById(board.getId());

        if(!boards.isEmpty()) {
            throw new DuplicatedException("중복되는 게시글이 존재합니다.");
        }

        return board;
    }

    public Board editBoard(BoardEditDto boardEditDto) {
        Board board = findBoard(boardEditDto.getId());

        return board.editBoard(boardEditDto);
    }

    @Transactional(readOnly = true)
    public Page<Board> findAllBoard(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 15);

        return boardRepository.findAll(pageable);
    }

    public void deleteBoard(Long id) {
        boardRepository.delete(findBoard(id));
    }
}
