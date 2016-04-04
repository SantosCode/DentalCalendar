/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

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

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import br.com.nfsconsultoria.dentalcalendar.dao.DentistaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.EspecialDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.RadiologiaDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import br.com.nfsconsultoria.dentalcalendar.domain.Especial;
import br.com.nfsconsultoria.dentalcalendar.domain.Radiologia;

/**
 *
 * @author luis
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DentistaBean implements Serializable {

    private Dentista dentista;
    private List<Dentista> dentistas;
    private List<Radiologia> radiologias;
    private List<Especial> especiais;

    public DentistaBean() {
        DentistaDAO dentDAO = new DentistaDAO();

        this.dentistas = dentDAO.listar();
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public List<Dentista> getDentistas() {
        return dentistas;
    }

    public void setDentistas(List<Dentista> dentistas) {
        this.dentistas = dentistas;
    }

    public List<Radiologia> getRadiologias() {
        return radiologias;
    }

    public void setRadiologias(List<Radiologia> radiologias) {
        this.radiologias = radiologias;
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
            DentistaDAO dentistaDAO = new DentistaDAO();
            dentistaDAO.listar();

            EspecialDAO especialDAO = new EspecialDAO();
            especialDAO.listar();

            RadiologiaDAO radiologiaDAO = new RadiologiaDAO();
            radiologiaDAO.listar();

        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu um erro ao tentar listar os dentistas");
            erro.printStackTrace();
        }

    }

    public void novo() {

        RadiologiaDAO radioDAO = new RadiologiaDAO();
        EspecialDAO espDAO = new EspecialDAO();
        this.radiologias = radioDAO.listar();
        this.especiais = espDAO.listar();
        dentista = new Dentista();

        if (this.radiologias.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar radiologia antes");
        } else if (this.especiais.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar especialidades antes");
        }
    }

    public void salvar() {

        try {
            DentistaDAO dentistaDAO = new DentistaDAO();
            dentistaDAO.merge(dentista);

            dentista = new Dentista();
            dentistaDAO.listar();
            Messages.addGlobalInfo("Dentista salvo com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo Dentista");
            erro.printStackTrace();
        }
    }

    public void excluir(ActionEvent evento) {
        try {
            dentista = (Dentista) evento.getComponent().getAttributes().get("dentistaSelecionado");

            DentistaDAO dentistaDAO = new DentistaDAO();
            dentistaDAO.excluir(dentista);

            dentistas = dentistaDAO.listar();

            Messages.addGlobalInfo("Dentista removido com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o dentista");
            erro.printStackTrace();
        }
    }

    public void editar(ActionEvent evento) {
        try {
            DentistaDAO dentistaDAO = new DentistaDAO();
            dentistaDAO.listarOrdenado();
            dentista = (Dentista) evento.getComponent().getAttributes().get("dentistaSelecionado");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um dentista");
            erro.printStackTrace();
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        pdf.addAuthor("Luis Carlos Santos");
        pdf.addTitle("Dentistas Cadastrados");
        pdf.addCreator("NFS Consultoria");
        pdf.addSubject("Dentistas Cadastrados");

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
