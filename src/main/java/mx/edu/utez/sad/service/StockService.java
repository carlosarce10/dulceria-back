package mx.edu.utez.sad.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.ProductEntity;
import mx.edu.utez.sad.entity.StockEntity;
import mx.edu.utez.sad.repository.StockRepository;

@Service
@Transactional
public class StockService {
	@Autowired
	private StockRepository stockRepository;
	
	public List<StockEntity> getAll(){
		return stockRepository.findAllByOrderByIdDesc();
	}
	
	public StockEntity findById(long id) {
		Optional<StockEntity> stock = stockRepository.findById(id);
		return stock.isPresent()?stock.get():new StockEntity();
	}
	
	public StockEntity save(StockEntity stock) {
		return stockRepository.save(stock); 
	}
	
	public boolean delete(long id) {
		stockRepository.deleteById(id);
		return !stockRepository.existsById(id);
	}
	
	public List<StockEntity> findByExpiredStock(){
		return stockRepository.findByExpiredStockOrderByIdDesc();
	}
	
	public List<StockEntity> findByNotExpiredStock(){
		return stockRepository.findByNotExpiredStockOrderByDateExpire();
	}
	
	public List<StockEntity> findByProductOrderByDateExpireAsc(ProductEntity product){
		return stockRepository.findByProductAndDateExpireAfterOrderByDateExpireAsc(product, new Date(System.currentTimeMillis()));
	}
}
