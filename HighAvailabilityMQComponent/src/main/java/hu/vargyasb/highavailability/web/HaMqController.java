package hu.vargyasb.highavailability.web;

import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.vargyasb.highavailability.ws.FreeSemestersMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/haMq")
public class HaMqController {

	private final JmsTemplate jmsTemplate;
	
	@PutMapping("/{externalStudentId}")
	public ResponseEntity<Void> getFreeSemestersFroStudent(@PathVariable Integer externalStudentId) {
		FreeSemestersMessage payload = new FreeSemestersMessage(externalStudentId, 0);
		this.jmsTemplate.convertAndSend("freeSemesters", payload);
		
		return ResponseEntity.ok().build();
	}
}
