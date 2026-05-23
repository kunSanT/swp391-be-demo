/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.model.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.utils.DbUtils;

/**
 *
 * @author Thanh
 */
public class RegistrationDAO implements Serializable {

    public RegistrationDTO checkLogin(String username, String password)
            throws SQLException, ClassNotFoundException {

        RegistrationDTO result = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Model connects DB
            con = DbUtils.getConnection();
            if (con != null) {
                //2. Model manipulates DB
                //2.1 Create sql String
                String sql = "Select lastname, isAdmin "
                        + "From Registration "
                        + "Where username=? "
                        + "And password=? ";
                //2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //2.3 Execute Query
                rs = stm.executeQuery();

                //3. Model processes result
                if (rs.next()) {
//                    result = true;
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    
                    result = new RegistrationDTO(username, null, fullName, role);
                }

            }//connection is available
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccounts() {
        return accounts;
    }

    public void searchLastName(String searchValue)
            throws SQLException, ClassNotFoundException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Model connects DB
            con = DbUtils.getConnection();
            if (con != null) {
                //2. Model manipulates DB
                //2.1 Create sql String
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname LIKE ?";

                //2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //2.3 Execute Query
                rs = stm.executeQuery();
                //3. xử lí kết quả ( get dữ liệu từ DB về để xử lí)
                while (rs.next()) {
                    //3.1 model get dl từ result set
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullname = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    //3.2model; set dl từ DTO object
                    RegistrationDTO dto = new RegistrationDTO(username, password, fullname, role);
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    }
                    this.accounts.add(dto);
                }

            }// check connecttion avalilable
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }

    public boolean deleteAccount(String username)
            throws SQLException, ClassNotFoundException {

        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Model connects DB
            con = DbUtils.getConnection();
            if (con != null) {
                //2. Model manipulates DB
                //2.1 Create sql String
                String sql = "Delete From Registration "
                        + "Where username = ?";
                //2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //2.3 Execute Query
                int effectedRows = stm.executeUpdate();
                //3. Model processes result
                if (effectedRows > 0) {
                    result = true;
                }
            }//connection is available
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean createAccount(RegistrationDTO account)
            throws SQLException, ClassNotFoundException {

        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Model connects DB
            con = DbUtils.getConnection();
            if (con != null) {
                //2. Model manipulates DB
                //2.1 Create sql String
                String sql = "Insert Into Registration("
                        + "username, password, lastname, isAdmin"
                        + ") Values("
                        + "?, ?, ?, ?"
                        + ")";
                //2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, account.getUsername());
                stm.setString(2, account.getPassword());
                stm.setString(3, account.getFullName());
                stm.setBoolean(4, account.isRole());
                //2.3 Execute Query
                int effectedRows = stm.executeUpdate();
                //3. Model processes result
                if (effectedRows > 0) {
                    result = true;
                }
            }//connection is available
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean updateAccount(String username, String password, boolean isAdmin) 
        throws SQLException, ClassNotFoundException {

        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        try {
            //1. Model connects DB
            con = DbUtils.getConnection();
            if (con != null) {
                //2. Model manipulates DB
                //2.1 Create sql String
                String sql = "Update Registration "
                        + "Set password = ?, isAdmin = ? "
                        + "Where username = ?";
                //2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, isAdmin);
                stm.setString(3, username);
                
                //2.3 Execute Query
                int effectedRows = stm.executeUpdate();
                //3. Model processes result
                if (effectedRows > 0) {
                    result = true;
                }
            }//connection is available
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
        return result;
    }

}
