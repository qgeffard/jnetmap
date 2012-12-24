package org.sio.jnetmap.web;

import org.sio.jnetmap.domain.NetModule;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/netmodules")
@Controller
@RooWebScaffold(path = "netmodules", formBackingObject = NetModule.class)
public class NetModuleController {
}
