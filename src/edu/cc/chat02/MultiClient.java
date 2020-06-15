package edu.cc.chat02;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 在线聊天室：客户端
 *多个客户收发多条信息
 *出现的问题其他客户必须等待之前的用户退出才能使用，排队
 * @author chunchun
 *@date 2020年6月15日
 * @projectname chatsocket_demo
 */
public class MultiClient {
	public static void main(String[] args) throws IOException {
		System.out.println("----client----");
		//建立连接，使用SOcket创建客户端+服务器的地址和端口
		Socket client =new Socket("localhost",8888);
		//客户端发送消息
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		DataInputStream dis = new DataInputStream(client.getInputStream());
		boolean isRuning = true;
		while(isRuning) {
			String msg = console.readLine();
			dos.writeUTF(msg);
			dos.flush();
			//获取消息
			 msg = dis.readUTF();
			 System.out.println(msg);
		}
		 //释放资源
		 dos.close();
		 dis.close();
		 client.close();
	}

}
