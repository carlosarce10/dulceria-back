package mx.edu.utez.sad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.sad.entity.CashboxEntity;
import mx.edu.utez.sad.entity.UserEntity;

@Repository
public interface CashboxRepository extends JpaRepository<CashboxEntity, Long>{

	public List<CashboxEntity> findByUserOrderByIdDesc(UserEntity user);
	
	public List<CashboxEntity> findAllByOrderByIdDesc();
}
