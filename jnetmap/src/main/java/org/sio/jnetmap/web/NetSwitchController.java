package org.sio.jnetmap.web;

import org.sio.jnetmap.domain.NetSwitch;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/netswitches")
@Controller
@RooWebScaffold(path = "netswitches", formBackingObject = NetSwitch.class)
public class NetSwitchController {
}
