package jogoDaVelhaSocket;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Cliente {
//	private static int jogadorAtual = 0;
//
	public static void main(String args[]) throws Exception {
		
		BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();

		InetAddress ipAddress = InetAddress.getByName("localhost");
		int port = 80;

		System.out.println("Deseja jogar o Jogo da Velha s/n?");
		String sentence = keyboardReader.readLine();

		byte[] sendData = sentence.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
		clientSocket.send(sendPacket);
		
		
		byte[] receivedData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);

		// Receber o segmento UDP
//		clientSocket.receive(receivePacket);

		while (true) {
			//receber resposta do servidor
			clientSocket.receive(receivePacket);
			String serverMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
			System.out.println("FROM SERVER:");
			System.out.println(serverMessage);
			
			//verificar se o servidor informou que o jogo terminou
			if (serverMessage.contains("Fim de jogo") || serverMessage.contains("Vencedor")) {
                System.out.println("Jogo encerrado.");
               break;
            }
			
			if(serverMessage.contains("Sua vez") || serverMessage.contains("primeiro")) {
				System.out.println("Em qual campo deseja jogar? ");
				String jogada = keyboardReader.readLine();
                sendData = jogada.getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
                clientSocket.send(sendPacket);
			}
		}
		 clientSocket.close();
	}

}
