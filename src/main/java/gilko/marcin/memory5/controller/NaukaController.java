package gilko.marcin.memory5.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import gilko.marcin.memory5.model.Nauka;
import gilko.marcin.memory5.model.Slowo;
import gilko.marcin.memory5.model.Zdanie;
import gilko.marcin.memory5.service.BazaSlowekService;
import gilko.marcin.memory5.service.NaukaService;
import gilko.marcin.memory5.service.ZdanieService;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class NaukaController {
	
	@Autowired
	private BazaSlowekService bazaService;
	@Autowired
	private NaukaService naukaService;
	@Autowired
	private ZdanieService zdanieService;
	
	@RequestMapping("/nauka")
	public String nauka(Model model) {
		Long count = bazaService.count();
		model.addAttribute("count", count);
		return "nauka";
	}
	//----------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------	
	//Prezentacja-----------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------
	
	@RequestMapping(value="/prezentacja/poczatek", method= RequestMethod.POST)
	public String prezentacjaPoczatek(@RequestParam(value="numberOfWords") int numberOfWords) {
		List<Slowo> listSlowo = new ArrayList<>();
	 	listSlowo = bazaService.getByNumber(numberOfWords);
	 	
	 	naukaService.deleteNaukaList();
	 	naukaService.saveNaukaList(listSlowo);	
	 	return "redirect:/prezentacja";
	}
	
	@RequestMapping("/prezentacja")
	public ModelAndView prezentacja(){
			int numberOfWords = naukaService.list().size();
			Long id = naukaService.getMinId();

			ModelAndView mav = new ModelAndView("prezentacja");
	
			Slowo sl = bazaService.get(id);
			List<Zdanie> listZdanie = zdanieService.listByIdSlowa(id);
			Collections.shuffle(listZdanie);
			Zdanie zdanie = new Zdanie();
			
		 	if(!listZdanie.isEmpty()) {
		 		zdanie = listZdanie.get(0);
		 	};
			
		 	mav.addObject("sl",sl);
		 	mav.addObject("zdanie", zdanie);
		 	mav.addObject("numberOfWords", numberOfWords);
		 	mav.addObject("id", id);
		
			return mav;
	}
	@RequestMapping(value = "prezentacja/nastepny", method = RequestMethod.POST)
	public String prezentacjaNastepny(@RequestParam(value= "id")Long id) {
		Nauka nauka = naukaService.get(id);
		
		nauka.setCzy_umiem(true);
		nauka.setWspolczynnik_powtorek(1.);
		naukaService.save(nauka);
		
		return "redirect:/prezentacja";
	}
	@RequestMapping("/prezentacja/przerwij")
	public String prezentacjaPrzerwij() {
		naukaService.deleteNaukaList();
		return "redirect:/nauka";
	}
	
	@RequestMapping("/prezentacja/koniec")
	public String prezentacjaKoniec() {
		List<Nauka> naukaList = new ArrayList<>();
		naukaList = naukaService.list();
		
		naukaService.setAllCzyUmiemToFalse(naukaList);
		
		return "redirect:/wybierz_tlumaczenie_angielskie";
	}
	
	//----------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------	
	//Wybierz tlumaczenie angielskie-----------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------
		
	//this is only made for faster testing
	@RequestMapping("/wybierz_tlumaczenie_angielskie/poczatek")
	public String wybierzTlumaczenieAngielskiePoczatek() {
		List<Slowo> listSlowo = new ArrayList<>();
	 	listSlowo = bazaService.getByNumber(5);
	 	
	 	
	 	
	 	naukaService.deleteNaukaList();
	 	naukaService.saveNaukaList(listSlowo);
	 	
	 	List<Nauka> naukaList = new ArrayList<>();
	 	naukaList = naukaService.list();
	 	
	 	naukaService.setAllWspolczynnikToOne(naukaList);
	 	return "redirect:/wybierz_tlumaczenie_angielskie";
	}
	
	@RequestMapping("/wybierz_tlumaczenie_angielskie")
	public ModelAndView wybierzTlumaczenieAngielskie() {
		//Selecting word with the smallest wspolczynnik powtorek and czy_umiem as false
			Double minWspolczynnik = naukaService.getMinWspolczynnik();
			int nauczone = naukaService.countCzyUmiem();
			Long id = naukaService.getMinIdAndWspolczynnik(minWspolczynnik);

			int count = naukaService.list().size();
			ModelAndView mav = new ModelAndView("wybierz_tlumaczenie_angielskie");
		 	Nauka nauka = new Nauka();
		 	nauka = naukaService.get(id);
		 	
		 	Slowo sl = new Slowo();
		 	sl = bazaService.get(id);

		 	//Creating list of 4 words for quiz
		 	List<Slowo> listLikeSlowo = new ArrayList<>();
		 	listLikeSlowo = bazaService.searchWordsLike(sl.getTlumaczenie());

		 	listLikeSlowo.add(sl);

		 	Collections.shuffle(listLikeSlowo);

		 	Slowo pierwszy = listLikeSlowo.get(0);
		 	Slowo drugi = listLikeSlowo.get(1);
		 	Slowo trzeci = listLikeSlowo.get(2);
		 	Slowo czwarty = listLikeSlowo.get(3);

		 	List<Zdanie> listZdanie = zdanieService.listByIdSlowa(id);
			Collections.shuffle(listZdanie);
			Zdanie zdanie = new Zdanie();
		 	if(!listZdanie.isEmpty()) {
		 		zdanie = listZdanie.get(0);
		 	};
		 	
		 	mav.addObject("pierwszy", pierwszy);
		 	mav.addObject("drugi", drugi);
		 	mav.addObject("trzeci", trzeci);
		 	mav.addObject("czwarty", czwarty);
		 	mav.addObject("sl", sl);
		 	mav.addObject("nauka", nauka);
		 	mav.addObject("id", id);
		 	mav.addObject("count", count);
		 	mav.addObject("listLikeSlowo", listLikeSlowo);
		 	mav.addObject("nauczone", nauczone);
		 	mav.addObject("zdanie", zdanie);
			return mav;
	}
	
	@RequestMapping(value = "/wybierz_tlumaczenie_angielskie/nastepny", method = RequestMethod.POST)
	public String wybierzTlumaczenieAngielskieNastepny(@RequestParam(value = "answer") int answer,
														@RequestParam(value= "id")Long id) {
		Nauka nauka = naukaService.get(id);
		if(answer == 1) {
			nauka.setCzy_umiem(true);
		}else {
			nauka.setWspolczynnik_powtorek(nauka.getWspolczynnik_powtorek()+ 0.01);
		}
		naukaService.save(nauka);
		return "redirect:/wybierz_tlumaczenie_angielskie";
	}
	@RequestMapping("/wybierz_tlumaczenie_angielskie/koniec")
	public String wybierzTlumaczenieAnieglskieKoniec() {
		List<Nauka> naukaList = new ArrayList<>();
		naukaList = naukaService.list();
		
		naukaService.setAllCzyUmiemToFalse(naukaList);
		
		return "redirect:/wybierz_tlumaczenie_polskie";
	}
	//----------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------	
	//Wybierz tlumaczenie polskie-----------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------
		
	//this is only made for faster testing
	@RequestMapping("/wybierz_tlumaczenie_polskie/poczatek")
	public String wybierzTlumaczeniePolskiePoczatek() {
		List<Slowo> listSlowo = new ArrayList<>();
	 	listSlowo = bazaService.getByNumber(5);
	 	
	 	
	 	
	 	naukaService.deleteNaukaList();
	 	naukaService.saveNaukaList(listSlowo);
	 	
	 	List<Nauka> naukaList = new ArrayList<>();
	 	naukaList = naukaService.list();
	 	
	 	naukaService.setAllWspolczynnikToOne(naukaList);
	 	return "redirect:/wybierz_tlumaczenie_polskie";
	}
	
	@RequestMapping("/wybierz_tlumaczenie_polskie")
	public ModelAndView wybierzTlumaczeniePolskie() {
		//Selecting word with the smallest wspolczynnik powtorek and czy_umiem as false
			Double minWspolczynnik = naukaService.getMinWspolczynnik();
			int nauczone = naukaService.countCzyUmiem();
			Long id = naukaService.getMinIdAndWspolczynnik(minWspolczynnik);

			int count = naukaService.list().size();
			ModelAndView mav = new ModelAndView("wybierz_tlumaczenie_polskie");
		 	Nauka nauka = new Nauka();
		 	nauka = naukaService.get(id);
		 	
		 	Slowo sl = new Slowo();
		 	sl = bazaService.get(id);

		 	//Creating list of 4 words for quiz
		 	List<Slowo> listLikeSlowo = new ArrayList<>();
		 	listLikeSlowo = bazaService.searchWordsLike(sl.getSlowo());

		 	listLikeSlowo.add(sl);

		 	Collections.shuffle(listLikeSlowo);

		 	Slowo pierwszy = listLikeSlowo.get(0);
		 	Slowo drugi = listLikeSlowo.get(1);
		 	Slowo trzeci = listLikeSlowo.get(2);
		 	Slowo czwarty = listLikeSlowo.get(3);

		 	
		 	List<Zdanie> listZdanie = zdanieService.listByIdSlowa(id);
			Collections.shuffle(listZdanie);
			Zdanie zdanie = new Zdanie();
		 	if(!listZdanie.isEmpty()) {
		 		zdanie = listZdanie.get(0);
		 	};
			
		 	mav.addObject("pierwszy", pierwszy);
		 	mav.addObject("drugi", drugi);
		 	mav.addObject("trzeci", trzeci);
		 	mav.addObject("czwarty", czwarty);
		 	mav.addObject("sl", sl);
		 	mav.addObject("nauka", nauka);
		 	mav.addObject("id", id);
		 	mav.addObject("count", count);
		 	mav.addObject("listLikeSlowo", listLikeSlowo);
		 	mav.addObject("nauczone", nauczone);
		 	mav.addObject("zdanie", zdanie);
			return mav;
	}
	@RequestMapping(value = "/wybierz_tlumaczenie_polskie/nastepny", method = RequestMethod.POST)
	public String wybierzTlumaczeniePolskieNastepny(@RequestParam(value = "answer") int answer,
														@RequestParam(value= "id")Long id) {
		
		Nauka nauka = naukaService.get(id);
		
		if(answer == 1) {
			nauka.setCzy_umiem(true);
		}else {
			nauka.setWspolczynnik_powtorek(nauka.getWspolczynnik_powtorek()+ 0.01);
		}
		
		naukaService.save(nauka);
		
		return "redirect:/wybierz_tlumaczenie_polskie";
	}
	
	@RequestMapping("/wybierz_tlumaczenie_polskie/koniec")
	public String wybierzTlumaczeniePolskieKoniec() {
		List<Nauka> naukaList = new ArrayList<>();
		naukaList = naukaService.list();
		
		naukaService.setAllCzyUmiemToFalse(naukaList);
		
		return "redirect:/rozpoznawanie_ze_sluchu";
	}
	
	//----------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------	
	//Rozpoznawanie ze sluchu-----------------------------------------------------------------
	//----------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------	
	@RequestMapping("/rozpoznawanie_ze_sluchu/poczatek")
	public String rozpoznawanieZeSluchuPoczatek() {
		List<Slowo> listSlowo = new ArrayList<>();
	 	listSlowo = bazaService.getByNumber(5);
	 	
	 	
	 	
	 	naukaService.deleteNaukaList();
	 	naukaService.saveNaukaList(listSlowo);
	 	
	 	List<Nauka> naukaList = new ArrayList<>();
	 	naukaList = naukaService.list();
	 	
	 	naukaService.setAllWspolczynnikToOne(naukaList);
	 	return "redirect:/rozpoznawanie_ze_sluchu";
	}
	
	@RequestMapping("/rozpoznawanie_ze_sluchu")
	public ModelAndView rozpoznawanieZeSluchu() {
		//Selecting word with the smallest wspolczynnik powtorek and czy_umiem as false
			Double minWspolczynnik = naukaService.getMinWspolczynnik();
			int nauczone = naukaService.countCzyUmiem();
			Long id = naukaService.getMinIdAndWspolczynnik(minWspolczynnik);

			int count = naukaService.list().size();
			ModelAndView mav = new ModelAndView("rozpoznawanie_ze_sluchu");
		 	Nauka nauka = new Nauka();
		 	nauka = naukaService.get(id);
		 	
		 	Slowo sl = new Slowo();
		 	sl = bazaService.get(id);

		 	//Creating list of 4 words for quiz
		 	List<Slowo> listLikeSlowo = new ArrayList<>();
		 	listLikeSlowo = bazaService.searchWordsLike(sl.getSlowo());

		 	listLikeSlowo.add(sl);

		 	Collections.shuffle(listLikeSlowo);

		 	Slowo pierwszy = listLikeSlowo.get(0);
		 	Slowo drugi = listLikeSlowo.get(1);
		 	Slowo trzeci = listLikeSlowo.get(2);
		 	Slowo czwarty = listLikeSlowo.get(3);

		 	List<Zdanie> listZdanie = zdanieService.listByIdSlowa(id);
			Collections.shuffle(listZdanie);
			Zdanie zdanie = new Zdanie();
		 	if(!listZdanie.isEmpty()) {
		 		zdanie = listZdanie.get(0);
		 	};
		 	
		 	mav.addObject("pierwszy", pierwszy);
		 	mav.addObject("drugi", drugi);
		 	mav.addObject("trzeci", trzeci);
		 	mav.addObject("czwarty", czwarty);
		 	mav.addObject("sl", sl);
		 	mav.addObject("nauka", nauka);
		 	mav.addObject("id", id);
		 	mav.addObject("count", count);
		 	mav.addObject("listLikeSlowo", listLikeSlowo);
		 	mav.addObject("nauczone", nauczone);
		 	mav.addObject("zdanie", zdanie);
			return mav;
	}
	@RequestMapping(value = "/rozpoznawanie_ze_sluchu/nastepny", method = RequestMethod.POST)
	public String rozpoznawanieZeSluchuNastepny(@RequestParam(value = "answer") int answer,
														@RequestParam(value= "id")Long id) {
		Nauka nauka = naukaService.get(id);
		if(answer == 1) {
			nauka.setCzy_umiem(true);
		}else {
			nauka.setWspolczynnik_powtorek(nauka.getWspolczynnik_powtorek()+ 0.01);
		}
		naukaService.save(nauka);
		return "redirect:/rozpoznawanie_ze_sluchu";
	}
	@RequestMapping("/rozpoznawanie_ze_sluchu/koniec")
	public String rozpoznawanieZeSluchuKoniec() {
		List<Nauka> naukaList = new ArrayList<>();
		naukaList = naukaService.list();
		
		naukaService.setAllCzyUmiemToFalse(naukaList);
		
		return "redirect:/nauka";
	}
	
		//----------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------	
		//Dyktando--------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------	
	
	@RequestMapping("/dyktando/poczatek")
	public String dyktandoPoczatek() {
		List<Slowo> listSlowo = new ArrayList<>();
	 	listSlowo = bazaService.getByNumber(5);
	 	
	 	
	 	
	 	naukaService.deleteNaukaList();
	 	naukaService.saveNaukaList(listSlowo);
	 	
	 	List<Nauka> naukaList = new ArrayList<>();
	 	naukaList = naukaService.list();
	 	
	 	naukaService.setAllWspolczynnikToOne(naukaList);
	 	return "redirect:/dyktando";
	}
	
	@RequestMapping("/dyktando")
	public ModelAndView dyktando() {
			//Selecting word with the smallest wspolczynnik powtorek and czy_umiem as false
			Double minWspolczynnik = naukaService.getMinWspolczynnik();
			int nauczone = naukaService.countCzyUmiem();
			Long id = naukaService.getMinIdAndWspolczynnik(minWspolczynnik);
	
			int count = naukaService.list().size();
			ModelAndView mav = new ModelAndView("dyktando");
		 	Nauka nauka = new Nauka();
		 	nauka = naukaService.get(id);
		 	
		 	Slowo sl = new Slowo();
		 	sl = bazaService.get(id);

		 	List<Zdanie> listZdanie = zdanieService.listByIdSlowa(id);
			Collections.shuffle(listZdanie);
			Zdanie zdanie = new Zdanie();
		 	if(!listZdanie.isEmpty()) {
		 		zdanie = listZdanie.get(0);
		 	};
		 	
			mav.addObject("zdanie", zdanie);
		 	mav.addObject("sl", sl);
		 	mav.addObject("nauka", nauka);
		 	mav.addObject("id", id);
		 	mav.addObject("count", count);
		 	mav.addObject("nauczone", nauczone);
			return mav;
	}
	@RequestMapping(value = "/dyktando/nastepny", method = RequestMethod.POST)
	public String dyktandoNastepny(@RequestParam(value = "answer") int answer,
														@RequestParam(value= "id")Long id) {
		Nauka nauka = naukaService.get(id);
		if(answer == 1) {
			nauka.setCzy_umiem(true);
		}else {
			nauka.setWspolczynnik_powtorek(nauka.getWspolczynnik_powtorek()+ 0.01);
		}
		naukaService.save(nauka);
		return "redirect:/dyktando";
	}
	@RequestMapping("/dyktando/koniec")
	public String dyktandoKoniec() {
		List<Nauka> naukaList = new ArrayList<>();
		naukaList = naukaService.list();
		
		naukaService.setAllCzyUmiemToFalse(naukaList);
		
		return "redirect:/wpisz_tlumaczenie";
	}
			//----------------------------------------------------------------------------------------
			//----------------------------------------------------------------------------------------	
			//Wpisz tlumaczenie-----------------------------------------------------------------------
			//----------------------------------------------------------------------------------------
			//----------------------------------------------------------------------------------------	
	@RequestMapping("/wpisz_tlumaczenie/poczatek")
	public String wpiszTlumaczeniePoczatek() {
		List<Slowo> listSlowo = new ArrayList<>();
	 	listSlowo = bazaService.getByNumber(5);
	 	
	 	
	 	
	 	naukaService.deleteNaukaList();
	 	naukaService.saveNaukaList(listSlowo);
	 	
	 	List<Nauka> naukaList = new ArrayList<>();
	 	naukaList = naukaService.list();
	 	
	 	naukaService.setAllWspolczynnikToOne(naukaList);
	 	return "redirect:/wpisz_tlumaczenie";
	}
	
	@RequestMapping("/wpisz_tlumaczenie")
	public ModelAndView wpiszTlumaczenie() {
			//Selecting word with the smallest wspolczynnik powtorek and czy_umiem as false
			Double minWspolczynnik = naukaService.getMinWspolczynnik();
			int nauczone = naukaService.countCzyUmiem();
			Long id = naukaService.getMinIdAndWspolczynnik(minWspolczynnik);
	
			int count = naukaService.list().size();
			ModelAndView mav = new ModelAndView("wpisz_tlumaczenie");
		 	Nauka nauka = new Nauka();
		 	nauka = naukaService.get(id);
		 	
		 	Slowo sl = new Slowo();
		 	sl = bazaService.get(id);
		 	List<Zdanie> listZdanie = zdanieService.listByIdSlowa(id);

			Collections.shuffle(listZdanie);

			Zdanie zdanie = new Zdanie();
		 	if(!listZdanie.isEmpty()) {
		 		zdanie = listZdanie.get(0);
		 	};

			
		 	mav.addObject("zdanie", zdanie);
		 	mav.addObject("sl", sl);
		 	mav.addObject("nauka", nauka);
		 	mav.addObject("id", id);
		 	mav.addObject("count", count);
		 	mav.addObject("nauczone", nauczone);
			return mav;
	}
	@RequestMapping(value = "/wpisz_tlumaczenie/nastepny", method = RequestMethod.POST)
	public String wpiszTlumaczenieNastepny(@RequestParam(value = "answer") int answer,
														@RequestParam(value= "id")Long id) {
		Nauka nauka = naukaService.get(id);
		if(answer == 1) {
			nauka.setCzy_umiem(true);
		}else {
			nauka.setWspolczynnik_powtorek(nauka.getWspolczynnik_powtorek()+ 0.01);
		}
		naukaService.save(nauka);
		return "redirect:/wpisz_tlumaczenie";
	}
	@RequestMapping("/wpisz_tlumaczenie/koniec")
	public String wpiszTlumaczenieKoniec() {
		List<Nauka> naukaList = new ArrayList<>();
		naukaList = naukaService.list();
		
		naukaService.setAllCzyUmiemToFalse(naukaList);
		
		return "redirect:/nauka";
	}
}
