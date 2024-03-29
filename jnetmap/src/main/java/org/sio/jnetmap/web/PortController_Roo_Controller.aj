// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.sio.jnetmap.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.sio.jnetmap.domain.NetModule;
import org.sio.jnetmap.domain.Outlet;
import org.sio.jnetmap.domain.Port;
import org.sio.jnetmap.domain.Vlan;
import org.sio.jnetmap.web.PortController;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect PortController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String PortController.create(@Valid Port port, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, port);
            return "ports/create";
        }
        uiModel.asMap().clear();
        port.persist();
        return "redirect:/ports/" + encodeUrlPathSegment(port.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String PortController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Port());
        return "ports/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String PortController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("port", Port.findPort(id));
        uiModel.addAttribute("itemId", id);
        return "ports/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String PortController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("ports", Port.findPortEntries(firstResult, sizeNo));
            float nrOfPages = (float) Port.countPorts() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("ports", Port.findAllPorts());
        }
        return "ports/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String PortController.update(@Valid Port port, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, port);
            return "ports/update";
        }
        uiModel.asMap().clear();
        port.merge();
        return "redirect:/ports/" + encodeUrlPathSegment(port.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String PortController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Port.findPort(id));
        return "ports/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String PortController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Port port = Port.findPort(id);
        port.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/ports";
    }
    
    void PortController.populateEditForm(Model uiModel, Port port) {
        uiModel.addAttribute("port", port);
        uiModel.addAttribute("netmodules", NetModule.findAllNetModules());
        uiModel.addAttribute("outlets", Outlet.findAllOutlets());
        uiModel.addAttribute("vlans", Vlan.findAllVlans());
    }
    
    String PortController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
