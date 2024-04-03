package com.epf.rentmanager.servlet;

import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    public VehicleService getVehicleService() {
        return vehicleService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Vehicule vehicule = this.getVehicleService().findById(Long.parseLong(req.getQueryString().substring(3)));
            req.setAttribute("marque",vehicule.getConstructeur());
            req.setAttribute("model",vehicule.getModele());
            req.setAttribute("nb_place",vehicule.getNb_places());
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String constructeur = req.getParameter("brand");
        String modele = req.getParameter("modele");
        short nb_places = Short.parseShort(req.getParameter("nbPlaces"));
        try {
            long returnId = this.getVehicleService().update(new Vehicule(Long.parseLong(req.getQueryString().substring(3)), constructeur, modele, nb_places));
            if (returnId > 0)
                resp.sendRedirect(req.getContextPath() + "/cars");
            else
                resp.sendRedirect(req.getContextPath() + "/cars/update?id=" + req.getQueryString().substring(3));
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}