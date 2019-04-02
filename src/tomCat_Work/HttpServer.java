package tomCat_Work;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.sun.net.httpserver.*;


public class HttpServer {
	public static String WEB_ROOT = System.getProperty("user.dir") + java.io.File.separator + "webroot";
	private static final String SHUTDOWN_CAMMOND = "/shutdown";
	private boolean shutdown = false;
	public static void main(String[] args) {
		HttpServer httpServer = new HttpServer();
		httpServer.await();
	}
	public void await() {
		ServerSocket serversocket = null;
		int port = 8080;
		try {
			serversocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.exit(1);
		}
		while(!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serversocket.accept();
				input = socket.getInputStream();
				OutputStream Output = socket.getOutputStream();
				Request request = new Request(input);
				request.parse();

				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResource();
				socket.close();
				shutdown = request.getUri().equals(SHUTDOWN_CAMMOND);
			}
			catch(Exception e) {
				e.printStackTrace();
				continue;
			}

		}
	}
}
