package mx.edu.utez.sad.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.ProductEntity;
import mx.edu.utez.sad.entity.StockEntity;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long>{

	public List<StockEntity> findByProductAndDateExpireAfterOrderByDateExpireAsc(ProductEntity product,Date date);
	
	@Query("SELECT s FROM StockEntity s WHERE date_format(date(s.dateExpire),'%Y-%m-%d')  < now()")
	public List<StockEntity> findByExpiredStockOrderByIdDesc();
	
	@Query("SELECT s FROM StockEntity s WHERE date_format(date(s.dateExpire),'%Y-%m-%d')  > now()")
	public List<StockEntity> findByNotExpiredStockOrderByDateExpire();
	
	@Query("SELECT s FROM StockEntity s WHERE date_format(date(s.dateExpire),'%Y-%m-%d')  > now()")
	public List<StockEntity> findAllByOrderByIdDesc();
}
