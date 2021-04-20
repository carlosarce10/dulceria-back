package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.CashboxEntity;
import mx.edu.utez.sad.entity.SalesEntity;
import mx.edu.utez.sad.repository.SalesRepository;

@Service
@Transactional
public class SalesService {
	@Autowired
	private SalesRepository salesRepository;
	
	public List<SalesEntity> getAll(){
		return salesRepository.findAllByOrderByIdDesc();
	}
	
	public SalesEntity findById(long id) {
		Optional<SalesEntity> sales = salesRepository.findById(id);
		return sales.isPresent()?sales.get():new SalesEntity();
	}
	
	public SalesEntity save(SalesEntity sales) {
		return salesRepository.save(sales);
	}
	
	public List<SalesEntity> findByCashbox(CashboxEntity cashbox){
		return salesRepository.findByCashboxOrderByIdDesc(cashbox);
	}
	
	public List<SalesEntity> salesOfTheDay(){
		return salesRepository.findBySalesOfTheDay();
	}
}
