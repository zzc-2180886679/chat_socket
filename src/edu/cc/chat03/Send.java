package edu.cc.chat03;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 客户端的发送器
 * @author chunchun
 *@date 2020年6月15日
 * @projectname chatsocket_demo
 */
public class Send implements Runnable{
	private BufferedReader console;
	private DataOutputStream dos;
	private Socket client;
	private boolean isRuning;
	public Send(Socket client) {
		this.client = client;
		isRuning =true;
		console = new BufferedReader(new InputStreamReader(System.in));
	    try {
			dos = new DataOutputStream(client.getOutputStream());
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
	//发送消息
			private void send(String msg) {
				try {
					dos.writeUTF(msg);
					dos.flush();
				} catch (IOException e) {
					System.out.println("===3==");
					release();
				}
			}
	//从控制台获取消息
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
