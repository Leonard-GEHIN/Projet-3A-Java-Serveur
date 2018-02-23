import java.io.IOException;
import java.net.ServerSocket;


public class Main{
	public static void main(String[] args) {
		int nombreJoueurMax = 4; //Sert a limiter le nomnre de connection au serveur
		int nombreClient = 0;
		
		/*
		 * Mise en place du serveur TCP et des connnections avec les clients
		 * Port: 
		 */
		int[] socketTravail = new int[nombreJoueurMax];
		
		//Creation du socket principal
		int port = 6000;
		boolean portValide = false;
		while(!portValide && port <= Math.pow(2, 16)-1) { //Si un port est deja utilise, il faut en prendre un autre
            portValide = true;
			try {
	            ServerSocket SocketPrincipal = new ServerSocket(port, nombreJoueurMax);
			} catch (IOException e) {
				System.out.println("Port "+port+" est deja utilise.");
				port++;
				portValide = false;
			}
		}
		
		//Aucun port trouve
		if(port == 65536) {
			System.out.println("Aucun port libre ; le serveur ne peut demarrer.");
		}
		else {
			System.out.println("Port utilile : " + port + ".");
			
		}
	}
}












