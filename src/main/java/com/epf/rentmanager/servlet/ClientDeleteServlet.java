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

@WebServlet("/users/delete")
public class ClientDeleteServlet extends HttpServlet {
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
            getClientService().delete(new Client(Integer.parseInt(req.getQueryString().substring(3))));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        System.out.println("Context path : "+req.getContextPath());
        System.out.println("Query string : "+req.getQueryString());
        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
