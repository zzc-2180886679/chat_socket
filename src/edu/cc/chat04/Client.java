package edu.cc.chat04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;




/**
 * 
 *Ŀ�꣺��������ʵ��Ⱥ��
 * @author chunchun
 *@date 2020��6��15��
 * @projectname chatsocket_demo
 */
public class Client {
	public static void main(String[] args) throws  IOException {
		System.out.println("----client----");
	 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	System.out.println("�������û���");	
	String name =br.readLine();
	 //�������ӣ�ʹ��SOcket�����ͻ���+�������ĵ�ַ�Ͷ˿�
		Socket client =new Socket("localhost",8888);
		//�ͻ��˷�����Ϣ
		new Thread(new Send(client,name)).start();
		new Thread(new Receive(client)).start();
	}

}
