package edu.cc.chat03;

import java.io.Closeable;

/**
 * ������
 * @author chunchun
 *@date 2020��6��15��
 * @projectname chatsocket_demo
 */
public class Cutils {
//�ͷ���Դ
	public static void close(Closeable ... targets) {
		for(Closeable target:targets) {
			try {
				if(null!=target) {
					target.close();
				}
			} catch (Exception e) {
			}
		}
	}
}
