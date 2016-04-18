/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.dao;

import br.com.nfsconsultoria.dentalcalendar.domain.GrupoDentista;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;

/**
 *
 * @author luis
 */
public class GrupoDentistaDAO extends GenericDAO<GrupoDentista> {

    @SuppressWarnings("unchecked")
    public List<GrupoDentista> listarLazy() {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            @SuppressWarnings("deprecation")
			Criteria consulta = sessao.createCriteria(GrupoDentista.class)
                    .setFetchMode("dentistas", FetchMode.LAZY);
            List<GrupoDentista> resultado = consulta.list();
            return resultado;
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }
}
