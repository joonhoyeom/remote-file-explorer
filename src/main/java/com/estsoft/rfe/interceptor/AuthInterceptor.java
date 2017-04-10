package com.estsoft.rfe.interceptor;

import com.estsoft.rfe.annotation.Auth;
import com.estsoft.rfe.vo.AdminVo;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by joonho on 2017-04-05.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler )	throws Exception {
        if( handler instanceof HandlerMethod == false ) {
            return true;
        }

        Auth auth = ( (HandlerMethod) handler ).getMethodAnnotation( Auth.class );
        // @Auth 가 없는 컨트롤러 핸들러
        // 접근 제어가 필요 없음
        if( auth == null ) {
            return true;
        }

        //접근 제어 (인증이 필요함)
        HttpSession session = request.getSession();
        if( session == null ) {
            response.sendRedirect("/");
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/login.jsp");
//			rd.forward( request, response );
            return false;
        }

        AdminVo authUser = (AdminVo)session.getAttribute( "authAdmin" );
        if( authUser == null ) {
            response.sendRedirect("/");
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/login.jsp");
//			rd.forward( request, response );
            return false;
        }

        // 인증된 사용자
        return true;
    }
}
