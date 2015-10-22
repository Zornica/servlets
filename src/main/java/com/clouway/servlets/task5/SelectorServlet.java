package com.clouway.servlets.task5;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by clouway on 15-10-22.
 */
public class SelectorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String user = req.getParameter("user");
        PrintWriter out = resp.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "clouway.com");
            PreparedStatement ps = con.prepareStatement("select currentSum from register where user = ? ");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            double sum = rs.getDouble("currentSum");

        BankAccount account = new BankAccount(sum);

        if (req.getParameter("check") != null) {
            out.write("<p>You have "+ account.currentState()+" in your account!</p>");
        } else if (req.getParameter("add") != null) {
            account.add(Double.parseDouble(req.getParameter("sum")));
            out.write("<p>You have " + account.currentState() + " in your account!</p>");
        } else if (req.getParameter("remove") != null) {
            account.remove(Double.parseDouble(req.getParameter("sum")));
            out.write("<p>You have "+ account.currentState()+" in your account!</p>");
        }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}