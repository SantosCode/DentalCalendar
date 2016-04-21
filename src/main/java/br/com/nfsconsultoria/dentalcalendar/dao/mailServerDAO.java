/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.dao;

import br.com.nfsconsultoria.dentalcalendar.domain.MailServer;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author luissantos
 */
public class mailServerDAO extends GenericDAO<MailServer>{
    
    public MailServer buscar(String nome) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(MailServer.class);
            consulta.add(Restrictions.eq("nome", nome));
            MailServer resultado = (MailServer) consulta.uniqueResult();
            return resultado;
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }
}
