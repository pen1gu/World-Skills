package classtest;

public class CallBack {
	public static void main(String[] args) {
		Callee callee = new Callee();
		callee.setCallback(new Callee.CallBack() {
			
			@Override
			public void onGetMessage(Callee callee) {
				//callback
				System.out.println("입력받은 메시지 >" + callee.getMsg());
			}
		});
		
		for(int i=0; i<5; i++){ //메시지 발송을 5번까지 보낸다
			callee.onInputMessage();
		}
	}
}
