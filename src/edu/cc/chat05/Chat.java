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
 * ���������ң������
 *Ŀ�꣺˽��
 *��װ����ά��
 * @author chunchun
 *@date 2020��6��15��
 * @projectname chatsocket_demo
 */
public class Chat {
	//private List<Channel> all = new  ArrayList<Channel>();����ʹ�ã�����socket���ʹ��CopyOnWriteArrayList����
	private static CopyOnWriteArrayList<Channel> all = new  CopyOnWriteArrayList<Channel>();
	public static void main(String[] args) throws IOException {
		System.out.println("-----server----");
		//ָ���˿� ʹ��ServerSocket ����������
		ServerSocket server = new ServerSocket(8888);
		//����ʽ�ȴ�����accept
		while(true) {
		Socket client = server.accept();
		System.out.println("һ���ͻ��˽�������");
		Channel c = new Channel(client);
		all.add(c);///�������еĳ�Ա
		//new Thread(new Channel(client)).start();
		new Thread(c).start();
		}
	}
	//һ���ͻ�һ��Channel
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
			    //��ȡ����
			    this.name = receive();
			    //��ӭ��ĵ���
			    this.send("��ӭ��ĵ���");
			    sendOthers(this.name+"�����˴���������",true);
			} catch (IOException e) {
				System.out.println("----1-----");
				release();
			}
		}
		//������Ϣ
		private String receive(){
			String msg ="";
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				System.out.println("----2-----");
				release();//���쳣���ͷ���Դ
			}
			return msg;
		}
		//������Ϣ
		private void send(String msg) {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				System.out.println("----3-----");
				release();
			}
		}
		//Ⱥ��
		
		/**
		 * @param msg
		 * ��ȡ�Լ���msg�����������������˵�Channel���������˵�send����
		 * ˽�ģ�Լ�����ݸ�ʽ��  @xxxx:msg
		 */
		private void sendOthers(String msg,boolean isSys) {
			
			boolean isPrivate = msg.startsWith("@");
			if(isPrivate) {//˽��
				int idx = msg.indexOf(":");
				//��ȡĿ�������
				String targetName = msg.substring(1,idx);
				msg = msg.substring(idx+1);
				for(Channel other:all) {
					if(other.name.equals(targetName)) {
						other.send(this.name+"���ĵĶ���˵"+msg);//˽����Ϣ����
						
					}
				}
				
				
			}else {//Ⱥ��
			for(Channel other:all) {
				if(other==this) {
					continue;
				}
				if(!isSys) {
				other.send(this.name+"��������˵:"+msg);//Ⱥ����Ϣ
				}else {
					other.send(msg);//ϵͳ��Ϣ
			}
			}
			}
		}
		//�ͷ���Դ
		private void release() {
			this.isRuning = false;
			Cutils.close(dis,dos,client);
			//�˳�
			all.remove(this);
			sendOthers(this.name+"�뿪����������", true);//�˳�֪ͨ
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
