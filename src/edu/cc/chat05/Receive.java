package edu.cc.chat05;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**�ͻ��˵Ľ�����
 * @author chunchun
 *@date 2020��6��15��
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
	//������Ϣ
			private String receive(){
				String msg ="";
				try {
					msg = dis.readUTF();
				} catch (IOException e) {
					System.out.println("====4===");
					release();//���쳣���ͷ���Դ
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
