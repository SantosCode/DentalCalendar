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
import br.com.nfsconsultoria.dentalcalendar.domain.Agenda;
import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import br.com.nfsconsultoria.dentalcalendar.domain.Representante;
import br.com.nfsconsultoria.dentalcalendar.domain.Visita;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    
    public VisitaBean(){
    	VisitaDAO visitaDAO = new VisitaDAO();
	AgendaDAO agendaDAO = new AgendaDAO();
    	RepresentanteDAO repreDAO = new RepresentanteDAO();
    	DentistaDAO dentDAO = new DentistaDAO();
    	
    	this.visitas = visitaDAO.listar(); 
    	this.agendas = agendaDAO.listar();
    	this.representantes = repreDAO.listar();
    	this.dentistas = dentDAO.listar();
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

    @PostConstruct
    public void listar() {

        try {
            VisitaDAO visitaDAO = new VisitaDAO();
            visitaDAO.listar();
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu um erro ao tentar listar as visitas");
            erro.printStackTrace();
        }

    }

    public void novo() {

        RepresentanteDAO representanteDAO = new RepresentanteDAO();
        DentistaDAO dentistaDAO = new DentistaDAO();
        AgendaDAO agendaDAO = new AgendaDAO();
  
        this.representantes = representanteDAO.listar();
        this.dentistas = dentistaDAO.listar();
        this.agendas = agendaDAO.listar();
        visita = new Visita();
        
        
         if (this.representantes.isEmpty()) {
             Messages.addGlobalError("É nescessario cadastrar representantes antes");
        } else if (this.dentistas.isEmpty()){
            Messages.addGlobalError("É nescessario cadastrar dentistas antes");
        } else if (this.agendas.isEmpty()){
            Messages.addGlobalError("É nescessario cadastrar agenda antes");
        }
    }

    public void salvar() {

        try {
            VisitaDAO visitaDAO = new VisitaDAO();
           
            visitaDAO.merge(visita);

            visita = new Visita();
            visitas = visitaDAO.listar();
            Messages.addGlobalInfo("Visita salva com sucesso");
        } catch (RuntimeException erro) {
             Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma nova visita");
            erro.printStackTrace();
        }
    }
    public void excluir(ActionEvent evento) {
        try {
            visita = (Visita) evento.getComponent().getAttributes().get("visitaSelecionada");

            VisitaDAO visitaDAO = new VisitaDAO();
            visitaDAO.excluir(visita);

            visitas = visitaDAO.listar();

            Messages.addGlobalInfo("Visita removida com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a visita");
            erro.printStackTrace();
        }
    }

    public void editar(ActionEvent evento) {
        try {
            visita = (Visita) evento.getComponent().getAttributes().get("visitaSelecionada");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma visita");
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
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator +  "images" + File.separator + "banner.png";
         
        pdf.add(Image.getInstance(logo));
    }
    
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
         
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);
             
            cell.setCellStyle(cellStyle);
        }
    }
    
    public void mail(){
        String to = visita.getAgenda().getDentista().getEmail();
        String from = visita.getAgenda().getRepresentante().getEmail();
        String user = "luis@nfsconsultoria.com.br";
        String pass = "wildog";
        String host = "mail.nfsconsultoria.com.br";
        String msg = visita.getAcordo();
        Properties propriedades = new Properties();
        propriedades.put("mail.smtp.host","mail.nfsconsultoria.com.br");
//        propriedades.put("mail.smtp.socketFactory.port", "25");
        propriedades.put("mail.smtp.auth", "true");
        propriedades.put("mail.smtp.port", "25");
        
        Session sessao = Session.getDefaultInstance(propriedades);
        new javax.mail.Authenticator() {
            
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user, pass);
            }
};
        
        try {
            MimeMessage mensagem = new MimeMessage(sessao);
            mensagem.setFrom(new InternetAddress(from));
            mensagem.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mensagem.setSubject("Acordo tratado em Visita da Radiodoc");
            mensagem.setContent(msg, "text/html");
            Transport.send(mensagem);
            Messages.addGlobalInfo("E-mail enviado com sucesso para o dentista");
        } catch (MessagingException erro) {
            Messages.addGlobalError("Ocorreu uma falha no envio de e-mail ao dentista");
        }
    }
}
