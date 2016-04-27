package br.com.nfsconsultoria.dentalcalendar.bean;

import br.com.nfsconsultoria.dentalcalendar.dao.DentistaDAO;
import br.com.nfsconsultoria.dentalcalendar.domain.Dentista;
import org.webx.easyreport.factory.ReportFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by luissantos on 26/04/16.
 */
@ManagedBean
@ViewScoped
public class RelatorioBean {

    public void Dentista() {
        ReportFactory<Dentista> rf = new ReportFactory<Dentista>();
        rf.setGrandTotalTitle("Dentistas");
        rf.setTitle("Relatório Dentistas");
        rf.setSubTitle("Dentistas Cadastrados");
        DentistaDAO dentDAO = new DentistaDAO();
        try {
            rf.generateReport(dentDAO.listar(), Dentista.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
