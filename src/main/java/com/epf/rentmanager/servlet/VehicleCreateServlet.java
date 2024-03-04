package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HomeServlet {
    private static final long serialVersionUID = 1L;
    private VehicleService vehicleService;
    public VehicleService getVehicleService() {
        if(vehicleService == null){
            this.vehicleService = VehicleService.getInstance();
        }
        return vehicleService;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
        String marque = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        short nb_places = Short.parseShort(request.getParameter("seats"));
        try {
            getVehicleService().create(new Vehicule(marque,modele,nb_places));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
