package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");

        if (login == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } else {
            try {
                accountService.addNewUser(new UserProfile(login, password, login + "@example.ru"));
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().println("User " + login + " is successfully added");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}