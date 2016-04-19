/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nfsconsultoria.dentalcalendar.dao;

import br.com.nfsconsultoria.dentalcalendar.domain.Secretaria;
import br.com.nfsconsultoria.dentalcalendar.util.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;

/**
 *
 * @author luissantos
 */
public class SecretariaDAO extends GenericDAO<Secretaria> {

    @SuppressWarnings("unchecked")
    public List<Secretaria> listarLazy() {
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        try {
            @SuppressWarnings("deprecation")
            Criteria consulta1 = sessao.createCriteria(Secretaria.class)
                    .setFetchMode("dentista1", FetchMode.LAZY);
            Criteria consulta2 = sessao.createCriteria(Secretaria.class)
                    .setFetchMode("dentista2", FetchMode.LAZY);
            Criteria consulta3 = sessao.createCriteria(Secretaria.class)
                    .setFetchMode("dentista3", FetchMode.LAZY);
            Criteria consulta4 = sessao.createCriteria(Secretaria.class)
                    .setFetchMode("dentista4", FetchMode.LAZY);
            List<Secretaria> resultado1 = consulta1.list();
            List<Secretaria> resultado2 = consulta2.list();
            List<Secretaria> resultado3 = consulta3.list();
            List<Secretaria> resultado4 = consulta4.list();
            if (!resultado1.isEmpty()) {
                return resultado1;
            } else if (!resultado2.isEmpty()) {
                return resultado2;
            } else if (!resultado3.isEmpty()) {
                return resultado3;
            } else 
                return resultado4;     
        } catch (RuntimeException erro) {
            throw erro;
        } finally {
            sessao.close();
        }
    }
}
