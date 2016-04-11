/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.DatasDAO;
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
import br.com.nfsconsultoria.dentalcalendar.domain.Datas;
import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import br.com.nfsconsultoria.dentalcalendar.domain.Especial;
import br.com.nfsconsultoria.dentalcalendar.domain.Radiologia;
import java.util.Arrays;

/**
 *
 * @author luis
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DentistaBean implements Serializable {

    private Dentista dentista;
    private Datas data;
    private List<Dentista> dentistas;
    private List<Datas> datas;
    private List<Radiologia> radiologias;
    private List<Especial> especiais;

    public DentistaBean() {
        DentistaDAO dentDAO = new DentistaDAO();
        DatasDAO dataDAO = new DatasDAO();
        EspecialDAO especialDAO = new EspecialDAO();
        RadiologiaDAO radiologiaDAO = new RadiologiaDAO();

        this.dentistas = dentDAO.listar();
        this.datas = dataDAO.listar();
        this.especiais = especialDAO.listar();
        this.radiologias = radiologiaDAO.listar();
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
        Integer[] dias = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
            16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
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
            DentistaDAO dentistaDAO = new DentistaDAO();
            dentistaDAO.listar();

            EspecialDAO especialDAO = new EspecialDAO();
            especialDAO.listar();

            RadiologiaDAO radiologiaDAO = new RadiologiaDAO();
            radiologiaDAO.listar();

            DatasDAO dataDAO = new DatasDAO();
            dataDAO.listar();
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu um erro ao tentar listar os dentistas");
        }

    }

    public void novo() {

        dentista = new Dentista();
        data = new Datas();

        if (this.radiologias.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar radiologia antes");
        } else if (this.especiais.isEmpty()) {
            Messages.addGlobalError("É nescessario cadastrar especialidades antes");
        }
    }

    public void salvar() {

        if (this.getDentista().getDiaNasc() != null && !this.getDentista().getMesNasc().isEmpty()) {
            try {
                DatasDAO datasDAO = new DatasDAO();
                data.setEvento("Aniverssário");
                data.setNome("Dr(a). " + this.getDentista().getNome());
                data.setDia(this.getDentista().getDiaNasc());
                data.setMes(this.getDentista().getMesNasc());
                datasDAO.merge(data);
                data = new Datas();
                datas = datasDAO.listar();
                Messages.addGlobalInfo("Aniverssário de Dentista salvo com sucesso");
            } catch (RuntimeException erro) {

                Messages.addFlashGlobalWarn("Aniverssário de Dentista não foi salvo");
            }
        }
        if (!this.getDentista().getNomeSec().isEmpty() && this.getDentista().getDiaNascSec() != null
                && !this.getDentista().getMesNascSec().isEmpty()) {
            try {
                DatasDAO datasDAO = new DatasDAO();
                data.setEvento("Aniverssário");
                data.setNome("Secr(a). " + this.getDentista().getNomeSec());
                data.setDia(this.getDentista().getDiaNascSec());
                data.setMes(this.getDentista().getMesNascSec());
                datasDAO.merge(data);
                data = new Datas();
                datas = datasDAO.listar();
                Messages.addGlobalInfo("Aniverssário de Secretario(a) salvo com sucesso");
            } catch (RuntimeException erro) {

                Messages.addFlashGlobalWarn(" Aniverssário de Secretário(a) não foi salvo");
            }
        }

        try {
            DentistaDAO dentistaDAO = new DentistaDAO();
            dentistaDAO.merge(dentista);

            dentista = new Dentista();
            dentistas = dentistaDAO.listar();
            Messages.addGlobalInfo("Dentista salvo com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar um novo Dentista");
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
        }
    }

    public void editar(ActionEvent evento) {
        try {
            DentistaDAO dentistaDAO = new DentistaDAO();
            dentistaDAO.listar();

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
