package edu.cc.chat03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 在线聊天室：服务端
 * 多个客户收发多条消息
 * 出现的问题其他客户必须等待之前的用户退出才能使用，排队
 *解决：使用多线程
 *封装利于维护
 * @author chunchun
 *@date 2020年6月15日
 * @projectname chatsocket_demo
 */
public class TMultiChat {
	public static void main(String[] args) throws IOException {
		System.out.println("-----server----");
		//指定端口 使用ServerSocket 创建服务器
		ServerSocket server = new ServerSocket(8888);
		//阻塞式等待连接accept
		while(true) {
		Socket client = server.accept();
		System.out.println("一个客户端建立连接");
		new Thread(new Channel(client)).start();
		}
	}
	//一个客户一个Channel
	static 	class  Channel implements Runnable{
		private DataInputStream dis= null;
		private DataOutputStream dos= null;
		private Socket client;
	    private boolean isRuning;
		public Channel(Socket client) {
			this.client = client;
			try {
				dis = new DataInputStream(client.getInputStream());
			    dos = new DataOutputStream(client.getOutputStream());
			    isRuning =true;
			} catch (IOException e) {
				System.out.println("----1-----");
				release();
			}
		}
		//接收消息
		private String receive(){
			String msg ="";
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				System.out.println("----2-----");
				release();//出异常就释放资源
			}
			return msg;
		}
		//发送消息
		private void send(String msg) {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				System.out.println("----3-----");
				release();
			}
		}
		//释放资源
		private void release() {
			this.isRuning = false;
			Cutils.close(dis,dos,client);
		}
		@Override
		public void run() {
			while(isRuning) {
				String msg =receive();
				if(!msg.equals("")) {
					send(msg);
				}
			}
		}
	}

}
