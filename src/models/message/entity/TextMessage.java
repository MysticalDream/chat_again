package models.message.entity;

import java.time.LocalDateTime;

import models.User;
import models.message.Message;
import models.message.MessageType;

public class TextMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2990959573685494649L;
	/**
	 * 消息内容
	 */
	private String content;

	public TextMessage(String content, LocalDateTime time, User sender, User receiver) {
		super(time, sender, receiver, MessageType.TEXT);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
