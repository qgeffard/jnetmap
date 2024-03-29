// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.sio.jnetmap.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.sio.jnetmap.domain.NetModule;
import org.springframework.transaction.annotation.Transactional;

privileged aspect NetModule_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager NetModule.entityManager;
    
    public static final EntityManager NetModule.entityManager() {
        EntityManager em = new NetModule().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long NetModule.countNetModules() {
        return entityManager().createQuery("SELECT COUNT(o) FROM NetModule o", Long.class).getSingleResult();
    }
    
    public static List<NetModule> NetModule.findAllNetModules() {
        return entityManager().createQuery("SELECT o FROM NetModule o", NetModule.class).getResultList();
    }
    
    public static NetModule NetModule.findNetModule(Long id) {
        if (id == null) return null;
        return entityManager().find(NetModule.class, id);
    }
    
    public static List<NetModule> NetModule.findNetModuleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM NetModule o", NetModule.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void NetModule.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void NetModule.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            NetModule attached = NetModule.findNetModule(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void NetModule.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void NetModule.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public NetModule NetModule.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        NetModule merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
