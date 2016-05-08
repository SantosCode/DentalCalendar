package br.com.nfsconsultoria.dentalcalendar.service;

import br.com.nfsconsultoria.dentalcalendar.dao.ClinicaDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Clinica;
import com.google.gson.Gson;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by luis on 04/05/16.
 */

@Path("Clinica")
public class ClinicaService {

    @GET
    public String listar() {
        ClinicaDAO clinicaDAO = new ClinicaDAO();
        List<Clinica> clinicas = clinicaDAO.listar();

        Gson gson = new Gson();
        String json = gson.toJson(clinicas);

        return json;
    }

    @POST
    public String salvar(String json) {
        Gson gson = new Gson();
        Clinica clinica = gson.fromJson(json, Clinica.class);

        ClinicaDAO clinicaDAO = new ClinicaDAO();
        clinicaDAO.merge(clinica);

        String jsonSaida = gson.toJson(clinica);
        return jsonSaida;
    }

    @PUT
    public String editar(String jon) {
        Gson gson = new Gson();
        Clinica clinica = gson.fromJson(jon, Clinica.class);

        ClinicaDAO clinicaDAO = new ClinicaDAO();
        clinicaDAO.editar(clinica);

        String jsonSaida = gson.toJson(clinica);
        return jsonSaida;
    }

    @DELETE
    @Path("{codigo}")
    public String excluir(@PathParam("codigo") Long codigo) {
        ClinicaDAO clinicaDAO = new ClinicaDAO();

        Clinica clinica = clinicaDAO.buscar(codigo);
        clinicaDAO.excluir(clinica);

        Gson gson = new Gson();
        String saida = gson.toJson(clinica);
        return saida;
    }
}
