package org.sio.jnetmap.web;

import org.sio.jnetmap.domain.Band;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/bands")
@Controller
@RooWebScaffold(path = "bands", formBackingObject = Band.class)
public class BandController {
}
