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

@WebServlet("/rents/update")
public class ReservationUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientService clientService;
    @Autowired
    VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    public ReservationService getReservationService() {
        return reservationService;
    }
    public VehicleService getVehicleService() {
        return vehicleService;
    }
    public ClientService getClientService() {
        return clientService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Reservation reservation = this.getReservationService().findById(Long.parseLong(req.getQueryString().substring(3)));
            List<Vehicule> vehicules = this.getVehicleService().findAll();
            vehicules.removeIf(vehicule -> vehicule.getId() == reservation.getVehicule_id());
            List<Client> clients = this.getClientService().findAll();
            clients.removeIf(client -> client.getId() == reservation.getClient_id());

            req.setAttribute("currentUser",reservation.getClient());
            req.setAttribute("currentVehicle",reservation.getVehicule());
            req.setAttribute("vehicles",vehicules);
            req.setAttribute("users",clients);
            req.setAttribute("debut",reservation.getDebut());
            req.setAttribute("fin",reservation.getFin());

        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/update.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long client_id = Long.parseLong(req.getParameter("client"));
        long vehicle_id = Long.parseLong(req.getParameter("car"));
        LocalDate debut = LocalDate.parse(req.getParameter("begin"));
        LocalDate fin = LocalDate.parse(req.getParameter("end"));
        try {
            this.getReservationService().update(new Reservation(Long.parseLong(req.getQueryString().substring(3)),client_id,vehicle_id,debut,fin));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/rents");
    }
}
