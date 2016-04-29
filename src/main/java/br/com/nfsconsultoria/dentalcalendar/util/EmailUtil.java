/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.util;

import br.com.nfsconsultoria.dentalcalendar.dao.mailServerDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.MailServer;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.omnifaces.util.Messages;

/**
 *
 * @author luissantos
 */
public class EmailUtil {

    public void EnviarEmail(String de, String para, String resp, String assunto, String mensagem) {
        mailServerDAO smtpDAO = new mailServerDAO();
        MailServer smtp = smtpDAO.buscar("smtp");
        HtmlEmail email = new HtmlEmail();
        email.setSSLOnConnect(smtp.getTssl());
        email.setHostName(smtp.getServidor());
        if (smtp.getTssl()) {
        email.setSslSmtpPort(smtp.getPorta());
        } else {
            email.setSmtpPort(Integer.parseInt(smtp.getPorta()));
        }
        email.setAuthenticator(new DefaultAuthenticator(smtp.getEmail(), smtp.getSenha()));
        email.setCharset("ISO-8859-1");
        try {
            if (de != null ) {
                email.setFrom(de, "NFS Radiologia");
            } else{
                email.setFrom(smtp.getEmail(), "NFS Radiologia");
            }
            if (resp != null){
                email.addReplyTo(resp);
            }
            email.setDebug(true);
            email.addTo(para);
            email.setSubject(assunto);
            email.setHtmlMsg(mensagem);
            email.send();
            Messages.addGlobalInfo("E-mail enviado com sucesso");
        } catch (EmailException erro) {
            Messages.addGlobalError("Erro " + erro.getMessage() + " ao tentar enviar e-mail");
            erro.printStackTrace();
        }
    }

}
