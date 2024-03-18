package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public ClientService getClientService() {
        return clientService;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(req, resp);
        String nom = req.getParameter("last_name");
        String prenom = req.getParameter("first_name");
        String email = req.getParameter("email");
        LocalDate naissance = LocalDate.parse(req.getParameter("birth_date"));
        try {
            getClientService().create(new Client(nom,prenom,email,naissance));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

    }
}
