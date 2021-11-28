package models.message;

import java.time.LocalDateTime;

import models.User;

/**
 * 抽象信息
 * 
 * @author 18177
 *
 */
public abstract class Message implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7383487658835177463L;
	/**
	 * 消息时间
	 */
	private LocalDateTime time;
	/**
	 * 消息发送者
	 */
	private User sender;
	/**
	 * 消息接受者
	 */
	private User receiver;
	/**
	 * 消息类型
	 */
	private int type;

	public Message(LocalDateTime time, User sender, User receiver, int type) {
		this.time = time;
		this.sender = sender;
		this.receiver = receiver;
		this.type = type;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
