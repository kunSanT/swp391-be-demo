/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pe.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.model.registration.RegistrationBLO;
import pe.model.registration.RegistrationDAO;

/**
 *
 * @author Thanh
 */
@WebServlet(name = "DeletePkServlet", urlPatterns = {"/DeletePkServlet"})
public class DeletePkServlet extends HttpServlet {

    private static String ERROR_PAGE = "error.html";

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
        //1.Controller get user's information
        String username = request.getParameter("pk");
        String searchValue = request.getParameter("lastSearchValue");
        String url = ERROR_PAGE;

        try {
            //2. Controller calls methods of model.
            //2.1 Controller initializes DAO object
//            RegistrationDAO dao = new RegistrationDAO();
            RegistrationBLO blo = new RegistrationBLO();
            //2.2 Controller calls methods of DAO object
//            boolean result = dao.deleteAccount(username);
            boolean result = blo.deleteAccount(username);
            //3. Controller processes result
            if (result) {
                //refresh: call previous function again
                //--> remind ===> add request parameters in to url
                //===> How many? same as quantity of input tag of previous fuction
                //applied  call Search again, add requesst parameter
                url = "MainController"
                        + "?action=Search"
                        + "&txtSearchValue=" + searchValue;
            }
//        } catch(SQLException ex) {
//            log("DeletePkServlet_SQL: "+ex.getMessage());
//        }catch(ClassNotFoundException ex) {
//            log("DeletePkServlet_Class Not Found: "+ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
