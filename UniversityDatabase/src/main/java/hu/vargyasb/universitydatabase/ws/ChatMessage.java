package hu.vargyasb.universitydatabase.ws;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {

	private int courseId;
	private String username;
	private String text;
}
