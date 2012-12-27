package org.sio.jnetmap.domain;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Building {

	@NotNull
	@Size(min = 1, max = 30)
	private String nameBuilding;
	
	public static List<Building> findAllBuildingsOrder() {
        return entityManager().createQuery("SELECT o FROM Building o ORDER BY o.nameBuilding", Building.class).getResultList();
    }
	
	public static Building findBuildingOfRoom(Long id) {
		return (Building) entityManager().createNativeQuery(
				"SELECT b.* FROM Building b, Room r WHERE r.building_room=b.id and r.id="
						+ id, Building.class).getSingleResult();
	}
	
	public static Building findBuildingOfDispatcher(Long id) {
		return (Building) entityManager().createNativeQuery(
				"SELECT b.* FROM Building b, Dispatcher d WHERE d.building_dispatcher=b.id and d.id="
						+ id, Building.class).getSingleResult();
	}
	
	public static Building findBuildingOfNetSwitch(Long id) {
		return (Building) entityManager().createNativeQuery(
				"SELECT b.* FROM Building b, Dispatcher d, Net_Switch s WHERE d.building_dispatcher=b.id and s.dispatcher_net_switch=d.id and s.id="
						+ id, Building.class).getSingleResult();
	}
	
	public static Building findBuildingOfOutletWhitchBand(Long id) {
		return (Building) entityManager().createNativeQuery(
				"SELECT b.* FROM Building b, Dispatcher d, Outlet o, Band ba WHERE b.id=d.building_dispatcher and o.band_outlet=ba.id and ba.dispatcher_band=d.id and o.id="
						+ id, Building.class).getSingleResult();
	}
	
	public static Building findBuildingOfOutletWhitchRoom(Long id) {
		return (Building) entityManager().createNativeQuery(
				"SELECT b.* FROM Outlet o, Room r, Building b WHERE o.room_outlet=r.id and r.building_room=b.id and o.id="
						+ id, Building.class).getSingleResult();
	}
}
