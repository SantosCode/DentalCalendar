/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.AgendaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.DatasDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Agenda;
import br.com.nfsconsultoria.dentalcalendar.domain.Datas;
import br.com.nfsconsultoria.dentalcalendar.util.RecUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author luissantos
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class indexBean {

    private List<Datas> datas;
    private List<Agenda> agendas;

    public indexBean() {
        Date hoje = new Date();
        String formato = "MM";
        SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);
        Long compara = Long.parseLong(dataFormatada.format(hoje));

        DatasDAO dataDAO = new DatasDAO();
        AgendaDAO agendaDAO = new AgendaDAO();

        AutenticaBean codigo = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");

        agendas = agendaDAO.listar(codigo.getRepresentanteLogado().getCodigo(), hoje);

        switch (compara.toString()) {

            case "1":
                datas = dataDAO.listar("Janeiro");
                break;
            case "2":
                datas = dataDAO.listar("Fevereiro");
                break;
            case "3":
                datas = dataDAO.listar("Março");
                break;
            case "4":
                datas = dataDAO.listar("Abril");
                break;
            case "5":
                datas = dataDAO.listar("Maio");
                break;
            case "6":
                datas = dataDAO.listar("Junho");
                break;
            case "7":
                datas = dataDAO.listar("Julho");
                break;
            case "8":
                datas = dataDAO.listar("Agosto");
                break;
            case "9":
                datas = dataDAO.listar("Setembro");
                break;
            case "10":
                datas = dataDAO.listar("Outubro");
                break;
            case "11":
                datas = dataDAO.listar("Novembro");
                break;
            case "12":
                datas = dataDAO.listar("Dezembro");
                break;

            default:
                datas = null;
                break;
        }

    }

    public List<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }

    public List<Datas> getDatas() {
        return datas;
    }

    public void setDatas(List<Datas> datas) {
        this.datas = datas;
    }
}
