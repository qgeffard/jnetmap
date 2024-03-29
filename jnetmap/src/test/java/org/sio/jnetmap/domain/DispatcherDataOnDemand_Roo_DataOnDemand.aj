// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.sio.jnetmap.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.sio.jnetmap.domain.BuildingDataOnDemand;
import org.sio.jnetmap.domain.Dispatcher;
import org.sio.jnetmap.domain.DispatcherDataOnDemand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect DispatcherDataOnDemand_Roo_DataOnDemand {
    
    declare @type: DispatcherDataOnDemand: @Component;
    
    private Random DispatcherDataOnDemand.rnd = new SecureRandom();
    
    private List<Dispatcher> DispatcherDataOnDemand.data;
    
    @Autowired
    private BuildingDataOnDemand DispatcherDataOnDemand.buildingDataOnDemand;
    
    public Dispatcher DispatcherDataOnDemand.getNewTransientDispatcher(int index) {
        Dispatcher obj = new Dispatcher();
        setNameDispatcher(obj, index);
        return obj;
    }
    
    public void DispatcherDataOnDemand.setNameDispatcher(Dispatcher obj, int index) {
        String nameDispatcher = "nameDispatcher_" + index;
        if (nameDispatcher.length() > 30) {
            nameDispatcher = nameDispatcher.substring(0, 30);
        }
        obj.setNameDispatcher(nameDispatcher);
    }
    
    public Dispatcher DispatcherDataOnDemand.getSpecificDispatcher(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Dispatcher obj = data.get(index);
        Long id = obj.getId();
        return Dispatcher.findDispatcher(id);
    }
    
    public Dispatcher DispatcherDataOnDemand.getRandomDispatcher() {
        init();
        Dispatcher obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Dispatcher.findDispatcher(id);
    }
    
    public boolean DispatcherDataOnDemand.modifyDispatcher(Dispatcher obj) {
        return false;
    }
    
    public void DispatcherDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Dispatcher.findDispatcherEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Dispatcher' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Dispatcher>();
        for (int i = 0; i < 10; i++) {
            Dispatcher obj = getNewTransientDispatcher(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
