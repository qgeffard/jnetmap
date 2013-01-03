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
public class Dispatcher {

    @NotNull
    @Size(min = 2, max = 30)
    private String nameDispatcher;

    @ManyToOne
    private Building buildingDispatcher;
    
    @SuppressWarnings("unchecked")
	public static List<Dispatcher> findAllDispatchersOrder() {
        return entityManager().createNativeQuery("SELECT d.* FROM Building b, Dispatcher d WHERE d.building_dispatcher=b.id ORDER BY b.name_building, d.name_dispatcher", Dispatcher.class).getResultList();
    }
    
    public static List<Dispatcher> findDispatchersOfBuilding(Long id) {
        return entityManager().createQuery("SELECT o FROM Dispatcher o where o.buildingDispatcher.id="+id+" or o.id=0", Dispatcher.class).getResultList();
    }
    
    public static Dispatcher findDispatcherOfBand(Long id) {
		return (Dispatcher) entityManager().createNativeQuery(
				"SELECT d.* FROM Dispatcher d, Band b WHERE b.dispatcher_band=d.id and b.id="
						+ id, Dispatcher.class).getSingleResult();
	}
    
    @SuppressWarnings("unchecked")
	public static List<Dispatcher> findDispatchersOfBand(Long id) {
		return entityManager().createNativeQuery(
				"SELECT d.* FROM Dispatcher d, Band b WHERE b.dispatcher_band=d.id and b.id="
						+ id, Dispatcher.class).getResultList();
	}
    
    @SuppressWarnings("unchecked")
	public static List<Dispatcher> findDispatchersOfNetSwitch(Long id) {
		return entityManager().createNativeQuery(
				"SELECT DISTINCT d.* FROM Dispatcher d, Net_Switch s WHERE d.id=0 or (s.dispatcher_net_switch=d.id and s.id="
						+ id+")", Dispatcher.class).getResultList();
	}
    
    public static Dispatcher maxId() {
		return entityManager().createQuery(
				"SELECT o FROM Dispatcher o WHERE o.id=(SELECT MAX(d.id) FROM Dispatcher d)", Dispatcher.class).getSingleResult();
	}
	
	@Transactional
	public void insert() {
		Query query = entityManager
				.createNativeQuery("INSERT INTO Dispatcher(id, name_dispatcher, building_dispatcher)"
						+ " VALUES(?,?,?)");
		query.setParameter(1, this.getId());
		query.setParameter(2, this.getNameDispatcher());
		query.setParameter(3, this.getBuildingDispatcher());
		query.executeUpdate();
	}
}
