package org.sio.jnetmap.domain;

import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Room {

	@NotNull
	@Size(min = 2, max = 30)
	private String nameRoom;

	@ManyToOne
	private Building buildingRoom;

	public static List<Room> findRoomsOfBuilding(Long id) {
		return entityManager().createQuery(
				"SELECT o FROM Room o WHERE o.buildingRoom.id=" + id
						+ " or o.id=0", Room.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Room> findAllRoomsOrder() {
		return entityManager()
				.createNativeQuery(
						"SELECT r.* FROM Building b, Room r WHERE r.building_room=b.id ORDER BY b.name_building, r.name_room",
						Room.class).getResultList();
	}

	public static Room findRoomOfOutlet(Long id) {
		return (Room) entityManager().createNativeQuery(
				"SELECT r.* FROM Room r, Outlet o WHERE o.room_outlet=r.id and o.id="
						+ id, Room.class).getSingleResult();
	}
	
	public static Room maxId() {
		return entityManager().createQuery(
				"SELECT o FROM Room o WHERE o.id=(SELECT MAX(r.id) FROM Room r)", Room.class).getSingleResult();
	}
	
	@Transactional
	public void insert() {
		Query query = entityManager
				.createNativeQuery("INSERT INTO Room(id, name_room, building_room)"
						+ " VALUES(?,?,?)");
		query.setParameter(1, this.getId());
		query.setParameter(2, this.getNameRoom());
		query.setParameter(3, this.getBuildingRoom());
		query.executeUpdate();
	}

}
