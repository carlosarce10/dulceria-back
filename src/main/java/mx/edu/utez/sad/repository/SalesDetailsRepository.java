package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.SalesDetailsEntity;
import mx.edu.utez.sad.entity.SalesEntity;

@Repository
public interface SalesDetailsRepository extends JpaRepository<SalesDetailsEntity, Long>{
	public List<SalesDetailsEntity> findBySalesOrderByIdDesc(SalesEntity sales);
	
	public List<SalesDetailsEntity> findAllByOrderByIdDesc();
	
	
}
