package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    ReservationService reservationService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;

    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public VehicleService getVehicleService() {
        return vehicleService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Vehicule> vehicules = this.getVehicleService().findAll();
            List<Client> clients = this.getClientService().findAll();
            req.setAttribute("vehicles",vehicules);
            req.setAttribute("users",clients);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long client_id = Long.parseLong(req.getParameter("client"));
        long vehicle_id = Long.parseLong(req.getParameter("car"));
        LocalDate debut = LocalDate.parse(req.getParameter("begin"));
        LocalDate fin = LocalDate.parse(req.getParameter("end"));
        System.out.println("client id : "+client_id);
        System.out.println("vehicle id : "+vehicle_id);
        System.out.println("debut : "+debut);
        System.out.println("fin : "+fin);
        try {
            getReservationService().create(new Reservation(client_id,vehicle_id,debut,fin));
            System.out.println("Reservation créée");
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/rents");
    }
}
