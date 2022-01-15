package jpa.board.controller;

import jpa.board.domain.Board;
import jpa.board.dto.BoardDto;
import jpa.board.exception.NotExistException;
import jpa.board.repository.BoardRepository;
import jpa.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
//    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @GetMapping
    public String boards(Model model) {
        List<Board> boards = boardService.findAllBoard();
        model.addAttribute("boards", boards);

        return "/board/boardList";
    }

    @GetMapping("/new-board")
    public String createBoardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());

        return "/board/boardForm";
    }

    @PostMapping("/new-board")
    public String createBoard(@ModelAttribute BoardDto boardDto, RedirectAttributes redirectAttributes) {
        Board board = new Board();
        Board newBoard = board.createBoard(boardDto);

        Long boardId = boardService.upload(newBoard);

        redirectAttributes.addAttribute("boardId", boardId);

        return "redirect:/boards/{boardId}";
    }

    @GetMapping("/{boardId}")
    public String readBoard(@PathVariable("boardId") Long boardId, Model model) {
        try {
            Board findBoard = boardService.findBoard(boardId);

            model.addAttribute("board", findBoard);
        } catch (NotExistException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return "/board/board";
    }

}
