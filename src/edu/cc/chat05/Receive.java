package edu.cc.chat05;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**客户端的接收器
 * @author chunchun
 *@date 2020年6月15日
 * @projectname chatsocket_demo
 */
public class Receive implements Runnable{
	private  DataInputStream dis ;
	private Socket client;
	private boolean isRuning;
	public Receive(Socket client) {
		isRuning = true;
		this.client = client;
		try {
			dis = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			System.out.println("===2===");
			release();
		}
	}
	//接收消息
			private String receive(){
				String msg ="";
				try {
					msg = dis.readUTF();
				} catch (IOException e) {
					System.out.println("====4===");
					release();//出异常就释放资源
				}
				return msg;
			}
	@Override
	public void run() {
		while(isRuning) {
			String msg = receive();
			if(!msg.equals("")) {
				System.out.println(msg);
			}
		}
		
	}
	private void release() {
		this.isRuning = false;
		Cutils.close(dis,client);
	}

}
