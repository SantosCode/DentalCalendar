/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.nfsconsultoria.dentalcalendar.domain.Datas;
import br.com.nfsconsultoria.dentalcalendar.domain.MailServer;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;

/**
 *
 * @author luis
 */
public class DatasDAO extends GenericDAO<Datas>{
	public Datas buscar(String mes) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(MailServer.class);
            consulta.add(Restrictions.eq("mes", mes));
            Datas resultado = (Datas) consulta.uniqueResult();
            return resultado;
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }
}
