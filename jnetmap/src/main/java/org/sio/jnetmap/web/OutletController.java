package org.sio.jnetmap.web;

import org.sio.jnetmap.domain.Outlet;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/outlets")
@Controller
@RooWebScaffold(path = "outlets", formBackingObject = Outlet.class)
public class OutletController {
}
