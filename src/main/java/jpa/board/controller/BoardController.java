package jpa.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/boards")
@Slf4j
public class BoardController {
    @GetMapping
    public String boards() {
        return "/board/boardList";
    }
}
