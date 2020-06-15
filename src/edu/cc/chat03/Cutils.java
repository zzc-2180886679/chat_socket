package edu.cc.chat03;

import java.io.Closeable;

/**
 * 工具类
 * @author chunchun
 *@date 2020年6月15日
 * @projectname chatsocket_demo
 */
public class Cutils {
//释放资源
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
