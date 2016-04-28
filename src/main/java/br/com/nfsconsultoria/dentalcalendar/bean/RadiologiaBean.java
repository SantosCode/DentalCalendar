/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.RadiologiaDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Radiologia;
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
 *
 * @author Luis Santos
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RadiologiaBean implements Serializable {

	private Radiologia radiologia;
	private List<Radiologia> radiologias;

	public RadiologiaBean() {
		RadiologiaDAO radioDAO = new RadiologiaDAO();
		this.radiologias = radioDAO.listar();
	}

	public Radiologia getRadiologia() {
		return radiologia;
	}

	public void setRadiologia(Radiologia radiologia) {
		this.radiologia = radiologia;
	}

	public List<Radiologia> getRadiologias() {

		return radiologias;
	}

	public void setRadiologias(List<Radiologia> radiologias) {
		this.radiologias = radiologias;
	}

	public void novo() {
		radiologia = new Radiologia();
	}

	public void salvar() {

		try {
			RadiologiaDAO radiologiasDAO = new RadiologiaDAO();
			radiologiasDAO.merge(radiologia);

			radiologias = radiologiasDAO.listar();
			radiologia = new Radiologia();
			Messages.addGlobalInfo("Radiologia salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu o erro " + erro.getLocalizedMessage() + " ao tentar salvar a radiologia");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			radiologia = (Radiologia) evento.getComponent().getAttributes().get("radiologiaSelecionada");

			RadiologiaDAO radiologiaDAO = new RadiologiaDAO();
			radiologiaDAO.excluir(radiologia);

			radiologias = radiologiaDAO.listar();

			Messages.addGlobalInfo("Radiologia removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError(
					"Ocorreu o erro " + erro.getMessage() + " ao tentar remover a radiologia" + erro.getMessage());
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			radiologia = (Radiologia) evento.getComponent().getAttributes().get("radiologiaSelecionada");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError(
					"Ocorreu o erro " + erro.getMessage() + " ao tentar selecionar uma radiologia");
			erro.printStackTrace();
		}
	}

	@PostConstruct
	public void listar() {

		try {
			RadiologiaDAO radiologiaDAO = new RadiologiaDAO();
			radiologiaDAO.listar();
			List<Radiologia> resultado = radiologiaDAO.listar();
			for (Radiologia radiologia : resultado) {
				Messages.addGlobalInfo("Nome: " + radiologia.getNome());
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() + " ao tentar listar as radiologias");
			erro.printStackTrace();
		}

	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.setPageSize(PageSize.A4);
		pdf.addAuthor("Luis Carlos Santos");
		pdf.addTitle("Radiologias Cadastradas");
		pdf.addCreator("NFS Consultoria");
		pdf.addSubject("Radiologias Cadastradas");
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
