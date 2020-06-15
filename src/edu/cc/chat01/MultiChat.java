package edu.cc.chat01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 在线聊天室：服务端
 * 收发多条消息
 * @author chunchun
 *@date 2020年6月15日
 * @projectname chatsocket_demo
 */
public class MultiChat {
	public static void main(String[] args) throws IOException {
		System.out.println("-----server----");
		//指定端口 使用ServerSocket 创建服务器
		ServerSocket server = new ServerSocket(8888);
		//阻塞式等待连接accept
		
		Socket client = server.accept();
		System.out.println("一个客户端建立连接");
		//接收消息
		DataInputStream dis = new DataInputStream(client.getInputStream());
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		boolean isRuning =true;
		while(isRuning) {
		 		String msg = dis.readUTF();
				//返回消息
				dos.writeUTF(msg);
				dos.flush();
		}
		
		 //释放资源
		 dos.close();
		 dis.close();
		 client.close();
	}

}
