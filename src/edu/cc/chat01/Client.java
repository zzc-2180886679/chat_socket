package edu.cc.chat01;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ���������ң��ͻ���
 * ʵ��һ���ͻ����������շ���Ϣ
 * @author chunchun
 *@date 2020��6��15��
 * @projectname chatsocket_demo
 */
public class Client {
	public static void main(String[] args) throws IOException {
		System.out.println("----client----");
		//�������ӣ�ʹ��SOcket�����ͻ���+�������ĵ�ַ�Ͷ˿�
		Socket client =new Socket("localhost",8888);
		//�ͻ��˷�����Ϣ
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		String msg = console.readLine();
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		dos.writeUTF(msg);
		dos.flush();
		//��ȡ��Ϣ
		DataInputStream dis = new DataInputStream(client.getInputStream());
		 msg = dis.readUTF();
		 System.out.println(msg);
		 
		 //�ͷ���Դ
		 dos.close();
		 dis.close();
		 client.close();
	}

}
