package controller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Observable;

/**
 * @author Adin Epstein
 * @since 25/01/2017
 *
 */
public class MyClientHandler extends Observable implements ClientHandler {

	private boolean exitFlag;
	String commandLine;
	LinkedList<String> params;

	public MyClientHandler() {
		exitFlag = false;
		commandLine = "";
		params = new LinkedList<String>();
	}

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		PrintWriter writer = new PrintWriter(outToClient);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inFromClient));

		while (!exitFlag) {
			writer.println("Please enter a command: ");
			writer.flush();
			try {
				commandLine = reader.readLine();
				commandLine = commandLine.toLowerCase();
				String[] arr = commandLine.split(" ");
				params.clear();
				for (int i = 0; i < arr.length; i++) {
					params.add(arr[i]);
				}
				if (arr[0].equals("exit")) {
					System.out.println(arr[0]);
					exitFlag = false;
					writer.println("bye");
				}
				setChanged();
				notifyObservers(params);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
