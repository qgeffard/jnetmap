package org.sio.jnetmap.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sio.jnetmap.domain.Band;
import org.sio.jnetmap.domain.Building;
import org.sio.jnetmap.domain.Dispatcher;
import org.sio.jnetmap.domain.NetModule;
import org.sio.jnetmap.domain.NetSwitch;
import org.sio.jnetmap.domain.Outlet;
import org.sio.jnetmap.domain.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@RequestMapping("/uc/admin/**")
@Controller
public class AdminController {

	@RequestMapping(method = RequestMethod.POST, value = "{id}")
	public void post(@PathVariable Long id, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
	}

	@RequestMapping
	public String index(ModelMap modelMap, WebRequest wr) {
		List<Building> buildings = Building.findAllBuildingsOrder();
		List<Room> rooms = Room.findAllRoomsOrder();
		List<Dispatcher> dispatchers = Dispatcher.findAllDispatchersOrder();
		List<Band> bands = Band.findAllBandsOrder();
		List<NetSwitch> netSwitches = NetSwitch.findAllNetSwitchesOrder();
		List<Outlet> outlets = Outlet.findAllOutletsOrder();
		List<NetModule> netModules = NetModule.findAllNetModulesOrder();

		String strBuildId = wr.getParameter("building");
		String strRoomId = wr.getParameter("room");
		String strDispatcherId = wr.getParameter("dispatcher");
		String strBandId = wr.getParameter("band");
		String strNetSwitchId = wr.getParameter("netSwitch");
		String strOutletId = wr.getParameter("outlet");
		String strNetModuleId = wr.getParameter("module");
		
		String selected = wr.getParameter("selected");
		if(selected == null){
			selected = "nothing";
		}

		Long buildId = 0L;
		Long roomId = 0L;
		Long dispatcherId = 0L;
		Long bandId = 0L;
		Long netSwitchId = 0L;
		Long outletId = 0L;
		Long netModuleId = 0L;

		if(strBuildId != null && strRoomId != null && strDispatcherId != null
				&& strBandId != null && strNetSwitchId != null && strOutletId != null  && strNetModuleId != null) {
			buildId = Long.parseLong(strBuildId);
			roomId = Long.parseLong(strRoomId);
			dispatcherId = Long.parseLong(strDispatcherId);
			bandId = Long.parseLong(strBandId);
			netSwitchId = Long.parseLong(strNetSwitchId);
			outletId = Long.parseLong(strOutletId);
			netModuleId = Long.parseLong(strNetModuleId);
		}
		
		if(selected.equals("building")) {
			if(buildId != 0){
				rooms = Room.findRoomsOfBuilding(buildId);
				dispatchers = Dispatcher.findDispatchersOfBuilding(buildId);
				bands = Band.findBandsOfBuilding(buildId);
				netSwitches = NetSwitch.findNetSwitchesOfBuilding(buildId);
				outlets = Outlet.findOutletsOfBuilding(buildId);
				netModules = NetModule.findNetModulesOfBuilding(buildId);
				modelMap.addAttribute("roomId", roomId);
				modelMap.addAttribute("buildingId", buildId);
				modelMap.addAttribute("dispatcherId", dispatcherId);
				if(dispatcherId != 0) {
					bands = Band.findBandsOfDispatcher(dispatcherId);
					netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatcherId);
					modelMap.addAttribute("bandId", bandId);
					modelMap.addAttribute("netSwitchId", netSwitchId);
					modelMap.addAttribute("outletId", outletId);
					if(roomId != 0 && bandId == 0){
						outlets = Outlet.findOutletsOfRoom(roomId);
					}else if(roomId != 0 && bandId != 0){
						outlets = Outlet.findOutletsOfRoomAndBand(roomId, bandId);
					}else if(roomId == 0 && bandId != 0){
						outlets = Outlet.findOutletsOfBand(bandId);
					}
					if(netSwitchId != 0){
						netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
						modelMap.addAttribute("netModuleId", netModuleId);
					}
				}
			}
		}else if(selected.equals("room")){
			if(roomId != 0){
				Building building = Building.findBuildingOfRoom(roomId);
				rooms = Room.findRoomsOfBuilding(building.getId());
				if(netSwitchId != 0){
					dispatchers = Dispatcher.findDispatchersOfNetSwitch(netSwitchId);
					bands = Band.findBandsOfNetSwitch(netSwitchId);
					netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
				}else{
					dispatchers = Dispatcher.findDispatchersOfBuilding(building.getId());
					bands = Band.findBandsOfBuilding(building.getId());
					netModules = NetModule.findNetModulesOfBuilding(building.getId());
				}
				netSwitches = NetSwitch.findNetSwitchesOfBuilding(building.getId());
				if(bandId == 0){
					outlets = Outlet.findOutletsOfRoom(roomId);
				}else{
					outlets = Outlet.findOutletsOfRoomAndBand(roomId, bandId);
					Dispatcher dispatcher = Dispatcher.findDispatcherOfBand(bandId);
					bands = Band.findBandsOfDispatcher(dispatcher.getId());
					netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatcher.getId());
				}
				if(netSwitchId != 0){
					netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
				}
				modelMap.addAttribute("buildingId", building.getId());
			}else{
				if(bandId != 0){
					outlets = Outlet.findOutletsOfBand(bandId);
				}else{
					outlets = Outlet.findOutletsOfBuilding(buildId);
				}
				rooms = Room.findRoomsOfBuilding(buildId);
				if(netSwitchId != 0){
					dispatchers = Dispatcher.findDispatchersOfNetSwitch(netSwitchId);
					bands = Band.findBandsOfNetSwitch(netSwitchId);
					netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
				}else{
					dispatchers = Dispatcher.findDispatchersOfBuilding(buildId);
					bands = Band.findBandsOfBuilding(buildId);
					netModules = NetModule.findNetModulesOfBuilding(buildId);
				}
				netSwitches = NetSwitch.findNetSwitchesOfBuilding(buildId);
				modelMap.addAttribute("buildingId", buildId);
			}
			if(dispatcherId != 0){
				bands = Band.findBandsOfDispatcher(dispatcherId);
				netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatcherId);
			}
			modelMap.addAttribute("dispatcherId", dispatcherId);
			modelMap.addAttribute("roomId", roomId);
			modelMap.addAttribute("bandId", bandId);
			modelMap.addAttribute("netSwitchId", netSwitchId);
		}else if(selected.equals("dispatcher")){
			if(dispatcherId != 0){
				Building building = Building.findBuildingOfDispatcher(dispatcherId);
				rooms = Room.findRoomsOfBuilding(building.getId());
				if(netSwitchId != 0){
					dispatchers = Dispatcher.findDispatchersOfNetSwitch(netSwitchId);
					netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
				}else{
					dispatchers = Dispatcher.findDispatchersOfBuilding(building.getId());
					netModules = NetModule.findNetModulesOfBuilding(building.getId());
				}
				bands = Band.findBandsOfDispatcher(dispatcherId);
				netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatcherId);
				outlets = Outlet.findOutletsOfBuilding(building.getId());
				modelMap.addAttribute("buildingId", building.getId());
				modelMap.addAttribute("bandId", bandId);
				modelMap.addAttribute("netSwitchId", netSwitchId);
			}else{
				if(netSwitchId != 0){
					netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
				}else{
					netModules = NetModule.findNetModulesOfBuilding(buildId);
				}
				rooms = Room.findRoomsOfBuilding(buildId);
				dispatchers = Dispatcher.findDispatchersOfBuilding(buildId);
				bands = Band.findBandsOfBuilding(buildId);
				netSwitches = NetSwitch.findNetSwitchesOfBuilding(buildId);
				outlets = Outlet.findOutletsOfBuilding(buildId);
				modelMap.addAttribute("buildingId", buildId);
			}
			if(roomId != 0 && bandId == 0){
				outlets = Outlet.findOutletsOfRoom(roomId);
			}else if(roomId != 0 && bandId != 0){
				outlets = Outlet.findOutletsOfRoomAndBand(roomId, bandId);
			}else if(roomId == 0 && bandId != 0){
				outlets = Outlet.findOutletsOfBand(bandId);
			}
			modelMap.addAttribute("dispatcherId", dispatcherId);
			modelMap.addAttribute("roomId", roomId);
		}else if(selected.equals("band")){
			if(bandId != 0){
				Dispatcher dispatcher = Dispatcher.findDispatcherOfBand(bandId);
				Building building = Building.findBuildingOfDispatcher(dispatcher.getId());
				bands = Band.findBandsOfDispatcher(dispatcher.getId());
				rooms = Room.findRoomsOfBuilding(building.getId());
				netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatcher.getId());
				if(roomId == 0){
					outlets = Outlet.findOutletsOfBand(bandId);
				}else{
					outlets = Outlet.findOutletsOfRoomAndBand(roomId, bandId);
				}
				if(netSwitchId == 0){
					netModules = NetModule.findNetModulesOfBuilding(building.getId());
					dispatchers = Dispatcher.findDispatchersOfBuilding(building.getId());
				}else{
					netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
					dispatchers = Dispatcher.findDispatchersOfNetSwitch(netSwitchId);
				}
				modelMap.addAttribute("dispatcherId", dispatcher.getId());
				modelMap.addAttribute("buildingId", building.getId());
			}else{
				if(roomId != 0){
					outlets = Outlet.findOutletsOfRoom(roomId);
					Building building = Building.findBuildingOfRoom(roomId);
					dispatchers = Dispatcher.findDispatchersOfBuilding(building.getId());
					rooms = Room.findRoomsOfBuilding(building.getId());
					netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatcherId);
					bands = Band.findBandsOfDispatcher(dispatcherId);
					if(netSwitchId == 0){
						netModules = NetModule.findNetModulesOfBuilding(building.getId());
					}else{
						netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
					}
					modelMap.addAttribute("buildingId", building.getId());
				}else{
					rooms = Room.findRoomsOfBuilding(buildId);
					dispatchers = Dispatcher.findDispatchersOfBuilding(buildId);
					if(dispatcherId == 0){
						bands = Band.findBandsOfBuilding(buildId);
						netSwitches = NetSwitch.findNetSwitchesOfBuilding(buildId);
					}else{
						bands = Band.findBandsOfDispatcher(dispatcherId);
						netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatcherId);
					}
					outlets = Outlet.findOutletsOfBuilding(buildId);
					netModules = NetModule.findNetModulesOfBuilding(buildId);
					modelMap.addAttribute("buildingId", buildId);
				}
				modelMap.addAttribute("dispatcherId", dispatcherId);
			}
			modelMap.addAttribute("bandId", bandId);
			modelMap.addAttribute("roomId", roomId);
			modelMap.addAttribute("netSwitchId", netSwitchId);
		}else if(selected.equals("switch")){
			if(netSwitchId != 0){
				Building building = Building.findBuildingOfNetSwitch(netSwitchId);
				rooms = Room.findRoomsOfBuilding(building.getId());
				dispatchers = Dispatcher.findDispatchersOfNetSwitch(netSwitchId);
				bands = Band.findBandsOfNetSwitch(netSwitchId);
				if(roomId != 0 && bandId == 0){
					outlets = Outlet.findOutletsOfRoom(roomId);
				}else if(roomId != 0 && bandId != 0){
					outlets = Outlet.findOutletsOfRoomAndBand(roomId, bandId);
				}else if(roomId == 0 && bandId != 0){
					outlets = Outlet.findOutletsOfBand(bandId);
				}else{
					outlets = Outlet.findOutletsOfBuilding(building.getId());
				}
				netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatchers.get(1).getId());
				netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
				modelMap.addAttribute("dispatcherId", dispatchers.get(1).getId());
				modelMap.addAttribute("buildingId", building.getId());
				modelMap.addAttribute("netSwitchId", netSwitchId);
			}else{
				rooms = Room.findRoomsOfBuilding(buildId);
				dispatchers = Dispatcher.findDispatchersOfBuilding(buildId);
				if(dispatcherId == 0){
					bands = Band.findBandsOfBuilding(buildId);
					netSwitches = NetSwitch.findNetSwitchesOfBuilding(buildId);
				}else{
					bands = Band.findBandsOfDispatcher(dispatcherId);
					netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatcherId);
				}
				if(roomId != 0 && bandId == 0){
					outlets = Outlet.findOutletsOfRoom(roomId);
				}else if(roomId != 0 && bandId != 0){
					outlets = Outlet.findOutletsOfRoomAndBand(roomId, bandId);
				}else if(roomId == 0 && bandId != 0){
					outlets = Outlet.findOutletsOfBand(bandId);
				}else{
					outlets = Outlet.findOutletsOfBuilding(buildId);
				}
				netModules = NetModule.findNetModulesOfBuilding(buildId);
				modelMap.addAttribute("buildingId", buildId);
				modelMap.addAttribute("dispatcherId", dispatcherId);
			}
			modelMap.addAttribute("roomId", roomId);
			modelMap.addAttribute("bandId", bandId);
		}
		
		List<Dispatcher> dispatchersBis = Dispatcher.findAllDispatchersOrder();
		modelMap.addAttribute("dispatchersBis", dispatchersBis);
		
		modelMap.addAttribute("buildings", buildings);
		modelMap.addAttribute("buildingsCount", buildings.size()-1);
		modelMap.addAttribute("rooms", rooms);
		modelMap.addAttribute("roomsCount", rooms.size()-1);
		modelMap.addAttribute("dispatchers", dispatchers);
		modelMap.addAttribute("dispatchersCount", dispatchers.size()-1);
		modelMap.addAttribute("bands", bands);
		modelMap.addAttribute("bandsCount", bands.size());
		modelMap.addAttribute("netSwitches", netSwitches);
		modelMap.addAttribute("netSwitchesCount", netSwitches.size()-1);
		modelMap.addAttribute("outlets", outlets);
		modelMap.addAttribute("outletsCount", outlets.size());
		modelMap.addAttribute("netModules", netModules);
		modelMap.addAttribute("netModulesCount", netModules.size());

		return "uc/admin/index";
	}
}
