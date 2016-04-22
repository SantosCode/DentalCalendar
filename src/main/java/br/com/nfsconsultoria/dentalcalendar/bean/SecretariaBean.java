/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.DatasDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.DentistaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.SecretariaDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Datas;
import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import br.com.nfsconsultoria.dentalcalendar.domain.Secretaria;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
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
public class SecretariaBean implements Serializable {

	private Secretaria sec;
	private Datas data;
	private List<Secretaria> secs;
	private List<Datas> datas;
	private List<Dentista> dentistas;

	public SecretariaBean() {

		SecretariaDAO secDAO = new SecretariaDAO();
		DatasDAO dataDAO = new DatasDAO();
		DentistaDAO dentiDAO = new DentistaDAO();

		this.secs = secDAO.listarLazy();
		this.datas = dataDAO.listar();
		this.dentistas = dentiDAO.listarLazy();
	}

	public Secretaria getSec() {
		return sec;
	}

	public void setSec(Secretaria sec) {
		this.sec = sec;
	}

	public List<Secretaria> getSecs() {
		return secs;
	}

	public void setSecs(List<Secretaria> secs) {
		this.secs = secs;
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

	public List<Dentista> getDentistas() {
		return dentistas;
	}

	public void setDentistas(List<Dentista> dentistas) {
		this.dentistas = dentistas;
	}

	public List<Integer> getDiasMes() {
		Integer[] dias = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
				23, 24, 25, 26, 27, 28, 29, 30, 31 };
		return Arrays.asList(dias);

	}

	public List<String> getMesAno() {
		String[] meses = new String[] { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto",
				"Setembro", "Outubro", "Novembro", "Dezembro" };
		return Arrays.asList(meses);

	}

	@PostConstruct
	public void listar() {

		try {
			SecretariaDAO secDAO = new SecretariaDAO();
			DatasDAO dataDAO = new DatasDAO();
			dataDAO.listar();
			secDAO.listarLazy();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() + " ao tentar listar secretárias");
			erro.printStackTrace();
		}

	}

	public void novo() {
		sec = new Secretaria();
		data = new Datas();
	}

	public void salvar() {

		if (this.getSec().getDiaNasc() != null && this.getSec().getMesNasc() != null) {
			try {
				DatasDAO datasDAO = new DatasDAO();
				data.setEvento("Aniverssário");
				data.setNome("Sec. " + this.getSec().getNome());
				data.setDia(this.getSec().getDiaNasc());
				data.setMes(this.getSec().getMesNasc());
				datasDAO.merge(data);
				datas = datasDAO.listar();
				data = new Datas();
				Messages.addGlobalInfo("Aniverssário de Secretária(o) salvo com sucesso");
			} catch (RuntimeException erro) {

				Messages.addFlashGlobalWarn("Aniverssário de Secretária(o) não foi salvo");
			}
		}
		try {
			SecretariaDAO secDAO = new SecretariaDAO();
			secDAO.merge(sec);
			secs = secDAO.listarLazy();
			sec = new Secretaria();
			Messages.addGlobalInfo("Secretária(o) salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() + " ao tentar salvar a secretária(o)");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			sec = (Secretaria) evento.getComponent().getAttributes().get("secSelecionada");
			SecretariaDAO secDAO = new SecretariaDAO();
			secDAO.excluir(sec);
			secs = secDAO.listarLazy();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() + " ao tentar excluir secretária");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			SecretariaDAO secDAO = new SecretariaDAO();
			secDAO.listar();
			sec = (Secretaria) evento.getComponent().getAttributes().get("secSelecionada");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() + " ao editar secretária");
			erro.printStackTrace();
		}
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		pdf.addAuthor("Luis Carlos Santos");
		pdf.addTitle("Secretarias Cadastradas");
		pdf.addCreator("NFS Consultoria");
		pdf.addSubject("Secretarias Cadastradas");

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
