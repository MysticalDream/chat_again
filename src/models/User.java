package models;

import java.time.LocalDate;
import java.util.ArrayList;

public class User implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4975803851556329276L;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 好友列表
	 */
	private ArrayList<User> friendList;
	/**
	 * 性别 1男 2女 粗糙的解决
	 */
	private Boolean sex;
	/**
	 * 出生日期
	 */
	private LocalDate birthday;
	/**
	 * 头像图片路径
	 */
	private String headImagePath;
	/**
	 * 账号申请日期
	 */
	private LocalDate applyDate;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 状态
	 */
	private Boolean status;

	/**
	 * 无参构造器
	 */
	public User() {
		this(null, null, null, null, null, null, null, null, null);
	}

	public User(String userName, String account, ArrayList<User> friendList, Boolean sex, LocalDate birthday,
			String headImagePath, LocalDate applyDate, String passWord, Boolean status) {
		super();
		this.userName = userName;
		this.account = account;
		this.friendList = friendList;
		this.sex = sex;
		this.birthday = birthday;
		this.headImagePath = headImagePath;
		this.applyDate = applyDate;
		this.passWord = passWord;
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public ArrayList<User> getFriendList() {
		return friendList;
	}

	public void setFriendList(ArrayList<User> friendList) {
		this.friendList = friendList;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getHeadImagePath() {
		return headImagePath;
	}

	public void setHeadImagePath(String headImagePath) {
		this.headImagePath = headImagePath;
	}

	public LocalDate getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(LocalDate applyDate) {
		this.applyDate = applyDate;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
		{
			return false;
			}
		return this.getAccount().equals(((User) obj).getAccount());
	}
}
