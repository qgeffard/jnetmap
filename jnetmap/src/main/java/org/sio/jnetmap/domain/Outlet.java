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
public class Outlet {

	@NotNull
	@Size(min = 1, max = 30)
	private String numOutlet;
	
	private int portOutlet;

	@ManyToOne
	private Room roomOutlet;

	@ManyToOne
	private Band bandOutlet;

	@SuppressWarnings("unchecked")
	public static List<Outlet> findAllOutletsOrder() {
		List<Outlet> l = entityManager()
				.createNativeQuery(
						"SELECT o.*, r.name_room FROM Outlet o, Room r, Band b WHERE o.id!=0 and o.room_outlet=r.id and o.band_outlet=b.id and o.room_Outlet != 0 ORDER BY r.name_room, o.num_outlet",
						Outlet.class).getResultList();
		l.addAll(entityManager()
				.createNativeQuery(
						"SELECT o.*, d.name_dispatcher FROM Outlet o, Room r, Band b, Dispatcher d WHERE o.id!=0 and o.room_outlet=r.id and o.band_outlet=b.id and b.dispatcher_band=d.id and o.room_Outlet=0 ORDER BY d.name_dispatcher, b.num_band, o.num_outlet",
						Outlet.class).getResultList());
		return l;
	}

	@SuppressWarnings("unchecked")
	public static List<Outlet> findOutletsOfBuilding(Long id) {
		List<Outlet> l = entityManager()
				.createNativeQuery(
						"SELECT o.* FROM Outlet o, Room r, Building b WHERE o.room_outlet=r.id and r.building_room=b.id and b.id="
								+ id + " ORDER BY r.name_room, o.num_outlet",
						Outlet.class).getResultList();
		l.addAll(entityManager()
				.createNativeQuery(
						"SELECT o.*, d.name_dispatcher FROM Outlet o, Room r, Band ba, Dispatcher d, Building b WHERE b.id=d.building_dispatcher and o.id!=0 and o.room_outlet=r.id and o.band_outlet=ba.id and ba.dispatcher_band=d.id and o.room_Outlet=0 and b.id="+id+"ORDER BY d.name_dispatcher, ba.num_band, o.num_outlet",
						Outlet.class).getResultList());
		return l;
	}

	@SuppressWarnings("unchecked")
	public static List<Outlet> findOutletsOfRoomAndBand(Long idRoom, Long idBand) {
		return entityManager().createNativeQuery(
				"SELECT o.* FROM Outlet o, Room r WHERE o.room_outlet=r.id and  o.room_outlet="
						+ idRoom + " and o.band_outlet=" + idBand
						+ " ORDER BY r.name_room, o.num_outlet", Outlet.class)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Outlet> findOutletsOfRoom(Long idRoom) {
		return entityManager().createNativeQuery(
				"SELECT o.* FROM Outlet o, Room r WHERE o.room_outlet=r.id and o.room_outlet="
						+ idRoom + " ORDER BY r.name_room, o.num_outlet",
				Outlet.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	public static List<Outlet> findOutletsOfBand(Long idBand) {
		return entityManager().createNativeQuery(
				"SELECT o.* FROM Outlet o, Room r WHERE o.room_outlet=r.id and o.band_outlet="
						+ idBand + " ORDER BY r.name_room, o.num_outlet",
				Outlet.class).getResultList();
	}
	
	public static Outlet maxId() {
		return entityManager().createQuery(
				"SELECT o FROM Outlet o WHERE o.id=(SELECT MAX(o.id) FROM Outlet o)", Outlet.class).getSingleResult();
	}
	
	@Transactional
	public void insert() {
		Query query = entityManager
				.createNativeQuery("INSERT INTO Outlet(id, num_outlet, room_outlet, band_outlet, port_outlet)"
						+ " VALUES(?,?,?,?,?)");
		query.setParameter(1, this.getId());
		query.setParameter(2, this.getNumOutlet());
		query.setParameter(3, this.getRoomOutlet());
		query.setParameter(4, this.getBandOutlet());
		query.setParameter(5, 0);
		query.executeUpdate();
	}
	
}
