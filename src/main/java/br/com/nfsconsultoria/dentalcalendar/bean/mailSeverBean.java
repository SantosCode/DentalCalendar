/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.SecretariaDAO;
import br.com.nfsconsultoria.dentalcalendar.dao.mailServerDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Secretaria;
import br.com.nfsconsultoria.dentalcalendar.domain.mailServer;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;

/**
 *
 * @author luissantos
 */
@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class mailSeverBean {

    private mailServer mailServer;
    private List<mailServer> mailServers;

    public void mailServerBean() {
        mailServerDAO mailDAO = new mailServerDAO();
        this.mailServers = mailDAO.listar();
    }

    public mailServer getMailServer() {
        return mailServer;
    }

    public void setMailServer(mailServer mailServer) {
        this.mailServer = mailServer;
    }

    public List<mailServer> getMailServers() {
        return mailServers;
    }

    public void setMailServers(List<mailServer> mailServers) {
        this.mailServers = mailServers;
    }

    @PostConstruct
    public void listar() {
        try {
            mailServerDAO mailDAO = new mailServerDAO();
            mailServers = mailDAO.listar();
        } catch(RuntimeException erro)  {
            Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() 
                    + " ao tentar listar mail server");
            erro.printStackTrace();
        }
    }
    
    public void novo(){
        mailServer = new mailServer();
    }

    public void salvar() {
        try {
            mailServerDAO mailDAO = new mailServerDAO();
            mailDAO.merge(mailServer);
            mailServers = mailDAO.listar();
            mailServer = new mailServer();
            Messages.addGlobalInfo("Mail server salvo com sucesso");
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() 
                    + " ao salvar mail server");
            erro.printStackTrace();
        }
    }
    
    public void excluir(ActionEvent evento){
        try {
            mailServer = (mailServer) evento.getComponent().getAttributes()
                    .get("mailSelecionado");
            mailServerDAO mailDAO = new mailServerDAO();
            mailDAO.excluir(mailServer);
            mailServer = new mailServer();
            mailServer.setEmail("seu_email");
            mailServer.setSenha("sua_senha");
            mailServer.setPorta(25);
            mailServer.setServidor("smtp.dominio.com.br");
            mailServer.setTtls(Boolean.TRUE);
            mailDAO.merge(mailServer);
            mailServers = mailDAO.listar();
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() 
                    + " ao tentar excluir SMTP");
            erro.printStackTrace();
        }
    }
    
    public void editar(ActionEvent evento){
        try {
            mailServerDAO mailDAO = new mailServerDAO();
            mailDAO.listar();
            mailServer = (mailServer) evento.getComponent().getAttributes()
                    .get("mailSelecionado");
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() 
                    + " ao editar mail server");
            erro.printStackTrace();
        }
    }
}
