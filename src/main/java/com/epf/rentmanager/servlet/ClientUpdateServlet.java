package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import sun.tools.jconsole.JConsole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/update")
public class ClientUpdateServlet extends HttpServlet {
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
        try {
            Client client = this.getClientService().findById(Long.parseLong(req.getQueryString().substring(3)));
            req.setAttribute("nom",client.getNom());
            req.setAttribute("prenom",client.getPrenom());
            req.setAttribute("mail",client.getEmail());
            req.setAttribute("date_naissance",client.getNaissance());
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nom = req.getParameter("last_name");
        String prenom = req.getParameter("first_name");
        String email = req.getParameter("email");
        LocalDate naissance = LocalDate.parse(req.getParameter("birth_date"));
        try {
            this.getClientService().update(new Client(Long.parseLong(req.getQueryString().substring(3)),nom,prenom,email,naissance));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
