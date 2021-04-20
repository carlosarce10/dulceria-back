package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.SalesDetailsEntity;
import mx.edu.utez.sad.entity.SalesEntity;
import mx.edu.utez.sad.repository.SalesDetailsRepository;

@Service
@Transactional
public class SalesDetailsService {
	@Autowired
	private SalesDetailsRepository salesDetailsRepository;
	
	public List<SalesDetailsEntity> getAll(){
		return salesDetailsRepository.findAllByOrderByIdDesc();
	}
	
	public SalesDetailsEntity getOne(long id) {
		Optional<SalesDetailsEntity> salesDetails = salesDetailsRepository.findById(id);
		return salesDetails.isPresent()?salesDetails.get():new SalesDetailsEntity();
	}
	
	public SalesDetailsEntity save(SalesDetailsEntity sales) {
		return salesDetailsRepository.save(sales);
	}
	
	public List<SalesDetailsEntity> findBySale(SalesEntity sales){
		return salesDetailsRepository.findBySalesOrderByIdDesc(sales);
	}
}
