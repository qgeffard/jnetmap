package org.sio.jnetmap.domain;

import java.util.List;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class NetSwitch {

	@NotNull
	@Size(min = 2, max = 30)
	private String ipNetSwitch;

	@NotNull
	@Size(min = 2, max = 30)
	private String nameNetSwitch;

	@ManyToOne
	private Dispatcher dispatcherNetSwitch;
	
	@SuppressWarnings("unchecked")
	public static List<NetSwitch> findAllNetSwitchesOrder() {
        return entityManager().createNativeQuery("SELECT s.* FROM Dispatcher d, Net_Switch s WHERE s.dispatcher_net_switch=d.id ORDER BY d.name_dispatcher, s.name_net_switch", NetSwitch.class).getResultList();
    }
	
	@SuppressWarnings("unchecked")
	public static List<NetSwitch> findNetSwitchesOfBuilding(Long id) {
		return entityManager().createNativeQuery(
					"SELECT distinct s.* FROM Net_Switch s, Dispatcher d, Building b WHERE s.id=0 or (s.dispatcher_net_switch=d.id and d.building_dispatcher=b.id and b.id="
							+ id + ")", NetSwitch.class).getResultList();
	}
	
	public static List<NetSwitch> findNetSwitchesOfDispatcher(Long id) {
		return entityManager().createQuery(
				"SELECT o FROM NetSwitch o WHERE o.dispatcherNetSwitch=" + id
						+ " or o.id=0", NetSwitch.class).getResultList();
	}
	
	public static NetSwitch findNetSwitchOfBand(Long id) {
		return (NetSwitch) entityManager().createNativeQuery(
				"SELECT distinct s.* FROM Net_Switch s, Band b, Dispatcher d WHERE s.dispatcher_net_switch=d.id and b.dispatcher_band=d.id and b.id=" + id, NetSwitch.class).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public static List<NetSwitch> findNetSwitchesOfBand(Long id) {
		return entityManager().createNativeQuery(
				"SELECT s.* FROM Net_Switch s, Band b, Dispatcher d WHERE s.dispatcher_net_switch=d.id and b.dispatcher_band=d.id and b.id=" + id, NetSwitch.class).getResultList();
	}

}
