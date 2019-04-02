package tomCat_Work;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class App {
	public static void main(String[] args) {
	try {
		Socket socket = new Socket("127.0.0.1",8080);
		OutputStream outputstream = socket.getOutputStream();
		boolean autoflush = true;
		PrintWriter out = new PrintWriter(outputstream, autoflush);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out.println("GET /index.jsp HTTP/1.1");
		out.println("Host:localhost:8080");
		out.println("Connection: Close");
		out.println();
		
		boolean loop = true;
		StringBuffer sb = new StringBuffer(8096);
	
		while(loop) {
			if(br.ready()) {
				int i = 0;
				while(i!=-1) {
					i = br.read();
					sb.append((char)i);
				}
				loop = false;
			}
			Thread.currentThread().sleep(50);
		}
		System.out.println(sb.toString());
		socket.close();
		
	} catch (Exception e) {
		// TODO: handle exception
	}
}
}