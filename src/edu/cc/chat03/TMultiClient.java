package edu.cc.chat03;

import java.io.IOException;
import java.net.Socket;




/**
 * 在线聊天室：客户端
 *多个客户收发多条信息
 *出现的问题其他客户必须等待之前的用户退出才能使用，排队
 *解决：使用多线程
 *封装利于维护
 * @author chunchun
 *@date 2020年6月15日
 * @projectname chatsocket_demo
 */
public class TMultiClient {
	public static void main(String[] args) throws  IOException {
		System.out.println("----client----");
		//建立连接，使用SOcket创建客户端+服务器的地址和端口
		Socket client =new Socket("localhost",8888);
		//客户端发送消息
		new Thread(new Send(client)).start();
		new Thread(new Receive(client)).start();
	}

}
