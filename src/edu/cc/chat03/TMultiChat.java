package edu.cc.chat03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * ���������ң������
 * ����ͻ��շ�������Ϣ
 * ���ֵ����������ͻ�����ȴ�֮ǰ���û��˳�����ʹ�ã��Ŷ�
 *�����ʹ�ö��߳�
 *��װ����ά��
 * @author chunchun
 *@date 2020��6��15��
 * @projectname chatsocket_demo
 */
public class TMultiChat {
	public static void main(String[] args) throws IOException {
		System.out.println("-----server----");
		//ָ���˿� ʹ��ServerSocket ����������
		ServerSocket server = new ServerSocket(8888);
		//����ʽ�ȴ�����accept
		while(true) {
		Socket client = server.accept();
		System.out.println("һ���ͻ��˽�������");
		new Thread(new Channel(client)).start();
		}
	}
	//һ���ͻ�һ��Channel
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
		//�ͷ���Դ
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
