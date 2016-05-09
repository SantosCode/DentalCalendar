package br.com.nfsconsultoria.dentalcalendar.service;

import br.com.nfsconsultoria.dentalcalendar.dao.VisitaDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Visita;
import com.google.gson.Gson;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by luissantos on 08/05/16.
 */

@Path("Visita")
public class VisitaService {
    @GET
    public String listar(){
        VisitaDAO visitaDAO = new VisitaDAO();
        List<Visita> visitas = visitaDAO.listar();

        Gson gson = new Gson();
        String json = gson.toJson(visitas);
        return json;
    }

    @POST
    public String salvar(String json) {
        Gson gson = new Gson();
        Visita visita = gson.fromJson(json, Visita.class);

        VisitaDAO visitaDAO = new VisitaDAO();
        visitaDAO.merge(visita);

        String jsonSaida = gson.toJson(visita);
        return jsonSaida;
    }

    @PUT
    public String editar(String jon) {
        Gson gson = new Gson();
        Visita visita = gson.fromJson(jon, Visita.class);

        VisitaDAO visitaDAO = new VisitaDAO();
        visitaDAO.editar(visita);

        String jsonSaida = gson.toJson(visita);
        return jsonSaida;
    }

    @DELETE
    @Path("{codigo}")
    public String excluir(@PathParam("codigo") Long codigo) {
        VisitaDAO visitaDAO = new VisitaDAO();

        Visita visita = visitaDAO.buscar(codigo);
        visitaDAO.excluir(visita);

        Gson gson = new Gson();
        String saida = gson.toJson(visita);
        return saida;
    }
}
