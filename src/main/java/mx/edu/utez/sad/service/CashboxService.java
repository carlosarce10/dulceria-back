package mx.edu.utez.sad.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.CashboxEntity;
import mx.edu.utez.sad.entity.UserEntity;
import mx.edu.utez.sad.repository.CashboxRepository;

@Service
@Transactional
public class CashboxService {
	@Autowired
	private CashboxRepository cbr;
	
	@Autowired 
	private UserService userService;
	
	public List<CashboxEntity> findAll(){
		return cbr.findAllByOrderByIdDesc();
	}
	
	public CashboxEntity findById(long id) {
		Optional<CashboxEntity> cashbox = cbr.findById(id); 
		return cashbox.isPresent()?cashbox.get():new CashboxEntity();
	}
	
	public CashboxEntity save(CashboxEntity cashbox) {
		LocalTime time = LocalTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formattedDate = time.format(myFormatObj);
		
		cashbox.setDate(new Date());
		cashbox.setStartTime(formattedDate);
		cashbox.setAmount(cashbox.getInitialAmount());
		cashbox.setWithdrawal(0.0);
		cashbox.setTotalSales(0);
		return cbr.save(cashbox);
	}
	
	public CashboxEntity update(CashboxEntity cashbox) {
		return cbr.save(cashbox);
	}
	
	public List<CashboxEntity> findByUser(long id){
		UserEntity user = userService.findById(id);
		return cbr.findByUserOrderByIdDesc(user);
	}
	
	public CashboxEntity closeCashBox(CashboxEntity cashbox) {
		LocalTime time = LocalTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formattedDate = time.format(myFormatObj);
		
		cashbox.setEndTime(formattedDate);
		return cbr.save(cashbox);
	}
	
	public boolean makeWithdrawal(CashboxEntity cashbox, double quantity ) {
		double amount = cashbox.getAmount();
		
		if((cashbox.getEndTime() == null || cashbox.getEndTime().equals("")) && amount >= quantity) {			
			double withdrawal = cashbox.getWithdrawal();
			withdrawal += quantity;
			cashbox.setWithdrawal(withdrawal);
			cashbox.setAmount(amount-quantity);
			return true;
		}
		return false;
	}
	
}
