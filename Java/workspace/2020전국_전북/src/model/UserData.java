package model;

public class UserData {
	private int userNo;
	private String userName;
	private boolean userLoginStatus;
	
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isUserLoginStatus() {
		return userLoginStatus;
	}
	public void setUserLoginStatus(boolean userLoginStatus) {
		this.userLoginStatus = userLoginStatus;
	}
}
