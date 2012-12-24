package org.sio.jnetmap.web;

import org.sio.jnetmap.domain.Sonde;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sondes")
@Controller
@RooWebScaffold(path = "sondes", formBackingObject = Sonde.class)
public class SondeController {
}
