package gilko.marcin.memory5.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name= "memo_zdanie")
public class Zdanie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_zdania;
	private String zdanie;
	private String zdanie_tlumaczenie;
	private String dzwiek;
	@ManyToOne(
			fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_slowa", nullable = false)
	private Slowo slowo;
	
	public Zdanie() {}
	public Zdanie(Long id_zdania, String zdanie, String zdanie_tlumaczenie, String dzwiek) {
		this.id_zdania = id_zdania;
		this.zdanie = zdanie;
		this.zdanie_tlumaczenie = zdanie_tlumaczenie;
		this.dzwiek = dzwiek;
	}
	
	public Long getId_zdania() {
		return id_zdania;
	}
	public void setId_zdania(Long id_zdania) {
		this.id_zdania = id_zdania;
	}
	
	public String getZdanie() {
		return zdanie;
	}
	public void setZdanie(String zdanie) {
		this.zdanie = zdanie;
	}
	public String getZdanie_tlumaczenie() {
		return zdanie_tlumaczenie;
	}
	public void setZdanie_tlumaczenie(String zdanie_tlumaczenie) {
		this.zdanie_tlumaczenie = zdanie_tlumaczenie;
	}
	public String getDzwiek() {
		return dzwiek;
	}
	public void setDzwiek(String dzwiek) {
		this.dzwiek = dzwiek;
	}
	public Slowo getSlowo() {
		return slowo;
	}
	public void setSlowo(Slowo slowo) {
		this.slowo = slowo;
	}
}
