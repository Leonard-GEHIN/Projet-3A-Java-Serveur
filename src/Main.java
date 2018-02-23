import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class Main{
	public static void main(String[] args) {
		int nombreJoueurMax = 4; //Sert a limiter le nomnre de connection au serveur
		int nombreClient = 0;
		
		/*
		 * Mise en place du serveur TCP et des connnections avec les clients
		 * Port: 
		 */
		Socket[] socketTravail = new Socket[nombreJoueurMax];
		
		//Creation du socket principal
		int port = 6000;
		boolean portValide = false;
		ServerSocket SocketPrincipal = null;
		while(!portValide && port <= Math.pow(2, 16)-1) { //Si un port est deja utilise, il faut en prendre un autre
            portValide = true;
			try {
	            SocketPrincipal = new ServerSocket(port, nombreJoueurMax);
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
			
			//Time out sur le socket principal pour le rendre non bloquant
			try {
				SocketPrincipal.setSoTimeout(100);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			socketTravail = attenteJoueur(SocketPrincipal, nombreJoueurMax);
		}
	}				
	
	
	//Renvoie les sockets de travail pour communiquer avec les clients
	public static Socket[] attenteJoueur(ServerSocket SocketPrincipal, int nombreJoueurMax) {
		Socket[] socketTravail = new Socket[nombreJoueurMax];
		boolean[] joueurPret = new boolean[nombreJoueurMax];
		boolean[] timeOutSet = new boolean[nombreJoueurMax];
		int nombreJoueurConnecte = 0;
		boolean attenteClient = true;
		
		for (int i = 0; i < socketTravail.length; i++) socketTravail[i] = null;
		
		while(attenteClient && nombreJoueurConnecte < nombreJoueurMax) {
			try {
				//Accept la connection
				socketTravail[nombreJoueurConnecte] = SocketPrincipal.accept();
				
				//Nouveau socket accepte
				if(socketTravail[nombreJoueurConnecte] != null) {
					//Mise en place du timeOut pour ce nouveau socket
					if(!timeOutSet[nombreJoueurConnecte]) {
						try {
							socketTravail[nombreJoueurConnecte].setSoTimeout(8);
						} catch (SocketException e) {
							e.printStackTrace();
						}
					}
					
					nombreJoueurConnecte++;
				}
				
				//Ecoute si les clients sont pret
				
				//Si un joueur n'est pas pret, on attend
				attenteClient = false;
				for (int i = 0; i < joueurPret.length; i++) 
					if(!joueurPret[i])
						attenteClient = true;
			} catch (IOException e) {
				e.printStackTrace(); //Affiche l'erreur
			}
		}
		
		return socketTravail;
	}
	
	/*
	//Ecoute les messages sur tout les sockets et renvoie un message recu
	//Les sockets ecoute sont les sockets non nuls
	public static String ecouteClient(Socket[] socketTravail) {
		String message = "";
		return message;
	}
	*/
}












