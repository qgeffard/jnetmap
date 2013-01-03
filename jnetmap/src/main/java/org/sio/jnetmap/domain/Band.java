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
public class Band {

	@NotNull
	@Size(min = 1, max = 30)
	private String numBand;

	@ManyToOne
	private Dispatcher dispatcherBand;
	
	@SuppressWarnings("unchecked")
	public static List<Band> findAllBandsOrder() {
        return entityManager().createNativeQuery("SELECT b.* FROM Dispatcher d, Band b WHERE b.dispatcher_band=d.id and b.id!=0 ORDER BY d.name_dispatcher, b.num_band", Band.class).getResultList();
    }
	
	@SuppressWarnings("unchecked")
	public static List<Band> findBandsOfBuilding(Long id) {
		return entityManager()
				.createNativeQuery(
						"SELECT distinct ba.* FROM Band ba, Dispatcher d, Building b WHERE ba.dispatcher_band=d.id and d.building_dispatcher=b.id and b.id="
								+ id, Band.class).getResultList();
	}

	public static List<Band> findBandsOfDispatcher(Long id) {
		return entityManager().createQuery(
				"SELECT o FROM Band o WHERE o.dispatcherBand.id=" + id,
				Band.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Band> findBandsOfNetSwitch(Long id) {
		return entityManager()
				.createNativeQuery(
						"SELECT distinct ba.* FROM Band ba, Dispatcher d, Net_Switch s WHERE ba.dispatcher_band=d.id and s.dispatcher_net_switch=d.id and s.id="
								+ id, Band.class).getResultList();
	}
	
	public static Band findBandOfOutlet(Long id) {
        return (Band) entityManager().createNativeQuery("SELECT b.* FROM Band b, Outlet o WHERE o.band_outlet=b.id and o.id="+id, Band.class).getSingleResult();
    }
	
	public static Band maxId() {
		return entityManager().createQuery(
				"SELECT o FROM Band o WHERE o.id=(SELECT MAX(b.id) FROM Band b)", Band.class).getSingleResult();
	}
	
	@Transactional
	public void insert() {
		Query query = entityManager
				.createNativeQuery("INSERT INTO Band(id, num_band, dispatcher_band)"
						+ " VALUES(?,?,?)");
		query.setParameter(1, this.getId());
		query.setParameter(2, this.getNumBand());
		query.setParameter(3, this.getDispatcherBand());
		query.executeUpdate();
	}
	
}
