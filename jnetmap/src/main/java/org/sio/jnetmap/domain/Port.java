package org.sio.jnetmap.domain;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Port {

    @NotNull
    private int numPort;

    @ManyToOne
    private Vlan vlanPort;

    @ManyToOne
    private NetModule modulePort;

    @ManyToOne
    private Outlet outletPort;
}
