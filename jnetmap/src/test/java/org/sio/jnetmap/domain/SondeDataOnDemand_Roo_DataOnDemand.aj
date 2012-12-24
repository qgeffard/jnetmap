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
import org.sio.jnetmap.domain.Sonde;
import org.sio.jnetmap.domain.SondeDataOnDemand;
import org.springframework.stereotype.Component;

privileged aspect SondeDataOnDemand_Roo_DataOnDemand {
    
    declare @type: SondeDataOnDemand: @Component;
    
    private Random SondeDataOnDemand.rnd = new SecureRandom();
    
    private List<Sonde> SondeDataOnDemand.data;
    
    public Sonde SondeDataOnDemand.getNewTransientSonde(int index) {
        Sonde obj = new Sonde();
        setMacSonde(obj, index);
        setNumSonde(obj, index);
        return obj;
    }
    
    public void SondeDataOnDemand.setMacSonde(Sonde obj, int index) {
        String macSonde = "macSonde_" + index;
        if (macSonde.length() > 30) {
            macSonde = macSonde.substring(0, 30);
        }
        obj.setMacSonde(macSonde);
    }
    
    public void SondeDataOnDemand.setNumSonde(Sonde obj, int index) {
        String numSonde = "numSonde_" + index;
        if (numSonde.length() > 30) {
            numSonde = numSonde.substring(0, 30);
        }
        obj.setNumSonde(numSonde);
    }
    
    public Sonde SondeDataOnDemand.getSpecificSonde(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Sonde obj = data.get(index);
        Long id = obj.getId();
        return Sonde.findSonde(id);
    }
    
    public Sonde SondeDataOnDemand.getRandomSonde() {
        init();
        Sonde obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Sonde.findSonde(id);
    }
    
    public boolean SondeDataOnDemand.modifySonde(Sonde obj) {
        return false;
    }
    
    public void SondeDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Sonde.findSondeEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Sonde' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Sonde>();
        for (int i = 0; i < 10; i++) {
            Sonde obj = getNewTransientSonde(i);
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
