package edu.cc.chat01;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 在线聊天室：客户端
 * 实现一个客户可以正常收发信息
 * @author chunchun
 *@date 2020年6月15日
 * @projectname chatsocket_demo
 */
public class Client {
	public static void main(String[] args) throws IOException {
		System.out.println("----client----");
		//建立连接，使用SOcket创建客户端+服务器的地址和端口
		Socket client =new Socket("localhost",8888);
		//客户端发送消息
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		String msg = console.readLine();
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		dos.writeUTF(msg);
		dos.flush();
		//获取消息
		DataInputStream dis = new DataInputStream(client.getInputStream());
		 msg = dis.readUTF();
		 System.out.println(msg);
		 
		 //释放资源
		 dos.close();
		 dis.close();
		 client.close();
	}

}
