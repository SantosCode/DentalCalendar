/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.nfsconsultoria.dentalcalendar.domain.Datas;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author luis
 */
public class DatasDAO extends GenericDAO<Datas> {

    public List<Datas> listar(String mes) {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            Criteria consulta = sessao.createCriteria(Datas.class);
            consulta.add(Restrictions.eq("mes", mes));
            List<Datas> resultado = consulta.list();
            return resultado;
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }
}
