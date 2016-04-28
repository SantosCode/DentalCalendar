/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.DatasDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Datas;
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
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author luis
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DatasBean implements Serializable {

    private Datas data;
    private List<Datas> datas;

    public DatasBean() {

        DatasDAO dataDAO = new DatasDAO();
        this.datas = dataDAO.listar();
    }

    public Datas getData() {
        return data;
    }

    public void setData(Datas data) {
        this.data = data;
    }

    public List<Datas> getDatas() {
        return datas;
    }

    public void setDatas(List<Datas> datas) {
        this.datas = datas;
    }

    public List<Integer> getDiasMes() {
        Integer[] dias = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
            23, 24, 25, 26, 27, 28, 29, 30, 31};
        return Arrays.asList(dias);

    }

    public List<String> getMesAno() {
        String[] meses = new String[]{"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto",
            "Setembro", "Outubro", "Novembro", "Dezembro"};
        return Arrays.asList(meses);

    }

    @PostConstruct
    public void listar() {

        try {
            DatasDAO datasDAO = new DatasDAO();
            datasDAO.listar();
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu um erro ao tentar listar as datas");
        }

    }

    public void novo() {

        data = new Datas();

    }

    public void salvar() {

        try {
            DatasDAO datasDAO = new DatasDAO();
            datasDAO.merge(data);

            data = new Datas();
            datas = datasDAO.listar();
            Messages.addGlobalInfo("Data salva com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar uma nova Data");
        }
    }

    public void excluir(ActionEvent evento) {
        try {
            data = (Datas) evento.getComponent().getAttributes().get("dataSelecionada");

            DatasDAO dataDAO = new DatasDAO();
            dataDAO.excluir(data);

            datas = dataDAO.listar();
            Messages.addGlobalInfo("Data removida com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a data");
        }
    }

    public void editar(ActionEvent evento) {
        try {
            DatasDAO dataDAO = new DatasDAO();
            dataDAO.listar();
            data = (Datas) evento.getComponent().getAttributes().get("dataSelecionada");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma data");
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.A4);
        pdf.addAuthor("Luis Carlos Santos");
        pdf.addTitle("Datas Cadastradas");
        pdf.addCreator("NFS Consultoria");
        pdf.addSubject("Datas Cadastradas");
        pdf.open();

        Font catFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);

        Paragraph p = new Paragraph("Relatório de Dentistas", catFont);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(20);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "images"
                + File.separator + "banner.png";

        pdf.add(Image.getInstance(logo));
        pdf.add(p);
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
