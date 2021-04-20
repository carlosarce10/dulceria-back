package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.CashboxEntity;
import mx.edu.utez.sad.entity.SalesEntity;

@Repository
public interface SalesRepository extends JpaRepository<SalesEntity, Long>{
	
	List<SalesEntity> findAllByOrderByIdDesc();
	
	List<SalesEntity> findByCashboxOrderByIdDesc(CashboxEntity cashbox);

	@Query("SELECT s FROM SalesEntity s WHERE DATE(s.date) = CURDATE()")
	List<SalesEntity> findBySalesOfTheDay();

}
