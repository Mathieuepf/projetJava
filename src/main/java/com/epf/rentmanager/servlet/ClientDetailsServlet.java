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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public ClientService getClientService() {
        return clientService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public VehicleService getVehicleService() {
        return vehicleService;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Client client = this.getClientService().findById(Long.parseLong(req.getQueryString().substring(3)));
            req.setAttribute("client", client);
            try {
                List<Reservation> reservations = this.getReservationService().findResaByClientId(client.getId());
                req.setAttribute("reservations", reservations);
                List<Vehicule> vehicules = new ArrayList<Vehicule>();
                for (Reservation reservation : reservations) {
                    if (!vehicules.contains(reservation.getVehicule())) {
                        vehicules.add(reservation.getVehicule());
                    }
                }
                req.setAttribute("vehicules", vehicules);
                req.setAttribute("count_reservations", reservations.size());
                req.setAttribute("count_vehicles", vehicules.size());
            } catch (ServiceException e) {
                req.setAttribute("count_reservations", 0);
                req.setAttribute("count_vehicles", 0);
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(req, resp);
    }
}
