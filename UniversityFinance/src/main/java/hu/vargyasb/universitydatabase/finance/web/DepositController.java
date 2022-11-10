package hu.vargyasb.universitydatabase.finance.web;

import java.time.OffsetDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.vargyasb.universitydatabase.ws.DepositMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/deposits")
public class DepositController {

	private final JmsTemplate jmsTemplate;
	
	@PutMapping("/{studentId}/amount/{deposit}")
	public ResponseEntity<Void> makeDeposit(@PathVariable Integer studentId, @PathVariable Integer deposit) {
		DepositMessage payload = new DepositMessage(studentId, deposit, OffsetDateTime.now());
		this.jmsTemplate.convertAndSend("deposits", payload);

		return ResponseEntity.ok().build();
	}
}
