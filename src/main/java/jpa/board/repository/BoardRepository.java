package jpa.board.repository;

import jpa.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findBoardsById(Long id);
    Optional<Board> findBoardById(Long id);
    Page<Board> findAll(Pageable pageable);
    void deleteById(Long id);
}
