package org.sio.jnetmap.web;

import org.sio.jnetmap.domain.Port;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ports")
@Controller
@RooWebScaffold(path = "ports", formBackingObject = Port.class)
public class PortController {
}
