package jpa.board.controller;

import jpa.board.domain.Board;
import jpa.board.dto.BoardDto;
import jpa.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardRepository boardRepository;

    @GetMapping
    public String boards(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);

        return "/board/boardList";
    }

    @GetMapping("/new-board")
    public String newBoardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());

        return "/board/boardForm";
    }
}
