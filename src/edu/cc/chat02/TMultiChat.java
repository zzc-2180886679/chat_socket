package edu.cc.chat02;

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
		//������Ϣ
		
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
			 //�ͷ���Դ
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
