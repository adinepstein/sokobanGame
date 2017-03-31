package controller.server;

import java.net.ServerSocket;
import java.net.Socket;

public class MyServer implements Server {
	private int port;
	private ClientHandler ch;
	private boolean stop;

	public MyServer(int port, ClientHandler ch) {
		this.port = port;
		this.ch = ch;
		stop = false;
	}

@SuppressWarnings("resource")
public void runServer() throws Exception {
	ServerSocket server=new ServerSocket(port);
	
		try{
			System.out.println("Server waiting for client");
			Socket aClient=server.accept();
			try{
			System.out.println("client connected");
			ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
			aClient.getInputStream().close();
			aClient.getOutputStream().close();
			aClient.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
}

	@Override
	public void openServer() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					runServer();
				} catch (Exception e) {
					// check were to send the message
					e.printStackTrace();
				}
			}

		});
		t.start();
	}

	@Override
	public void stopServer() {
		stop = true;
	}
}
