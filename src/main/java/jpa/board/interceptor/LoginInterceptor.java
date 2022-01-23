package jpa.board.interceptor;

import jpa.board.domain.dto.sessionname.SessionName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Base64;

import static jpa.board.domain.dto.sessionname.SessionName.SESSION_ID;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SESSION_ID) == null) {
            log.info("미 인증 사용자 : [{}]", requestURI);

            response.sendRedirect("/login?redirectURL=" + requestURI);

            return false;
        }

        return true;
    }
}
