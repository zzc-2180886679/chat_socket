package edu.cc.chat03;

import java.io.IOException;
import java.net.Socket;




/**
 * ���������ң��ͻ���
 *����ͻ��շ�������Ϣ
 *���ֵ����������ͻ�����ȴ�֮ǰ���û��˳�����ʹ�ã��Ŷ�
 *�����ʹ�ö��߳�
 *��װ����ά��
 * @author chunchun
 *@date 2020��6��15��
 * @projectname chatsocket_demo
 */
public class TMultiClient {
	public static void main(String[] args) throws  IOException {
		System.out.println("----client----");
		//�������ӣ�ʹ��SOcket�����ͻ���+�������ĵ�ַ�Ͷ˿�
		Socket client =new Socket("localhost",8888);
		//�ͻ��˷�����Ϣ
		new Thread(new Send(client)).start();
		new Thread(new Receive(client)).start();
	}

}
