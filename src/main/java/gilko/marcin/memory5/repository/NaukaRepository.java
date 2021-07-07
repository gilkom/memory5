package gilko.marcin.memory5.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gilko.marcin.memory5.model.Nauka;

public interface NaukaRepository extends JpaRepository<Nauka, Long> {

	@Query("Select id_slowa from Slowo where id_slowa not in (select id_slowa from Nauczone)")
	List<Long> getNieNauczoneId();
	
	@Query("Select s from Slowo s where s.id_slowa in (:limitedArray)")
	List<Nauka> getByNumber(Long[] limitedArray);

	@Query("Select min(n.pozycja) from Nauka n where n.czy_umiem = false")
	Long getMinId();
	
	@Query("Select min(n.wspolczynnik_powtorek) from Nauka n where n.czy_umiem = false")
	Double getMinWspolczynnik();
	
	@Query("Select min(n.pozycja) from Nauka n where n.czy_umiem = false and n.wspolczynnik_powtorek = :minWspolczynnik")
	Long getMinIdAndWspolczynnik(Double minWspolczynnik);
	
	@Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM Nauka n WHERE n.czy_umiem = true")
	boolean checkCzyUmiem();
	
	@Query("SELECT count(n) from Nauka n where n.czy_umiem = true")
	int countCzyUmiem();
	
	
}
