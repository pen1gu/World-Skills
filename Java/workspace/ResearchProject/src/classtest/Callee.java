package classtest;

import java.util.Scanner;

public class Callee {
	private String msg;
	private CallBack callback;
	
	@FunctionalInterface
	public interface CallBack{
		public void onGetMessage(Callee callee);
	}
	
	public Callee() {
		this.msg = "";
		this.callback = null;
	}

	public String getMsg() {
		return msg;
	}

	public void setCallback(CallBack callback) {
		this.callback = callback;
	}
	
	public void onInputMessage() {
		Scanner scanner = new Scanner(System.in);
		this.msg = ""; //초기화
		System.out.print("메시지를 입력하세요 : ");
		this.msg = scanner.nextLine();
		
		if(this.callback != null) { //callback처리
			this.callback.onGetMessage(Callee.this);
		}
	}
}
