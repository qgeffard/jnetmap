// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.sio.jnetmap.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.sio.jnetmap.domain.NetModule;
import org.sio.jnetmap.domain.NetSwitch;
import org.sio.jnetmap.web.NetModuleController;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect NetModuleController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String NetModuleController.create(@Valid NetModule netModule, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, netModule);
            return "netmodules/create";
        }
        uiModel.asMap().clear();
        netModule.persist();
        return "redirect:/netmodules/" + encodeUrlPathSegment(netModule.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String NetModuleController.createForm(Model uiModel) {
        populateEditForm(uiModel, new NetModule());
        return "netmodules/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String NetModuleController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("netmodule", NetModule.findNetModule(id));
        uiModel.addAttribute("itemId", id);
        return "netmodules/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String NetModuleController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("netmodules", NetModule.findNetModuleEntries(firstResult, sizeNo));
            float nrOfPages = (float) NetModule.countNetModules() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("netmodules", NetModule.findAllNetModules());
        }
        return "netmodules/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String NetModuleController.update(@Valid NetModule netModule, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, netModule);
            return "netmodules/update";
        }
        uiModel.asMap().clear();
        netModule.merge();
        return "redirect:/netmodules/" + encodeUrlPathSegment(netModule.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String NetModuleController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, NetModule.findNetModule(id));
        return "netmodules/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String NetModuleController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        NetModule netModule = NetModule.findNetModule(id);
        netModule.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/netmodules";
    }
    
    void NetModuleController.populateEditForm(Model uiModel, NetModule netModule) {
        uiModel.addAttribute("netModule", netModule);
        uiModel.addAttribute("netswitches", NetSwitch.findAllNetSwitches());
    }
    
    String NetModuleController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
