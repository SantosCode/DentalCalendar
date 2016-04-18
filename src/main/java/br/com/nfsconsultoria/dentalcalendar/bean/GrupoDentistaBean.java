/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.DentistaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.GrupoDentistaDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import br.com.nfsconsultoria.dentalcalendar.domain.GrupoDentista;
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
public class GrupoDentistaBean implements Serializable{
   
   private GrupoDentista grupoDentista;
   private List<GrupoDentista> gruposDentista;
   private List<Dentista> dentistas;
   
   public GrupoDentistaBean(){
       GrupoDentistaDAO grupoDAO = new GrupoDentistaDAO();
       DentistaDAO dentistaDAO = new DentistaDAO();
       this.gruposDentista = grupoDAO.listarLazy();
       this.dentistas = dentistaDAO.listarLazy();
   }

    public GrupoDentista getGrupoDentista() {
        return grupoDentista;
    }

    public void setGrupoDentista(GrupoDentista grupoDentista) {
        this.grupoDentista = grupoDentista;
    }

    public List<GrupoDentista> getGruposDentista() {
        return gruposDentista;
    }

    public void setGruposDentista(List<GrupoDentista> gruposDentista) {
        this.gruposDentista = gruposDentista;
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
            GrupoDentistaDAO grupoDAO = new GrupoDentistaDAO();
            grupoDAO.listar();
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu um erro ao tentar listar as clinicas");
            erro.printStackTrace();
        }
    }
    
    public void novo(){
        grupoDentista = new GrupoDentista();
    }
    
    public void salvar(){
        try {
            GrupoDentistaDAO grupoDAO = new GrupoDentistaDAO();
            grupoDAO.merge(grupoDentista);
            gruposDentista = grupoDAO.listarLazy();
            grupoDentista = new GrupoDentista();
            Messages.addGlobalInfo("Clinica salva com sucesso");
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu o erro " +erro.getMessage()+ " ao tentar salvar a clinica");
            erro.printStackTrace();
        }
    }
    
     public void excluir(ActionEvent evento) {
        try {
            
            grupoDentista = (GrupoDentista) evento.getComponent().getAttributes().get("clinicaSelecionada");
 
            GrupoDentistaDAO grupoDAO = new GrupoDentistaDAO();
            grupoDAO.excluir(grupoDentista);
            
          gruposDentista = grupoDAO.listarLazy();
            Messages.addGlobalInfo("Clinica removida com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu o erro " +erro.getMessage()+ " ao tentar remover a clinica");
            erro.printStackTrace();
        }
    }

    public void editar(ActionEvent evento) {
        try {
            GrupoDentistaDAO grupoDAO = new GrupoDentistaDAO();
            grupoDAO.listarLazy();
            grupoDentista = (GrupoDentista) evento.getComponent().getAttributes().get("clinicaSelecionada");
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
