package jogoDaVelhaSocket;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import jogoDaVelhaSocket.mensagem.EnvioDePacote;
import jogoDaVelhaSocket.utils.ConfiguracoesServidor;
import jogoDaVelhaSocket.utils.FabricaDeMensagem;
import jogoDaVelhaSocket.utils.Mensagem;
import jogoDaVelhaSocket.utils.Pacote;
import jogoDaVelhaSocket.utils.TipoDeMensagem;


public class Cliente {

	public static void main(String args[]) throws Exception {
		 DatagramSocket socket = new DatagramSocket();
	     Conexao conexao = new Conexao(socket, InetAddress.getByName(ConfiguracoesServidor.ENDERECO_SERVIDOR), ConfiguracoesServidor.PORTA_SERVIDOR);
	     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	     Scanner scanner = new Scanner(System.in);
	     System.out.println("Deseja jogar o jogo da velha s/n?");
		 String sentence = reader.readLine();
		 Mensagem mensagem = null;
		 
			
			if (sentence.equalsIgnoreCase("s")) {
				conexao.enviarMensagem(FabricaDeMensagem.criarMensagemIniciarJogador());
			}else {
				
			}

		while(true) {
			
			// Verifica se a fila não está vazia
            mensagem = conexao.receberMensagem();

            // Certifique-se de que o pacote não é null
            if (mensagem == null) {
                // Pause o loop por um breve momento para não consumir CPU desnecessariamente
                Thread.sleep(500);  // A pausa evita sobrecarregar o processador
               
            }else {
            	// Supondo que a mensagem contenha um inteiro correspondente ao enum.
            	Object obj = mensagem.getFields()[0];

            	// Verifique se o objeto é de fato um Integer.
            	if (obj instanceof Integer) {
            	    int valor = (Integer) obj;

            	    // Faça o mapeamento do inteiro para o enum.
            	    TipoDeMensagem tipoDeMensagem = TipoDeMensagem.values()[valor];

            	    switch (tipoDeMensagem) {
            	    case esperandoJogador:
            	    	System.out.println("Esperando o outro jogador...\n");
            	    	break;
            	    case iniciarJogador:
            	    	System.out.println("Iniciando o jogo...\n");
            	    	break;
            	    case jogoEncerrado:
            	    	System.out.println("Jogo encerrado. ");
            	    	conexao.stop();
            	    	socket.close();
            	    	break;
            	   // case vezDoJogador:
            	    case entradaInvalida:
            	    case suaVez:
            	    	System.out.println("Vamos lá, sua vez! 1");
            	    	System.out.println("Por favor, diga em qual linha deseja jogar. ");
            	    	int linha = reader.read();
            	    	System.out.println("Por favor, diga em qual coluna deseja jogar. ");
            	    	int coluna = reader.read();
            	    	Jogada jogada = new Jogada(linha, coluna);
            	    	Object[] conteudoMensagem = {TipoDeMensagem.enviarJogada.ordinal(), jogada};
            	    	Mensagem jogadaMensagem = new Mensagem(conteudoMensagem);
            	    	conexao.enviarMensagem(jogadaMensagem);
            	    	break;
            	    case jogadoresProntos:
            	    	System.out.println("O jogo está prestes a começar");
            	    	break;
            	    case jogadorInicia:
            	    	System.out.println("Vamos lá, sua vez 2!");
            	    	System.out.println("Por favor, diga em qual linha deseja jogar. ");
            	    	int linha2 = scanner.nextInt();
            	    	System.out.println("Por favor, diga em qual coluna deseja jogar. ");
            	    	int coluna2 = scanner.nextInt();
            	    	Jogada jogada2 = new Jogada(linha2, coluna2);
            	    	
            	    	Object[] conteudoMensagem2 = {TipoDeMensagem.enviarJogada.ordinal(), jogada2};
            	    	Mensagem jogadaMensagem2 = new Mensagem(conteudoMensagem2);
            	    	conexao.enviarMensagem(jogadaMensagem2);
            	    	break;
            	    case jogadorEspera:
            	    	System.out.println("Aguarde, o outro jogador será o primeiro");
            	    	break;
//           	    case jogadorVenceu:
//           	    	if(mensagem.getFields()[1] instanceof String) {
//        	    		String tabuleiro = (String) mensagem.getFields()[1];
//        	    		System.out.println(tabuleiro);
//        	    	}
           	    	
           	    	
           	    	//break;
            	    case jogoEmpatou:
            	    	System.out.println("Jogo empatou");
            	   
            	    	break;
            	    case aguardeSuaVez:
            	    	System.out.println("Aguarde, vez do outro jogador");
            	    	break;
            	    case tabuleiro:
            	    	System.out.println("esta aqui");
            	    	if(mensagem.getFields()[1] instanceof String) {
            	    		String tabuleiro = (String) mensagem.getFields()[1];
            	    		System.out.println("tabuleiro esta aqui " +tabuleiro);
            	    	}
            	    	break;
            	    	
            	    }
            	    
         	          
            	  
            	} else {
            	    System.out.println("Tipo inválido para conversão para enum.");
            	}


            }
			
		}
		
		
		
		
		
		
		
		
		
		
//		BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
//		Scanner scanner = new Scanner(System.in);
//		DatagramSocket clientSocket = new DatagramSocket();
//
//		InetAddress ipAddress = InetAddress.getByName("localhost");
//		int port = 80;
//
//		System.out.println("Deseja jogar o jogo da velha s/n?");
//		String sentence = keyboardReader.readLine();
//
//		EnvioDePacote.enviarMensagem(clientSocket, sentence, ipAddress, port);
//		
//		byte[] receivedData = new byte[1024];
//
//		while (true) {
//			//receber resposta do servidor
//			String serverMessage = EnvioDePacote.receberMensagem(clientSocket);
//
//			System.out.println("Servidor:");
//			System.out.println(serverMessage);
//			
//			//verificar se o servidor informou que o jogo terminou
//			if (serverMessage.contains("Fim de jogo") || serverMessage.contains("Vencedor")) {
//                System.out.println("Jogo encerrado.");
//                clientSocket.close();
//               break;
//            }
//			
//			
//			if(serverMessage.contains("Sua vez") || serverMessage.contains("primeiro")  || serverMessage.contains("inválida")) {
//				System.out.println("Por favor, diga em qual linha deseja jogar. ");
//				int linha = scanner.nextInt();
//				System.out.println("Por favor, diga em qual coluna deseja jogar. ");
//				int coluna = scanner.nextInt();
//				
//				Jogada jogada = new Jogada(linha, coluna);
//                EnvioDePacote.enviarMensagem(clientSocket, jogada, ipAddress, port);
//			}
//		}
//		 clientSocket.close();
	}

}