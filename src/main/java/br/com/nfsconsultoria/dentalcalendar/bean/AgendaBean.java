/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.AgendaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.DentistaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.RepresentanteDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Agenda;
import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import br.com.nfsconsultoria.dentalcalendar.domain.Representante;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.omnifaces.util.Messages;

/**
 *
 * @author luis
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AgendaBean implements Serializable {

    private Agenda agenda;
    private List<Agenda> agendas;
    private List<Representante> representantes;
    private List<Dentista> dentistas;

    public AgendaBean() {
        AgendaDAO agendaDAO = new AgendaDAO();
        RepresentanteDAO repreDAO = new RepresentanteDAO();
        DentistaDAO dentDAO = new DentistaDAO();
        this.agendas = agendaDAO.listar();
        this.representantes = repreDAO.listar();
        this.dentistas = dentDAO.listar();
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public List<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }

    public List<Representante> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(List<Representante> representantes) {
        this.representantes = representantes;
    }

    public List<Dentista> getDentistas() {
        return dentistas;
    }

    public void setDentistas(List<Dentista> dentistas) {
        this.dentistas = dentistas;
    }

    @PostConstruct
    public void listar() {

        try {
            AgendaDAO agendaDAO = new AgendaDAO();
            agendaDAO.listar();
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu um erro ao tentar listar os agendas");
            erro.printStackTrace();
        }

    }

    public void novo() {

        agenda = new Agenda();

        if (this.representantes.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar representantes antes");
        } else if (this.dentistas.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar dentistas antes");
        }
    }

    public void salvar() {

        try {
            AgendaDAO agendaDAO = new AgendaDAO();
            RepresentanteDAO representanteDAO = new RepresentanteDAO();
            DentistaDAO dentistaDAO = new DentistaDAO();

            agendaDAO.merge(agenda);
            agenda = new Agenda();

            agendas = agendaDAO.listar();
            representantes = representanteDAO.listar();
            dentistas = dentistaDAO.listar();
            Messages.addGlobalInfo("Agenda salva com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma nova agenda");
            erro.printStackTrace();
        }
    }

    public void excluir(ActionEvent evento) {
        try {
            agenda = (Agenda) evento.getComponent().getAttributes().get("agendaSelecionada");

            AgendaDAO agendaDAO = new AgendaDAO();
            agendaDAO.excluir(agenda);

            agendas = agendaDAO.listar();

            Messages.addGlobalInfo("Agenda removida com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a agenda");
            erro.printStackTrace();
        }
    }

    public void editar(ActionEvent evento) {
        try {
            agenda = (Agenda) evento.getComponent().getAttributes().get("agendaSelecionada");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma agenda");
            erro.printStackTrace();
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        pdf.addAuthor("Luis Carlos Santos");
        pdf.addTitle("Agendas Cadastradas");
        pdf.addCreator("NFS Consultoria");
        pdf.addSubject("Agendas Cadastradas");

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "banner.png";

        pdf.add(Image.getInstance(logo));
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }
}
