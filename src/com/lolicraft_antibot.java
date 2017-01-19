package com;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class lolicraft_antibot {

    public static void main(String[] args) throws IOException {
	if (args == null || args.length < 3) {
	    System.err.println(GetNowTime()+"Empty Value:Local Port,RemoteIP,RemotePort");
	    return;
	}
	//Get Config
	int localPort = Integer.parseInt(args[0].trim());
	String remoteIp = args[1].trim();
	int remotePort = Integer.parseInt(args[2].trim());

	//Start Listen Port&Addres
	ServerSocket serverSocket = new ServerSocket(localPort);
	System.out.println(GetNowTime()+"Start Listen Local Port:" + localPort);
	while (true) {
	    Socket clientSocket = null;
	    Socket remoteServerSocket = null;
	    //Start Client Proxy Connect
	    try {
		clientSocket = serverSocket.accept();
		String user_ip = clientSocket.getInetAddress().getHostAddress();
		//System.out.println(ip_addr);

		if (!CheckIPIsBot(user_ip)) {
		    System.out.println(GetNowTime()+"Successfully intercepted attacks from ip:" + user_ip);
		    continue;
		} else {
		    System.out.println(GetNowTime()+"BOT check to allow for release,ip:" + user_ip);
		}
		remoteServerSocket = new Socket(remoteIp, remotePort);
		(new TransPortData(clientSocket, remoteServerSocket, "1")).start();
		(new TransPortData(remoteServerSocket, clientSocket, "2")).start();
	    } catch (Exception e) {
		System.out.println(GetNowTime()+"Fail To Build Connect Request");
	    }
	}
    }

    public static String GetNowTime() {
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	String str = sdf.format(date);
	return "["+str+"]";
    }

    public static boolean CheckIPIsBot(String ip) throws IOException {
	IP.enableFileWatch = true; // 默认值为：false，如果为true将会检查ip库文件的变化自动reload数据
	File directory = new File("");
	String ipip_path = directory.getCanonicalPath();
	IP.load(ipip_path + "\\ipip_db.dat");
	String[] data = IP.find(ip);//返回字符串数组["GOOGLE","GOOGLE"]
	String location = data[0];
	//System.out.println("ip:"+ip+"from "+location);
	//System.out.println(data[0]);
	if (location.equals("中国")) {
	    return true;
	} else {
	    return false;
	}
    }
}
