package edu.cc.chat05;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.management.loading.PrivateClassLoader;

import com.sun.org.apache.bcel.internal.generic.AllocationInstruction;


/**
 * 在线聊天室：服务端
 *目标：私聊
 *封装利于维护
 * @author chunchun
 *@date 2020年6月15日
 * @projectname chatsocket_demo
 */
public class Chat {
	//private List<Channel> all = new  ArrayList<Channel>();可以使用，但在socket编程使用CopyOnWriteArrayList更好
	private static CopyOnWriteArrayList<Channel> all = new  CopyOnWriteArrayList<Channel>();
	public static void main(String[] args) throws IOException {
		System.out.println("-----server----");
		//指定端口 使用ServerSocket 创建服务器
		ServerSocket server = new ServerSocket(8888);
		//阻塞式等待连接accept
		while(true) {
		Socket client = server.accept();
		System.out.println("一个客户端建立连接");
		Channel c = new Channel(client);
		all.add(c);///管理所有的成员
		//new Thread(new Channel(client)).start();
		new Thread(c).start();
		}
	}
	//一个客户一个Channel
	static 	class  Channel implements Runnable{
		private DataInputStream dis= null;
		private DataOutputStream dos= null;
		private Socket client;
	    private boolean isRuning;
	    private String name;
		public Channel(Socket client) {
			this.client = client;
			try {
				dis = new DataInputStream(client.getInputStream());
			    dos = new DataOutputStream(client.getOutputStream());
			    isRuning =true;
			    //获取名称
			    this.name = receive();
			    //欢迎你的到来
			    this.send("欢迎你的到来");
			    sendOthers(this.name+"来到了春春聊天室",true);
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
		//群聊
		
		/**
		 * @param msg
		 * 获取自己的msg利用容器遍历其他人的Channel调用其他人的send方法
		 * 私聊：约定数据格式：  @xxxx:msg
		 */
		private void sendOthers(String msg,boolean isSys) {
			
			boolean isPrivate = msg.startsWith("@");
			if(isPrivate) {//私聊
				int idx = msg.indexOf(":");
				//获取目标和数据
				String targetName = msg.substring(1,idx);
				msg = msg.substring(idx+1);
				for(Channel other:all) {
					if(other.name.equals(targetName)) {
						other.send(this.name+"悄悄的对你说"+msg);//私聊消息内容
						
					}
				}
				
				
			}else {//群聊
			for(Channel other:all) {
				if(other==this) {
					continue;
				}
				if(!isSys) {
				other.send(this.name+"对所有人说:"+msg);//群聊消息
				}else {
					other.send(msg);//系统消息
			}
			}
			}
		}
		//释放资源
		private void release() {
			this.isRuning = false;
			Cutils.close(dis,dos,client);
			//退出
			all.remove(this);
			sendOthers(this.name+"离开春春聊天室", true);//退出通知
		}
		@Override
		public void run() {
			while(isRuning) {
				String msg =receive();
				if(!msg.equals("")) {
					//send(msg);
					sendOthers(msg,false);
				}
			}
		}
	}

}
