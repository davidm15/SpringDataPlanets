package at.fh.swenga.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.GalaxyRepository;
import at.fh.swenga.jpa.dao.PlanetRepository;
import at.fh.swenga.jpa.model.GalaxyModel;
import at.fh.swenga.jpa.model.PlanetModel;

@Controller
public class PlanetController {

	@Autowired
	PlanetRepository planetRepository;
	
	@Autowired
	GalaxyRepository galaxyRepository;
	
	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
		List<PlanetModel> planets = planetRepository.findAll();
		model.addAttribute("planets", planets);
		model.addAttribute("count", planets.size());
		return "index";
	}
	
	@RequestMapping(value = { "/getPage" })
	public String getPage(Pageable page,Model model) {
		Page<PlanetModel> planetsPage = planetRepository.findAll(page);
		model.addAttribute("planets", planetsPage.getContent());
		model.addAttribute("count", planetsPage.getTotalElements());
		return "index";
	}
	
	@RequestMapping(value = { "/find" })
	public String find(Model model, @RequestParam String searchString, @RequestParam String searchType) {
		List<PlanetModel> planets = null;
		int count=0;

		switch (searchType) {
		case "query1":
			planets = planetRepository.findAll();
			break;
		case "query2":
			planets = planetRepository.findByNameContaining(searchString);
			break;
		case "query3":
			planets = planetRepository.findByNameOrSurface(searchString,searchString);
			break;
		case "query4":
			planets = planetRepository.doANameSearchWithLike("%"+searchString+"%");
			break;
		case "query5":
			planets = planetRepository.findBySize(Float.parseFloat(searchString));
			break;
		case "query6":
			count = planetRepository.deleteByName(searchString);		
			break;
		case "query7":
			planets = planetRepository.findByNameOrderBySize(searchString);		
			break;
		case "query8":
			planets = planetRepository.findByNameContainingOrSurfaceContainingAllIgnoreCase(searchString,searchString);		
			break;
		case "query9":
			planets = planetRepository.findBySizeGreaterThan20000();	
			break;
		case "query10":
			planets = planetRepository.findByWhateverName(searchString);	
			break;
		case "query11":
			count = planetRepository.deleteByGalaxyName(searchString);	
			break;
		
		default:
			planets = planetRepository.findAll();
		}
		
		model.addAttribute("planets", planets);

		if (planets!=null) {
			model.addAttribute("count", planets.size());			
		}
		else {
			model.addAttribute("count", count);				
		}
		return "index";
	}
	
	@RequestMapping("/fillPlanetList")
	@Transactional
	public String fillData(Model model) {
		
		GalaxyModel galaxy = new GalaxyModel("Milkyway");
		
		PlanetModel planet1 = new PlanetModel("Earth","Blue",40000);
		planet1.setGalaxy(galaxy);
		planetRepository.save(planet1);
		
		PlanetModel planet2 = new PlanetModel("Mars","Red",30000);
		planet2.setGalaxy(galaxy);
		planetRepository.save(planet2);
		
		PlanetModel planet3 = new PlanetModel("Saturn","Brown",100000);
		planet3.setGalaxy(galaxy);
		planetRepository.save(planet3);
	
		return "forward:list";
	}
	
	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		planetRepository.delete(id);

		return "forward:list";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}
	
}
