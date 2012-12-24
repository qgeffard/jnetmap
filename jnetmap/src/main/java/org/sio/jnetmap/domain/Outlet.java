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
public class Outlet {

	@NotNull
	@Size(min = 2, max = 30)
	private String numOutlet;

	private int portOutlet;

	@ManyToOne
	private Room roomOutlet;

	@ManyToOne
	private Band bandOutlet;
	
	@SuppressWarnings("unchecked")
	public static List<Outlet> findAllOutletsOrder() {
        return entityManager().createNativeQuery("SELECT o.* FROM Outlet o, Room r WHERE o.id!=0 and o.room_outlet=r.id ORDER BY r.name_room, o.num_outlet", Outlet.class).getResultList();
    }
	
	@SuppressWarnings("unchecked")
	public static List<Outlet> findOutletsOfBuilding(Long id) {
		return entityManager().createNativeQuery(
				"SELECT o.* FROM Outlet o, Room r, Building b WHERE o.room_outlet=r.id and r.building_room=b.id and b.id="+id+" ORDER BY r.name_room, o.num_outlet", Outlet.class)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Outlet> findOutletsOfRoomAndBand(Long idRoom, Long idBand) {
		return entityManager().createNativeQuery(
				"SELECT o.* FROM Outlet o, Room r WHERE o.room_outlet=r.id and  o.room_outlet=" + idRoom
						+ " and o.band_outlet=" + idBand+" ORDER BY r.name_room, o.num_outlet", Outlet.class)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Outlet> findOutletsOfRoom(Long idRoom) {
		return entityManager().createNativeQuery(
				"SELECT o.* FROM Outlet o, Room r WHERE o.room_outlet=r.id and o.room_outlet=" + idRoom+" ORDER BY r.name_room, o.num_outlet", Outlet.class)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Outlet> findOutletsOfBand(Long idBand) {
		return entityManager().createNativeQuery(
				"SELECT o.* FROM Outlet o, Room r WHERE o.room_outlet=r.id and o.band_outlet=" + idBand+" ORDER BY r.name_room, o.num_outlet", Outlet.class)
				.getResultList();
	}
}
