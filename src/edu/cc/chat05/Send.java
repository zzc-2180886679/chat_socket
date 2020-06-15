package edu.cc.chat05;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * �ͻ��˵ķ�����
 * @author chunchun
 *@date 2020��6��15��
 * @projectname chatsocket_demo
 */
public class Send implements Runnable{
	private BufferedReader console;
	private DataOutputStream dos;
	private Socket client;
	private boolean isRuning;
	private String name;
	public Send(Socket client,String name) {
		this.client = client;
		isRuning =true;
		this.name=name;
		console = new BufferedReader(new InputStreamReader(System.in));
	    try {
			dos = new DataOutputStream(client.getOutputStream());
			//��������
			send(name);
		} catch (IOException e) {
			System.out.println("===1==");
			this.release();
		}
	}
	@Override
	public void run() {
		System.out.println(isRuning);
		while(isRuning) {
			String msg = getStrFromConsole();
			if(!msg.equals("")) {
				send(msg);
			}
		}
	}
	//������Ϣ
			private void send(String msg) {
				try {
					dos.writeUTF(msg);
					dos.flush();
				} catch (IOException e) {
					System.out.println("===3==");
					release();
				}
			}
	//�ӿ���̨��ȡ��Ϣ
	private String getStrFromConsole() {
		try {
			return console.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	private void release() {
		this.isRuning = false;
		Cutils.close(dos,client);
	}
}
