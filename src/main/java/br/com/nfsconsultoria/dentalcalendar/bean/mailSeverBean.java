/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.nfsconsultoria.dentalcalendar.dao.mailServerDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.MailServer;

/**
 *
 * @author luissantos
 */
@ManagedBean
@ViewScoped
public class mailSeverBean {

    private MailServer mailServer;
    private List<MailServer> mailServers;

    public void mailServerBean() {
        mailServerDAO mailDAO = new mailServerDAO();
        this.mailServers = mailDAO.listar();
    }

    public MailServer getMailServer() {
        return mailServer;
    }

    public void setMailServer(MailServer mailServer) {
        this.mailServer = mailServer;
    }

    public List<MailServer> getMailServers() {
        return mailServers;
    }

    public void setMailServers(List<MailServer> mailServers) {
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
        mailServer = new MailServer();
    }

    public void salvar() {
        try {
            mailServerDAO mailDAO = new mailServerDAO();
            mailServer.setNome("smtp");
            mailDAO.merge(mailServer);
            mailServers = mailDAO.listar();
            mailServer = new MailServer();
            Messages.addGlobalInfo("Mail server salvo com sucesso");
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() 
                    + " ao salvar mail server");
            erro.printStackTrace();
        }
    }
    
    public void excluir(ActionEvent evento){
        try {
            mailServer = (MailServer) evento.getComponent().getAttributes()
                    .get("mailSelecionado");
            mailServerDAO mailDAO = new mailServerDAO();
            mailDAO.excluir(mailServer);
            mailServer = new MailServer();
            mailServer.setEmail("seu_email");
            mailServer.setSenha("sua_senha");
            mailServer.setPorta("25");
            mailServer.setServidor("smtp.dominio.com.br");
            mailServer.setTssl(Boolean.TRUE);
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
            mailServer = (MailServer) evento.getComponent().getAttributes()
                    .get("mailSelecionado");
        } catch (RuntimeException erro) {
            Messages.addGlobalError("Ocorreu o erro " + erro.getMessage() 
                    + " ao editar mail server");
            erro.printStackTrace();
        }
    }
}
