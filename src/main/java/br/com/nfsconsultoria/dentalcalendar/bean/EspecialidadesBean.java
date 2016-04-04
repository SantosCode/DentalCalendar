/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.EspecialDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Especial;
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
public class EspecialidadesBean implements Serializable{
    private Especial especial;
    private List<Especial> especiais;
    
    public EspecialidadesBean(){
    	EspecialDAO espeDAO = new EspecialDAO();
    	this.especiais = espeDAO.listar();
    }

    public Especial getEspecial() {
        return especial;
    }

    public void setEspecial(Especial especial) {
        this.especial = especial;
    }

    public List<Especial> getEspeciais() {
        return especiais;
    }

    public void setEspeciais(List<Especial> especiais) {
        this.especiais = especiais;
    }

    @PostConstruct
    public void listar() {

        try {
            EspecialDAO especiaisDAO = new EspecialDAO();
            especiaisDAO.listar("descricao");
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu um erro ao tentar listar as especialidades");
            erro.printStackTrace();
        }

    }

    public void novo() {
        especial = new Especial();
    }

    public void salvar(){
        
        try {
            EspecialDAO especialDAO= new EspecialDAO();
            especialDAO.merge(especial);
            
            especial = new Especial();
            especialDAO.listar("descricao");
            Messages.addGlobalInfo("Especialidade salva com sucesso");
        } catch (RuntimeException erro) {
      Messages.addGlobalError("Ocorreu um erro ao tentar salvar a especialidade");
            erro.printStackTrace();
        }

    }

      public void excluir(ActionEvent evento) {
        try {
            especial = (Especial) evento.getComponent().getAttributes().get("especialSelecionado");

            EspecialDAO especialDAO = new EspecialDAO();
            especialDAO.excluir(especial);

            especiais = especialDAO.listar();

            Messages.addGlobalInfo("Dentista removido com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a especialidade");
            erro.printStackTrace();
        }
    }
      
       public void editar(ActionEvent evento) {
        try {
            especial = (Especial) evento.getComponent().getAttributes().get("especialSelecionado");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma especialidade");
            erro.printStackTrace();
        }
    }
       
         public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        pdf.addAuthor("Luis Carlos Santos");
        pdf.addTitle("Especialidades Cadastradas");
        pdf.addCreator("NFS Consultoria");
        pdf.addSubject("Especialidades Cadastradas");
 
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
}
