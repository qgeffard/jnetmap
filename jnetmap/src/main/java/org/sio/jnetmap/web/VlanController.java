package org.sio.jnetmap.web;

import org.sio.jnetmap.domain.Vlan;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/vlans")
@Controller
@RooWebScaffold(path = "vlans", formBackingObject = Vlan.class)
public class VlanController {
}
