package edu.cc.chat01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ���������ң������
 * @author chunchun
 *@date 2020��6��15��
 * @projectname chatsocket_demo
 */
public class Chat {
	public static void main(String[] args) throws IOException {
		System.out.println("-----server----");
		//ָ���˿� ʹ��ServerSocket ����������
		ServerSocket server = new ServerSocket(8888);
		//����ʽ�ȴ�����accept
		Socket client = server.accept();
		System.out.println("һ���ͻ��˽�������");
		//������Ϣ
		DataInputStream dis = new DataInputStream(client.getInputStream());
		String msg = dis.readUTF();
		//������Ϣ
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		dos.writeUTF(msg);
		dos.flush();
		 //�ͷ���Դ
		 dos.close();
		 dis.close();
		 client.close();
	}

}