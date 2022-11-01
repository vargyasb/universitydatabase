package hu.vargyasb.universitydatabase.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatroomController {

	private final SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping("/chat")
	public void send(ChatMessage message) {
		messagingTemplate.convertAndSend("/topic/courseChat/" + message.getCourseId(),
					String.format("%s: %s", message.getUsername(), message.getText()));
	}
}
