/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.RepresentanteDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Representante;
import br.com.nfsconsultoria.dentalcalendar.util.RecUtil;
import com.lowagie.text.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.commandbutton.CommandButton;

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
 * @author luis
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RepresentanteBean implements Serializable {

    private Representante representante;
    private List<Representante> representantes;
    private String rSenha;

    public RepresentanteBean() {
        RepresentanteDAO repreDAO = new RepresentanteDAO();
        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");
        if (login.getRepresentanteLogado().getAdmin().equals("Admin")
                || login.getRepresentanteLogado().getAdmin().equals("Analista")) {
            this.representantes = repreDAO.listar();
        } else {
            this.representantes = repreDAO.listarCod(login.getRepresentanteLogado().getCodigo());
        }
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public List<Representante> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(List<Representante> representantes) {
        this.representantes = representantes;
    }

    public String getrSenha() {
        return rSenha;
    }

    public void setrSenha(String rSenha) {
        this.rSenha = rSenha;
    }

    public List<String> getNivel() {
        String[] nivel = new String[]{"Admin", "Representante", "Analista"};
        return Arrays.asList(nivel);

    }

    @PostConstruct
    public void listar() {
        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");
        if (login.getRepresentanteLogado().getAdmin().equals("Admin")
                || login.getRepresentanteLogado().getAdmin().equals("Analista")) {
            try {
                RepresentanteDAO representanteDAO = new RepresentanteDAO();
                representanteDAO.listar();
            } catch (RuntimeException erro) {
                Messages.addGlobalError("Ocorreu um erro ao tentar listar os representantes");
                erro.printStackTrace();
            }
        } else {
            try {
                RepresentanteDAO representanteDAO = new RepresentanteDAO();
                representanteDAO.listarCod(login.getRepresentanteLogado().getCodigo());
            } catch (RuntimeException erro) {
                Messages.addGlobalError("Ocorreu um erro ao tentar listar os representantes");
                erro.printStackTrace();
            }
        }

    }

    public void novo() {
        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");

        representante = new Representante();

    }

    public void salvar() {

        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");

        try {
            RepresentanteDAO representanteDAO = new RepresentanteDAO();
            if (!representante.getSenha().equals(rSenha)) {
                Messages.addGlobalError("O campo senha e repetir senha não conferem");
                return;
            }
            SimpleHash hash = new SimpleHash("md5", representante.getSenha());
            representante.setSenha(hash.toHex());

            if (login.getRepresentanteLogado().getAdmin().equals("Representante")) {
                representante.setAdmin(login.getRepresentanteLogado().getAdmin());
            }
            representanteDAO.merge(representante);

            if (login.getRepresentanteLogado().getAdmin().equals("Admin")) {
                representantes = representanteDAO.listar();
                representante = new Representante();
            } else {
                representantes = representanteDAO.listarCod(login.getRepresentanteLogado().getCodigo());
            }
            Messages.addGlobalInfo("Representante salvo com sucesso");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar o representante");
            erro.printStackTrace();
        }
    }

    public void excluir(ActionEvent evento) {
        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");
        if (login.getRepresentanteLogado().getAdmin().equals("Admin")) {
            try {
                representante = (Representante) evento.getComponent().getAttributes().get("representanteSelecionado");

                RepresentanteDAO representanteDAO = new RepresentanteDAO();
                representanteDAO.excluir(representante);

                representantes = representanteDAO.listar();

                Messages.addGlobalInfo("Representante removido com sucesso");
            } catch (RuntimeException erro) {
                Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o representante");
                erro.printStackTrace();
            }
        } else {
            Messages.addGlobalError("Você não possui permissões administrativas");
        }
    }

    public void editar(ActionEvent evento) {

        AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");

        try {
            representante = (Representante) evento.getComponent().getAttributes().get("representanteSelecionado");
        } catch (RuntimeException erro) {
            Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um representante");
            erro.printStackTrace();
        }

    }

    public void exibir() {

        try {

            AutenticaBean login = (AutenticaBean) RecUtil.getObjectSession("autenticaBean");

            if (login.getRepresentanteLogado().getAdmin().equals("Analista")) {

                CommandButton cmdExcluir = (CommandButton) Faces.getViewRoot().findComponent("formListagem:tabela:cmdExcluir");
                cmdExcluir.setDisabled(true);

                CommandButton cmdEditar = (CommandButton) Faces.getViewRoot().findComponent("formListagem:tabela:cmdEditar");
                cmdEditar.setDisabled(true);

                CommandButton cmdNovo = (CommandButton) Faces.getViewRoot().findComponent("formListagem:tabela:cmdNovo");
                cmdNovo.setDisabled(true);

            }

            if (login.getRepresentanteLogado().getAdmin().equals("Representante")) {
                CommandButton cmdNovo = (CommandButton) Faces.getViewRoot().findComponent("formListagem:tabela:cmdNovo");
                cmdNovo.setDisabled(true);
            }

        } catch (RuntimeException erro) {
            erro.printStackTrace();
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        pdf.addAuthor("Luis Carlos Santos");
        pdf.addTitle("Representantes Cadastrados");
        pdf.addCreator("NFS Consultoria");
        pdf.addSubject("Representantes Cadastrados");

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
