package gilko.marcin.memory5.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gilko.marcin.memory5.model.Slowo;
import gilko.marcin.memory5.service.BazaSlowekService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class BazaSlowekController {
	
	@Autowired
	private BazaSlowekService service;
	
	/*@RequestMapping("/baza_slowek")
	public String bazaSlowek(Model model) {
		List<Slowo> listaSlow = service.list();
		model.addAttribute("listaSlow", listaSlow);
		return "baza_slowek";
	}*/
	@PostMapping("/baza_slowek")
	public ResponseEntity<Slowo> dodajSlowo(@RequestBody Slowo slowo){
		try {
			
			service.save(slowo);
			return new ResponseEntity<>(slowo, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/baza_slowek")
	public ResponseEntity<List<Slowo>> wyswietlWszystkieSlowa(@RequestParam(required = false) String slowo){
		try {
			List<Slowo> slowa = service.list();
			
			if(slowo == null) {
				//dorobic
			}else {
				//dorobic
			}
			if(slowa.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(slowa, HttpStatus.OK);
				
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//tylko test, pozniej wykasowac
	@GetMapping("/slowka")
	public ResponseEntity<List<Slowo>> getAllSlowo(){
		List<Slowo> listaSlow = service.list();
		return new ResponseEntity<>(listaSlow, HttpStatus.OK);
	}
}
