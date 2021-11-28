package models.message.entity;

import java.time.LocalDateTime;

import models.User;
import models.message.Message;
import models.message.MessageType;

public class MediaMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6324550317595557579L;
	
 	private byte[] content;
 	
	public MediaMessage(LocalDateTime time, User sender, User receiver) {
		super(time, sender, receiver, MessageType.VIDEO);
	}

}
