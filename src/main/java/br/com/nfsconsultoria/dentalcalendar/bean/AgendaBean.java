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
import br.com.nfsconsultoria.dentalcalendar.util.EmailUtil;
import br.com.nfsconsultoria.dentalcalendar.util.RecUtil;
import com.lowagie.text.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
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
        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");

        if (login.getRepresentanteLogado().getAdmin().equals("Analista")
                || login.getRepresentanteLogado().getAdmin().equals("Admin")) {
            this.agendas = agendaDAO.listar();
            this.representantes = repreDAO.listar();
        } else if (login.getRepresentanteLogado().getAdmin().equals("Representante")) {
            this.agendas = agendaDAO.listarRep(login.getRepresentanteLogado().getCodigo());
            this.representantes = repreDAO.listarCod(login.getRepresentanteLogado().getCodigo());
        }
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

        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");

        try {
            AgendaDAO agendaDAO = new AgendaDAO();
            RepresentanteDAO repDAO = new RepresentanteDAO();
            if (login.getRepresentanteLogado().getAdmin().equals("Analista")
                    || login.getRepresentanteLogado().getAdmin().equals("Admin")) {
                agendas = agendaDAO.listar();
                representantes = repDAO.listar();
            } else if (login.getRepresentanteLogado().getAdmin().equals("Representante")) {
                agendas = agendaDAO.listarRep(login.getRepresentanteLogado().getCodigo());
                representantes = repDAO.listarCod(login.getRepresentanteLogado().getCodigo());
            }
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu um erro ao tentar listar os agendas");
            erro.printStackTrace();
        }
    }

    public void novo() {

        agenda = new Agenda();

        if (this.representantes.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar representantes antes");
        }
        if (this.dentistas.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar dentistas antes");

        }
    }

    public void salvar() {

        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");

        try {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(agenda.getRepresentante().getNome());
            stringBuilder.append(" você tem uma visita agendada dia ");
            stringBuilder.append(agenda.getDia().getDate());
            stringBuilder.append("/");
            stringBuilder.append(agenda.getDia().getMonth() + 1);
            stringBuilder.append("/");
            stringBuilder.append(agenda.getDia().getYear() + 1900);
            stringBuilder.append(" as ");
            stringBuilder.append(agenda.getHora().getHours());
            stringBuilder.append(" horas e ");
            stringBuilder.append(agenda.getHora().getMinutes());
            stringBuilder.append(" minutos, com o/a Dr(a). ");
            stringBuilder.append(agenda.getDentista().getNome());
            stringBuilder.append(", na Rua ");
            stringBuilder.append(agenda.getDentista().getRua());
            if (!agenda.getDentista().getComplemento().isEmpty()) {
                stringBuilder.append(", complemento ");
                stringBuilder.append(agenda.getDentista().getComplemento());
            }
            stringBuilder.append(", bairro ");
            stringBuilder.append(agenda.getDentista().getBairro());
            stringBuilder.append(", na cidade de ");
            stringBuilder.append(agenda.getDentista().getCidade());
            String msg = stringBuilder.toString();

            EmailUtil mail = new EmailUtil();
            mail.EnviarEmail(null, agenda.getRepresentante().getEmail(), null, "Nova visita Agendada", msg);

            AgendaDAO agendaDAO = new AgendaDAO();
            RepresentanteDAO representanteDAO = new RepresentanteDAO();
            DentistaDAO dentistaDAO = new DentistaDAO();

            agendaDAO.merge(agenda);
            if (login.getRepresentanteLogado().getAdmin().equals("Admin")
                    || login.getRepresentanteLogado().getAdmin().equals("Analista")) {
                agendas = agendaDAO.listar();
                representantes = representanteDAO.listar();
            } else if (login.getRepresentanteLogado().getAdmin().equals("Representante")) {
                agendas = agendaDAO.listarRep(login.getRepresentanteLogado().getCodigo());
                representantes = representanteDAO.listarCod(login.getRepresentanteLogado().getCodigo());
            }
            dentistas = dentistaDAO.listar();
            agenda = new Agenda();
            Messages.addGlobalInfo("Agenda salva com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma nova agenda");
            erro.printStackTrace();
        }
    }


    public void excluir(ActionEvent evento) {
        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");

        try {
            agenda = (Agenda) evento.getComponent().getAttributes().get("agendaSelecionada");

            AgendaDAO agendaDAO = new AgendaDAO();
            agendaDAO.excluir(agenda);

            if (login.getRepresentanteLogado().getAdmin().equals("Admin")
                    || login.getRepresentanteLogado().getAdmin().equals("Analista")) {
                agendas = agendaDAO.listar();
            } else if (login.getRepresentanteLogado().getAdmin().equals("Representante")) {
                agendas = agendaDAO.listarRep(login.getRepresentanteLogado().getCodigo());
            }
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
        pdf.setPageSize(PageSize.A4);
        pdf.addAuthor("Luis Carlos Santos");
        pdf.addTitle("Agendas Cadastradas");
        pdf.addCreator("NFS Consultoria");
        pdf.addSubject("Agendas Cadastradas");
        pdf.open();

        Font catFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);

        Paragraph p = new Paragraph("Relatório de Agendas", catFont);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(20);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images"
                + File.separator + "banner.png";

        pdf.add(Image.getInstance(logo));
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(CellStyle.ALIGN_JUSTIFY);
        cellStyle.setFont(font);


        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }
}
