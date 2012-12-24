// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.sio.jnetmap.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.sio.jnetmap.domain.Outlet;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Outlet_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Outlet.entityManager;
    
    public static final EntityManager Outlet.entityManager() {
        EntityManager em = new Outlet().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Outlet.countOutlets() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Outlet o", Long.class).getSingleResult();
    }
    
    public static List<Outlet> Outlet.findAllOutlets() {
        return entityManager().createQuery("SELECT o FROM Outlet o WHERE o.id!=0", Outlet.class).getResultList();
    }
    
    public static Outlet Outlet.findOutlet(Long id) {
        if (id == null) return null;
        return entityManager().find(Outlet.class, id);
    }
    
    public static List<Outlet> Outlet.findOutletEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Outlet o", Outlet.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Outlet.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Outlet.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Outlet attached = Outlet.findOutlet(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Outlet.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Outlet.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Outlet Outlet.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Outlet merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
