package hu.vargyasb.universitydatabase.finance.ws;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositMessage {

	private int studentId;
	private int deposit;
	private OffsetDateTime timestamp;
}
