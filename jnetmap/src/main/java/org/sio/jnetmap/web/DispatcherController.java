package org.sio.jnetmap.web;

import org.sio.jnetmap.domain.Dispatcher;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/dispatchers")
@Controller
@RooWebScaffold(path = "dispatchers", formBackingObject = Dispatcher.class)
public class DispatcherController {
}
