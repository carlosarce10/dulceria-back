package mx.edu.utez.sad.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.sad.entity.LogBookEntity;
import mx.edu.utez.sad.repository.LogBookRepository;

@Service
@Transactional
public class LogBookService {
	@Autowired
	private LogBookRepository lbr;
	
	public List<LogBookEntity> findAll(){
		return lbr.findAllByOrderByIdDesc();
	}
	
	public LogBookEntity findById(long id) {
		Optional<LogBookEntity> logbook = lbr.findById(id);
		return logbook.isPresent()?logbook.get():new LogBookEntity();
	}
	
	public LogBookEntity save(LogBookEntity brand) {
		return lbr.save(brand);
	}
}
