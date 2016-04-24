/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.AgendaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.DentistaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.RepresentanteDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.VisitaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.mailServerDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Agenda;
import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import br.com.nfsconsultoria.dentalcalendar.domain.Representante;
import br.com.nfsconsultoria.dentalcalendar.domain.Visita;
import br.com.nfsconsultoria.dentalcalendar.domain.MailServer;
import br.com.nfsconsultoria.dentalcalendar.util.EmailUtil;
import br.com.nfsconsultoria.dentalcalendar.util.RecUtil;

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
public class VisitaBean implements Serializable {

    private Visita visita;
    private List<Agenda> agendas;
    private List<Representante> representantes;
    private List<Dentista> dentistas;
    private List<Visita> visitas;
    private MailServer mail;
    private List<MailServer> mails;

    public VisitaBean() {
        VisitaDAO visitaDAO = new VisitaDAO();
        AgendaDAO agendaDAO = new AgendaDAO();
        RepresentanteDAO repreDAO = new RepresentanteDAO();
        DentistaDAO dentDAO = new DentistaDAO();
        mailServerDAO mailDAO = new mailServerDAO();

        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");
        Agenda agenCod = agendaDAO.buscarRep(login.getRepresentanteLogado().getCodigo());

        if (login.getRepresentanteLogado().getAdmin()) {
            this.visitas = visitaDAO.listar();
            this.agendas = agendaDAO.listar();
            this.representantes = repreDAO.listar();
            this.dentistas = dentDAO.listar();
            this.mails = mailDAO.listar();
        } else {
            this.visitas = visitaDAO.listarAgen(agenCod.getCodigo());
            this.agendas = agendaDAO.listarRep(login.getRepresentanteLogado().getCodigo());
            this.representantes = repreDAO.listarCod(login.getRepresentanteLogado().getCodigo());
            this.dentistas = dentDAO.listar();
            this.mails = mailDAO.listar();
        }
    }

    public Visita getVisita() {
        return this.visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
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

    public List<Visita> getVisitas() {
        return this.visitas;
    }

    public void setVisitas(List<Visita> visitas) {
        this.visitas = visitas;
    }

    public MailServer getMail() {
        return mail;
    }

    public void setMail(MailServer mail) {
        this.mail = mail;
    }

    public List<MailServer> getMails() {
        return mails;
    }

    public void setMails(List<MailServer> mails) {
        this.mails = mails;
    }

    @PostConstruct
    public void listar() {
        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");
        AgendaDAO agendaDAO = new AgendaDAO();
        Agenda agenCod = agendaDAO.buscarRep(login.getRepresentanteLogado().getCodigo());
        try {
            VisitaDAO visitaDAO = new VisitaDAO();
            if (login.getRepresentanteLogado().getAdmin()) {
                this.visitas = visitaDAO.listar();
            } else {
                this.visitas = visitaDAO.listarAgen(agenCod.getCodigo());
            }
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu um erro ao tentar listar os acordos");
        }

    }

    public void novo() {

        RepresentanteDAO representanteDAO = new RepresentanteDAO();
        DentistaDAO dentistaDAO = new DentistaDAO();
        AgendaDAO agendaDAO = new AgendaDAO();
        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");
        if (login.getRepresentanteLogado().getAdmin()) {
            this.representantes = representanteDAO.listar();
            this.dentistas = dentistaDAO.listar();
            this.agendas = agendaDAO.listar();
        } else {
            this.representantes = representanteDAO.listarCod(login.getRepresentanteLogado().getCodigo());
            this.dentistas = dentistaDAO.listar();
            this.agendas = agendaDAO.listarRep(login.getRepresentanteLogado().getCodigo());
        }
        visita = new Visita();
        if (this.representantes.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar representantes antes");
        }
        if (this.dentistas.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar dentistas antes");
        }
        if (this.agendas.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar agenda antes");
        }
    }

    public void salvar() {
        AgendaDAO agendaDAO = new AgendaDAO();
        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");
        Agenda agenCod = agendaDAO.buscarRep(login.getRepresentanteLogado().getCodigo());

        try {
            if (visita.getEmail()) {
                if (!visita.getAgenda().getDentista().getEmail().isEmpty()) {
                    EmailUtil email = new EmailUtil();
                    email.EnviarEmail(null, visita.getAgenda().getDentista().getEmail(),
                            visita.getAgenda().getRepresentante().getEmail(), "Radiodoc", visita.getAcordo());
                } else {
                    Messages.addGlobalError("Dentista não possui e-mail cadastrado");
                }
            }
            VisitaDAO visitaDAO = new VisitaDAO();
            visitaDAO.merge(visita);
            visita = new Visita();
            if (login.getRepresentanteLogado().getAdmin()) {
                visitas = visitaDAO.listar();
            } else {
                visitas = visitaDAO.listarAgen(agenCod.getCodigo());
            }
            Messages.addGlobalInfo("Visita salva com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma novo acordo");
        }
    }

    public void excluir(ActionEvent evento) {
        AgendaDAO agendaDAO = new AgendaDAO();
        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");
        Agenda agenCod = agendaDAO.buscarRep(login.getRepresentanteLogado().getCodigo());

        try {
            visita = (Visita) evento.getComponent().getAttributes().get("visitaSelecionada");

            VisitaDAO visitaDAO = new VisitaDAO();
            visitaDAO.excluir(visita);
            if (login.getRepresentanteLogado().getAdmin()) {
                visitas = visitaDAO.listar();
            } else {
                visitas = visitaDAO.listarAgen(agenCod.getCodigo());
            }
            Messages.addGlobalInfo("Visita removida com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o acordo");
        }
    }

    public void editar(ActionEvent evento) {
        try {
            visita = (Visita) evento.getComponent().getAttributes().get("visitaSelecionada");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um acordo");
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        pdf.addAuthor("Luis Carlos Santos");
        pdf.addTitle("Acordo Cadastrados");
        pdf.addCreator("NFS Consultoria");
        pdf.addSubject("Acordo Cadastrados");

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images"
                + File.separator + "banner.png";

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
