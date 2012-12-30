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

	@RequestMapping(value="index")
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
		String type = wr.getParameter("type");
		if(selected == null){
			selected = "nothing";
		}
		if(type == null){
			type = "nothing";
		}

		Long buildId = 0L;
		Long roomId = 0L;
		Long dispatcherId = 0L;
		Long bandId = 0L;
		Long netSwitchId = 0L;
		Long outletId = 0L;
		Long netModuleId = 0L;

		if(strBuildId != null) {
			buildId = Long.parseLong(strBuildId);
		}
		if(strRoomId != null){
			roomId = Long.parseLong(strRoomId);
		}
		if(strDispatcherId != null){
			dispatcherId = Long.parseLong(strDispatcherId);
		}
		if(strBandId != null){
			bandId = Long.parseLong(strBandId);
		}
		if(strNetSwitchId != null){
			netSwitchId = Long.parseLong(strNetSwitchId);
		}
		if(strOutletId != null){
			outletId = Long.parseLong(strOutletId);
		}
		if(strNetModuleId != null){
			netModuleId = Long.parseLong(strNetModuleId);
		}
		
		modelMap.addAttribute("buildingId", 0);
		if(type.equals("show")){
			if(selected.equals("building")){
				modelMap.addAllAttributes(show(selected, buildId));
			}else if(selected.equals("room")){
				modelMap.addAllAttributes(show(selected, roomId));
			}else if(selected.equals("dispatcher")){
				modelMap.addAllAttributes(show(selected, dispatcherId));
			}else if(selected.equals("band")){
				modelMap.addAllAttributes(show(selected, bandId));
			}else if(selected.equals("switch")){
				modelMap.addAllAttributes(show(selected, netSwitchId));
			}else if(selected.equals("outlet")){
				modelMap.addAllAttributes(show(selected, outletId));
			}else if(selected.equals("module")){
				modelMap.addAllAttributes(show(selected, netModuleId));
			}
			modelMap.addAttribute("type", selected);
			return "uc/admin/show";
		}else if(type.equals("update")){
			if(selected.equals("building")){
				modelMap.addAllAttributes(update(selected, buildId));
			}else if(selected.equals("room")){
				modelMap.addAllAttributes(update(selected, roomId));
			}else if(selected.equals("dispatcher")){
				modelMap.addAllAttributes(update(selected, dispatcherId));
			}else if(selected.equals("band")){
				modelMap.addAllAttributes(update(selected, bandId));
			}else if(selected.equals("switch")){
				modelMap.addAllAttributes(update(selected, netSwitchId));
			}else if(selected.equals("outlet")){
				modelMap.addAllAttributes(update(selected, outletId));
			}else if(selected.equals("module")){
				modelMap.addAllAttributes(update(selected, netModuleId));
			}
			modelMap.addAttribute("type", selected);
			return "uc/admin/update";
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
				modelMap.addAttribute("outletId", outletId);
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
				modelMap.addAttribute("outletId", outletId);
			}else{
				rooms = Room.findRoomsOfBuilding(buildId);
				dispatchers = Dispatcher.findDispatchersOfBuilding(buildId);
				if(dispatcherId == 0){
					bands = Band.findBandsOfBuilding(buildId);
					netSwitches = NetSwitch.findNetSwitchesOfBuilding(buildId);
					netModules = NetModule.findNetModulesOfBuilding(buildId);
				}else{
					bands = Band.findBandsOfDispatcher(dispatcherId);
					netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatcherId);
					netModules = NetModule.findNetModulesOfDispatcher(dispatcherId);
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
				modelMap.addAttribute("buildingId", buildId);
				modelMap.addAttribute("dispatcherId", dispatcherId);
			}
			modelMap.addAttribute("roomId", roomId);
			modelMap.addAttribute("bandId", bandId);
		}else if(selected.equals("outlet")){
			/*
			SELECT * FROM OUTLET o WHERE o.ID=1847
			Résultat => Id band : 34 - Id room : 33
			
			SELECT * FROM ROOM r WHERE r.ID=33
			Résultat => Room : cdti - Id building : 5
			
			SELECT * FROM BUILDING b WHERE b.ID=5
			Résultat => Building : C
			
			SELECT * FROM BAND b WHERE b.ID=34
			Résultat => Id dispatcher : 6
			
			SELECT * FROM DISPATCHER d WHERE d.ID=6
			Résultat => Dispatcher : RGIF
			
			ERREUR
			La prise se trouve dans le batiment C mais utilise le répartiteur RGIF qui est utilisé pour le batiment F (illogique)
			*/
			if(outletId != 0){
				Building building = Building.findBuildingOfOutletWhitchRoom(outletId);
				if(Band.findBandsOfBuilding(building.getId()).size() != 0){
					building = Building.findBuildingOfOutletWhitchBand(outletId);
					bands = Band.findBandsOfDispatcher(Dispatcher.findDispatcherOfBand(Band.findBandOfOutlet(outletId).getId()).getId());
					netSwitches = NetSwitch.findNetSwitchesOfDispatcher(Dispatcher.findDispatcherOfBand(Band.findBandOfOutlet(outletId).getId()).getId());
					if(netSwitchId != 0){
						netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
					}else{
						netModules = NetModule.findNetModulesOfDispatcher(Dispatcher.findDispatcherOfBand(Band.findBandOfOutlet(outletId).getId()).getId());
					}
					if(Room.findRoomOfOutlet(outletId)==null){
						outlets = Outlet.findOutletsOfBand(Band.findBandOfOutlet(outletId).getId());
					}else{
						outlets = Outlet.findOutletsOfRoomAndBand(Room.findRoomOfOutlet(outletId).getId(), Band.findBandOfOutlet(outletId).getId());
					}
					modelMap.addAttribute("dispatcherId", Dispatcher.findDispatcherOfBand(Band.findBandOfOutlet(outletId).getId()).getId());
					modelMap.addAttribute("bandId", Band.findBandOfOutlet(outletId).getId());
					if(NetSwitch.findNetSwitchesOfBand(Band.findBandOfOutlet(outletId).getId()).size() <= 1){
						modelMap.addAttribute("netSwitchId", NetSwitch.findNetSwitchOfBand(Band.findBandOfOutlet(outletId).getId()).getId());
					}else{
						modelMap.addAttribute("netSwitchId", netSwitchId);
					}
				}else{
					bands = Band.findBandsOfBuilding(building.getId());
					netSwitches = NetSwitch.findNetSwitchesOfBuilding(building.getId());
					outlets = Outlet.findOutletsOfRoom(Room.findRoomOfOutlet(outletId).getId());
					if(netSwitchId != 0){
						netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
					}else if(dispatcherId != 0){
						netModules = NetModule.findNetModulesOfDispatcher(dispatcherId);
					}else{
						netModules = NetModule.findNetModulesOfBuilding(building.getId());
					}
				}
				rooms = Room.findRoomsOfBuilding(building.getId());
				dispatchers = Dispatcher.findDispatchersOfBuilding(building.getId());
				modelMap.addAttribute("buildingId", building.getId());
				modelMap.addAttribute("roomId", Room.findRoomOfOutlet(outletId).getId());
				modelMap.addAttribute("outletId", outletId);
			}else{
				rooms = Room.findRoomsOfBuilding(buildId);
				dispatchers = Dispatcher.findDispatchersOfBuilding(buildId);
				if(netSwitchId != 0){
					bands = Band.findBandsOfNetSwitch(netSwitchId);
					netModules = NetModule.findNetModulesOfNetSwitch(netSwitchId);
				}else if(dispatcherId != 0){
					bands = Band.findBandsOfDispatcher(dispatcherId);
					netModules = NetModule.findNetModulesOfDispatcher(dispatcherId);
				}else{
					bands = Band.findBandsOfBuilding(buildId);
					netModules = NetModule.findNetModulesOfBuilding(buildId);
				}
				
				if(dispatcherId != 0){
					netSwitches = NetSwitch.findNetSwitchesOfDispatcher(dispatcherId);
				}else{
					netSwitches = NetSwitch.findNetSwitchesOfBuilding(buildId);
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
				modelMap.addAttribute("buildingId", buildId);
				modelMap.addAttribute("roomId", roomId);
				modelMap.addAttribute("dispatcherId", dispatcherId);
				modelMap.addAttribute("bandId", bandId);
				modelMap.addAttribute("netSwitchId", netSwitchId);
			}
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
	
	public ModelMap show(String selected, Long id) {
		ModelMap modelMap = new ModelMap();
		if(selected.equals("building")){
			Building building = Building.findBuilding(id);
			modelMap.addAttribute("building", building);
		}else if(selected.equals("room")){
			Room room = Room.findRoom(id);
			modelMap.addAttribute("room", room);
		}else if(selected.equals("dispatcher")){
			Dispatcher dispatcher = Dispatcher.findDispatcher(id);
			modelMap.addAttribute("dispatcher", dispatcher);
		}else if(selected.equals("band")){
			Band band = Band.findBand(id);
			modelMap.addAttribute("band", band);
		}else if(selected.equals("switch")){
			NetSwitch netSwitch = NetSwitch.findNetSwitch(id);
			modelMap.addAttribute("switch", netSwitch);
		}else if(selected.equals("outlet")){
			Outlet outlet = Outlet.findOutlet(id);
			modelMap.addAttribute("outlet", outlet);
		}else if(selected.equals("module")){
			NetModule netModule = NetModule.findNetModule(id);
			modelMap.addAttribute("netModule", netModule);
		}
		return modelMap;
	}
	
	public ModelMap update(String selected, Long id) {
		ModelMap modelMap = new ModelMap();
		List<Building> buildings = Building.findAllBuildingsOrder();
		List<Room> rooms = Room.findAllRoomsOrder();
		List<Dispatcher> dispatchers = Dispatcher.findAllDispatchersOrder();
		modelMap.addAttribute("buildings", buildings);
		modelMap.addAttribute("rooms", rooms);
		modelMap.addAttribute("dispatchers", dispatchers);
		
		if(selected.equals("building")){
			Building building = Building.findBuilding(id);
			modelMap.addAttribute("building", building);
		}else if(selected.equals("room")){
			Room room = Room.findRoom(id);
			modelMap.addAttribute("room", room);
			modelMap.addAttribute("buildingId", room.getBuildingRoom().getId());
		}else if(selected.equals("dispatcher")){
			Dispatcher dispatcher = Dispatcher.findDispatcher(id);
			modelMap.addAttribute("dispatcher", dispatcher);
			modelMap.addAttribute("buildingId", dispatcher.getBuildingDispatcher().getId());
		}else if(selected.equals("band")){
			Band band = Band.findBand(id);
			modelMap.addAttribute("band", band);
			modelMap.addAttribute("buildingId", band.getDispatcherBand().getBuildingDispatcher().getId());
			modelMap.addAttribute("dispatcherId", band.getDispatcherBand().getId());
		}else if(selected.equals("switch")){
			NetSwitch netSwitch = NetSwitch.findNetSwitch(id);
			modelMap.addAttribute("switch", netSwitch);
			modelMap.addAttribute("buildingId", netSwitch.getDispatcherNetSwitch().getBuildingDispatcher().getId());
			modelMap.addAttribute("dispatcherId", netSwitch.getDispatcherNetSwitch().getId());
		}else if(selected.equals("outlet")){
			Outlet outlet = Outlet.findOutlet(id);
			modelMap.addAttribute("outlet", outlet);
			modelMap.addAttribute("buildingId", outlet.getRoomOutlet().getBuildingRoom().getId());
			modelMap.addAttribute("roomId", outlet.getRoomOutlet().getId());
			modelMap.addAttribute("dispatcherId", outlet.getBandOutlet().getDispatcherBand().getId());
			modelMap.addAttribute("bandId", outlet.getBandOutlet().getId());
		}else if(selected.equals("module")){
			NetModule netModule = NetModule.findNetModule(id);
			modelMap.addAttribute("netModule", netModule);
		}
		return modelMap;
	}
	
	@RequestMapping(value="update")
	public String doUpdate(ModelMap modelMap, WebRequest wr) {
		String selected = wr.getParameter("selected");
		List<Building> buildings = Building.findAllBuildingsOrder();
		List<Room> rooms = Room.findAllRoomsOrder();
		List<Dispatcher> dispatchers = Dispatcher.findAllDispatchersOrder();
		Building building = new Building();
		Dispatcher dispatcher = new Dispatcher();
		Room room = new Room();
		Band band = new Band();
		String strBuildId = wr.getParameter("building");
		String strDispatcherId = wr.getParameter("dispatcher");
		if(selected.equals("building")){
			strBuildId = wr.getParameter("idBuilding");
			String name = wr.getParameter("nameBuilding");
			building = Building.findBuilding(Long.parseLong(strBuildId));
			building.setNameBuilding(name);
			building.persist();
		}else if(selected.equals("room")){
			String strRoomId = wr.getParameter("idRoom");
			String nameRoom = wr.getParameter("nameRoom");
			building = Building.findBuilding(Long.parseLong(strBuildId));
			room = Room.findRoom(Long.parseLong(strRoomId));
			room.setBuildingRoom(building);
			room.setNameRoom(nameRoom);
			room.persist();
		}else if(selected.equals("dispatcher")){
			strDispatcherId = wr.getParameter("idDispatcher");
			String nameDispatcher = wr.getParameter("nameDispatcher");
			building = Building.findBuilding(Long.parseLong(strBuildId));
			dispatcher = Dispatcher.findDispatcher(Long.parseLong(strDispatcherId));
			dispatcher.setBuildingDispatcher(building);
			dispatcher.setNameDispatcher(nameDispatcher);
			dispatcher.persist();
		}else if(selected.equals("band")){
			String strBandId = wr.getParameter("idBand");
			String numBand = wr.getParameter("numBand");
			building = Building.findBuilding(Long.parseLong(strBuildId));
			dispatcher = Dispatcher.findDispatcher(Long.parseLong(strDispatcherId));
			band = Band.findBand(Long.parseLong(strBandId));
			dispatcher.setBuildingDispatcher(building);
			band.setDispatcherBand(dispatcher);
			band.setNumBand(numBand);
			band.persist();	
		}else if(selected.equals("switch")){
			String strSwitchId = wr.getParameter("idSwitch");
			String ipSwitch = wr.getParameter("ipSwitch");
			String nameSwitch = wr.getParameter("nameSwitch");
			building = Building.findBuilding(Long.parseLong(strBuildId));
			dispatcher = Dispatcher.findDispatcher(Long.parseLong(strDispatcherId));
			NetSwitch netSwitch = NetSwitch.findNetSwitch(Long.parseLong(strSwitchId));
			dispatcher.setBuildingDispatcher(building);
			netSwitch.setDispatcherNetSwitch(dispatcher);
			netSwitch.setIpNetSwitch(ipSwitch);
			netSwitch.setNameNetSwitch(nameSwitch);
			netSwitch.persist();
			modelMap.addAttribute("switch", netSwitch);
		}else if(selected.equals("outlet")){
			String strRoomId = wr.getParameter("room");
			String strBandId = wr.getParameter("idBand");
			String strOutletId = wr.getParameter("idOutlet");
			String numBand = wr.getParameter("numBand");
			String numOutlet = wr.getParameter("numOutlet");
			building = Building.findBuilding(Long.parseLong(strBuildId));
			room = Room.findRoom(Long.parseLong(strRoomId));
			dispatcher = Dispatcher.findDispatcher(Long.parseLong(strDispatcherId));
			band = Band.findBand(Long.parseLong(strBandId));
			Outlet outlet = Outlet.findOutlet(Long.parseLong(strOutletId));
			room.setBuildingRoom(building);
			dispatcher.setBuildingDispatcher(building);
			band.setDispatcherBand(dispatcher);
			band.setNumBand(numBand);
			outlet.setBandOutlet(band);
			outlet.setRoomOutlet(room);
			outlet.setNumOutlet(numOutlet);
			outlet.persist();
			modelMap.addAttribute("outlet", outlet);
		}
		
		modelMap.addAttribute("building", building);
		modelMap.addAttribute("buildingId", building.getId());
		modelMap.addAttribute("buildings", buildings);
		modelMap.addAttribute("dispatcher", dispatcher);
		modelMap.addAttribute("dispatcherId", dispatcher.getId());
		modelMap.addAttribute("dispatchers", dispatchers);
		modelMap.addAttribute("room", room);
		modelMap.addAttribute("roomId", room.getId());
		modelMap.addAttribute("rooms", rooms);
		modelMap.addAttribute("band", band);
		modelMap.addAttribute("bandId", band.getId());

		modelMap.addAttribute("type", selected);
		String update = "Updated";
		modelMap.addAttribute("update", update);
		return "uc/admin/update";
	}
}
