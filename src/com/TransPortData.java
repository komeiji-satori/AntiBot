package com;

import static com.lolicraft_antibot.GetNowTime;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TransPortData extends Thread {

    //private transient s
    Socket getDataSocket;
    Socket putDataSocket;
    String type;

    public TransPortData(Socket getDataSocket, Socket putDataSocket, String type) {
	this.getDataSocket = getDataSocket;
	this.putDataSocket = putDataSocket;
	this.type = type;
    }

    public void run() {
	try {
	    while (true) {
		InputStream in = getDataSocket.getInputStream();
		OutputStream out = putDataSocket.getOutputStream();
		//Read Data
		byte[] data = new byte[1024];
		int readlen = in.read(data);
		//If Empty Data then Stop
		if (readlen <= 0) {
		    Thread.sleep(300);
		    continue;
		}
		out.write(data, 0, readlen);
		out.flush();
	    }
	} catch (Exception e) {
	    int response_type = Integer.parseInt(type);
	    if (response_type>2) {
		System.out.println(GetNowTime()+"Throw Error,ErrType:" + type);
	    }
	} finally {
	    //Close Socket Connect
	    try {
		if (putDataSocket != null) {
		    putDataSocket.close();
		}
	    } catch (Exception exx) {
	    }
	    try {
		if (getDataSocket != null) {
		    getDataSocket.close();
		}
	    } catch (Exception exx) {

	    }
	}
    }
}
