/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author chris
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(Services.AccountService.class);
        resources.add(Services.AuthtestService.class);
        resources.add(Services.EmailResource.class);
        resources.add(Services.EventService.class);
        resources.add(Services.LoginService.class);
        resources.add(Services.LogoutService.class);
        resources.add(Services.RecoverService.class);
        resources.add(Services.TeilnahmeService.class);
        resources.add(Services.TeilnehmerService.class);
        resources.add(Services.VeranstalterService.class);
        resources.add(Services.VerifyService.class);
    }
    
    
}
