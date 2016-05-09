package br.com.nfsconsultoria.dentalcalendar.service;

import br.com.nfsconsultoria.dentalcalendar.dao.DentistaDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import com.google.gson.Gson;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by luissantos on 08/05/16.
 */

@Path("Dentista")
public class DentistaService {

    @GET
    public String listar(){
        DentistaDAO dentistaDAO = new DentistaDAO();
        List<Dentista> dentistas = dentistaDAO.listar();

        Gson gson = new Gson();
        String json = gson.toJson(dentistas);
        return json;
    }

    @POST
    public String salvar(String json) {
        Gson gson = new Gson();
        Dentista dentista = gson.fromJson(json, Dentista.class);

        DentistaDAO dentistaDAO = new DentistaDAO();
        dentistaDAO.merge(dentista);

        String jsonSaida = gson.toJson(dentista);
        return jsonSaida;
    }

    @PUT
    public String editar(String jon) {
        Gson gson = new Gson();
        Dentista dentista = gson.fromJson(jon, Dentista.class);

        DentistaDAO dentistaDAO = new DentistaDAO();
        dentistaDAO.editar(dentista);

        String jsonSaida = gson.toJson(dentista);
        return jsonSaida;
    }

    @DELETE
    @Path("{codigo}")
    public String excluir(@PathParam("codigo") Long codigo) {
        DentistaDAO dentistaDAO = new DentistaDAO();

        Dentista dentista = dentistaDAO.buscar(codigo);
        dentistaDAO.excluir(dentista);

        Gson gson = new Gson();
        String saida = gson.toJson(dentista);
        return saida;
    }
}
