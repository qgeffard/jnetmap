// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.sio.jnetmap.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.sio.jnetmap.domain.Dispatcher;
import org.sio.jnetmap.domain.NetSwitch;
import org.sio.jnetmap.web.NetSwitchController;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect NetSwitchController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String NetSwitchController.create(@Valid NetSwitch netSwitch, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, netSwitch);
            return "netswitches/create";
        }
        uiModel.asMap().clear();
        netSwitch.persist();
        return "redirect:/netswitches/" + encodeUrlPathSegment(netSwitch.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String NetSwitchController.createForm(Model uiModel) {
        populateEditForm(uiModel, new NetSwitch());
        return "netswitches/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String NetSwitchController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("netswitch", NetSwitch.findNetSwitch(id));
        uiModel.addAttribute("itemId", id);
        return "netswitches/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String NetSwitchController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("netswitches", NetSwitch.findNetSwitchEntries(firstResult, sizeNo));
            float nrOfPages = (float) NetSwitch.countNetSwitches() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("netswitches", NetSwitch.findAllNetSwitches());
        }
        return "netswitches/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String NetSwitchController.update(@Valid NetSwitch netSwitch, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, netSwitch);
            return "netswitches/update";
        }
        uiModel.asMap().clear();
        netSwitch.merge();
        return "redirect:/netswitches/" + encodeUrlPathSegment(netSwitch.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String NetSwitchController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, NetSwitch.findNetSwitch(id));
        return "netswitches/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String NetSwitchController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        NetSwitch netSwitch = NetSwitch.findNetSwitch(id);
        netSwitch.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/netswitches";
    }
    
    void NetSwitchController.populateEditForm(Model uiModel, NetSwitch netSwitch) {
        uiModel.addAttribute("netSwitch", netSwitch);
        uiModel.addAttribute("dispatchers", Dispatcher.findAllDispatchers());
    }
    
    String NetSwitchController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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