package edu.cc.chat02;

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
		//接收消息
		
		new Thread(()-> {
			DataInputStream dis =null;
			DataOutputStream dos =null;
			try {
				dis = new DataInputStream(client.getInputStream());
			    dos = new DataOutputStream(client.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			boolean isRuning =true;
			while(isRuning) {
			 		String msg;
					try {
						msg = dis.readUTF();
						dos.writeUTF(msg);
						dos.flush();
						
					} catch (IOException e) {
						e.printStackTrace();
						isRuning = false;
					}
					
			}
			 //释放资源
			 try {
				 if(null==dos) {
				dos.close();
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				 if(null==dis) {
					 dis.close();
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				 if(null==client) {
					 client.close();
				 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start(); 
		}
	}

}
