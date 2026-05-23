/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.model.registration.Registration;
import pe.model.registration.RegistrationBLO;
import pe.model.registration.RegistrationDAO;
import pe.model.registration.RegistrationDTO;

/**
 *
 * @author Thanh
 */
public class LoginServlet extends HttpServlet {

    private static final String SEARCH_PAGE = "search.jsp";
    private static final String INVALID_PAGE = "invalid.html";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        PrintWriter out = response.getWriter();
        String url = INVALID_PAGE;
        //1. get all user's information
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");

        try {
            //2. Controller calls methods of model.
            //2.1 Controller initializes DAO object (new operator)
//            RegistrationDAO dao = new RegistrationDAO();
            RegistrationBLO blo = new RegistrationBLO();
            //2.2 Controller calls methods of DAO object
//            RegistrationDTO result = dao.checkLogin(username, password);
            Registration result = blo.checkLogin(username, password);
            //3. Controller processes result
//            if(result) {
            if(result != null) {
                url = SEARCH_PAGE;
                //write cookie
                Cookie cookie = new Cookie(username, password);
                cookie.setMaxAge(60*5);
                response.addCookie(cookie);
                //store session
                HttpSession session = request.getSession();
                session.setAttribute("USERINFO", result);
            }
//        } catch (SQLException ex) {
//            log("LoginServlet _ SQL" + ex.getMessage());
//        } catch (ClassNotFoundException ex) {
//            log("LoginServlet _ SQL" + ex.getMessage());
        } finally {
            //send to view
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
