package br.com.nfsconsultoria.dentalcalendar.util;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by luis on 05/05/16.
 */
@ApplicationPath("rest")
public class DentalResConfig extends ResourceConfig {
    public DentalResConfig() {
        packages("br.com.nfsconsultoria.dentalcalendar.service");
    }
}
