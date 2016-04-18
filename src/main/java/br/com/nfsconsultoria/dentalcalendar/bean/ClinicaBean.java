/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.ClinicaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.DentistaDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Clinica;
import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
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
 * @author luissantos
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClinicaBean implements Serializable{
   
   private Clinica clinica;
   private List<Clinica> clinicas;
   private List<Dentista> dentistas;
   
   public ClinicaBean(){
       ClinicaDAO clinicaDAO = new ClinicaDAO();
       DentistaDAO dentistaDAO = new DentistaDAO();
       this.clinicas = clinicaDAO.listarLazy();
       this.dentistas = dentistaDAO.listarLazy();
   }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public List<Clinica> getClinicas() {
        return clinicas;
    }

    public void setClinicas(List<Clinica> clinicas) {
        this.clinicas = clinicas;
    }

    public List<Dentista> getDentistas() {
        return dentistas;
    }

    public void setDentistas(List<Dentista> dentistas) {
        this.dentistas = dentistas;
    }
    
    @PostConstruct
    public void listar(){
        try {
            ClinicaDAO clinicaDAO = new ClinicaDAO();
            clinicaDAO.listar();
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu um erro ao tentar listar as clinicas");
            erro.printStackTrace();
        }
    }
    
    public void novo(){
        clinica = new Clinica();
    }
    
    public void salvar(){
        try {
            ClinicaDAO clinicaDAO = new ClinicaDAO();
            clinicaDAO.merge(clinica);
            clinicas = clinicaDAO.listarLazy();
            clinica = new Clinica();
            Messages.addGlobalInfo("Clinica salva com sucesso");
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu o erro " +erro.getMessage()+ " ao tentar salvar a clinica");
            erro.printStackTrace();
        }
    }
    
     public void excluir(ActionEvent evento) {
        try {
            
            clinica = (Clinica) evento.getComponent().getAttributes().get("clinicaSelecionada");
 
            ClinicaDAO clinicaDAO = new ClinicaDAO();
            clinicaDAO.excluir(clinica);
            
            clinicas = clinicaDAO.listarLazy();
            Messages.addGlobalInfo("Clinica removida com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu o erro " +erro.getMessage()+ " ao tentar remover a clinica");
            erro.printStackTrace();
        }
    }

    public void editar(ActionEvent evento) {
        try {
            ClinicaDAO clinicaDAO = new ClinicaDAO();
            clinicaDAO.listarLazy();
            clinica = (Clinica) evento.getComponent().getAttributes().get("clinicaSelecionada");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu o erro " +erro.getMessage()+ " ao tentar selecionar a clinica");
            erro.printStackTrace();
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        pdf.addAuthor("Luis Carlos Santos");
        pdf.addTitle("Clinicas Cadastradas");
        pdf.addCreator("NFS Consultoria");
        pdf.addSubject("Clinicas Cadastradas");

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
