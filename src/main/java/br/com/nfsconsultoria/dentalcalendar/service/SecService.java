package br.com.nfsconsultoria.dentalcalendar.service;

import br.com.nfsconsultoria.dentalcalendar.dao.SecretariaDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Secretaria;
import com.google.gson.Gson;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by luis on 05/05/16.
 */

@Path("Secretaria")
public class SecService {
    @GET
    public String listar() {
        SecretariaDAO secretariaDAO = new SecretariaDAO();
        List<Secretaria> secretarias = secretariaDAO.listar();

        Gson gson = new Gson();
        String json = gson.toJson(secretarias);

        return json;
    }

    @POST
    public String salvar(String json) {
        Gson gson = new Gson();
        Secretaria secretaria = gson.fromJson(json, Secretaria.class);

        SecretariaDAO secretariaDAO = new SecretariaDAO();
        secretariaDAO.merge(secretaria);

        String jsonSaida = gson.toJson(secretaria);
        return jsonSaida;
    }

    @PUT
    public String editar(String jon) {
        Gson gson = new Gson();
        Secretaria secretaria = gson.fromJson(jon, Secretaria.class);

        SecretariaDAO secretariaDAO = new SecretariaDAO();
        secretariaDAO.editar(secretaria);

        String jsonSaida = gson.toJson(secretaria);
        return jsonSaida;
    }

    @DELETE
    @Path("{codigo}")
    public String excluir(@PathParam("codigo") Long codigo) {
        SecretariaDAO secretariaDAO = new SecretariaDAO();

        Secretaria secretaria = secretariaDAO.buscar(codigo);
        secretariaDAO.excluir(secretaria);

        Gson gson = new Gson();
        String saida = gson.toJson(secretaria);
        return saida;
    }
}
