package com.estsoft.rfe.interceptor;

import com.estsoft.rfe.repository.AdminRepository;
import com.estsoft.rfe.vo.AdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by joonho on 2017-04-05.
 */
public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ( "GET".equals( request.getMethod() )){
            return true;
        }
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        AdminVo adminVo = new AdminVo();
        adminVo.setEmail(email);
        adminVo.setPassword(password);
        AdminVo authUser = adminRepository.findByEmailPassword(adminVo);
        if(authUser == null){
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            rd.forward( request, response );
            return false;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("authAdmin", authUser );
        response.sendRedirect( "main" );
        return false;
    }
}
