package org.sio.jnetmap.web;

import org.sio.jnetmap.domain.Building;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/buildings")
@Controller
@RooWebScaffold(path = "buildings", formBackingObject = Building.class)
public class BuildingController {
}
