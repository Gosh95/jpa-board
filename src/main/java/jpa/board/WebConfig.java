package jpa.board;

import jpa.board.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/boards/new-board")
                .excludePathPatterns("/", "/boards/boardId/comments", "/boards", "/login", "/logout", "/css/**", "/style.css", "/members/new");
    }
}
