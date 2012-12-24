package org.sio.jnetmap.domain;

import java.util.List;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class NetModule {

    @NotNull
    private int numNetModule;

    @ManyToOne
    private NetSwitch netSwitchModule;
    
    @SuppressWarnings("unchecked")
	public static List<NetModule> findNetModulesOfBuilding(Long id) {
		return (List<NetModule>) entityManager().createNativeQuery(
					"SELECT distinct m.* FROM Net_Module m, Net_Switch s, Dispatcher d, Building b WHERE m.net_Switch_Module=s.id and s.dispatcher_net_switch=d.id and d.building_dispatcher=b.id and b.id="
							+ id, NetModule.class).getResultList();
	}
    
    public static List<NetModule> findNetModulesOfNetSwitch(Long id) {
        return entityManager().createQuery("SELECT o FROM NetModule o WHERE o.netSwitchModule.id="+id, NetModule.class).getResultList();
    }
    
}