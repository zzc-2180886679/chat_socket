package edu.cc.chat04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;




/**
 * 
 *目标：加入容器实现群聊
 * @author chunchun
 *@date 2020年6月15日
 * @projectname chatsocket_demo
 */
public class Client {
	public static void main(String[] args) throws  IOException {
		System.out.println("----client----");
	 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	System.out.println("亲输入用户名");	
	String name =br.readLine();
	 //建立连接，使用SOcket创建客户端+服务器的地址和端口
		Socket client =new Socket("localhost",8888);
		//客户端发送消息
		new Thread(new Send(client,name)).start();
		new Thread(new Receive(client)).start();
	}

}
