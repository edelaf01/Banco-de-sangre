/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.registroDao;
import pojo.User;

/**
 *
 * @author kkkk
 */
public class registroServlet extends HttpServlet {

    public registroServlet() {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Copying all the input parameters in to local variables

        String type = request.getParameter("tipo");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        //Using Java Beans - An easiest way to play with group of related data
        user.setUsername(userName);
        user.setType(type);

        user.setPassword(password);

        registroDao registerDao = new registroDao();

        //The core Logic of the Registration application is present here. We are going to insert user data in to the database.
        String userRegistered = registerDao.registerUser(user);

        if (userRegistered.equals("SUCCESS")) //On success, you can display a message to user on Home page
        {
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", userRegistered);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

}
