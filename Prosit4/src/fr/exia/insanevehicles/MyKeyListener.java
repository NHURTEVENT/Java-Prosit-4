package fr.exia.insanevehicles;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyListener extends KeyAdapter {
	
	private static char Dirrection; 

	public static char getDirrection() {
		return Dirrection;
	}

	public void setDirrection(char dirrection) {
		Dirrection = dirrection;
	}

	@Override

	public void keyPressed(KeyEvent event) {

		KeyEvent key = event;

		switch(key.getKeyCode()){
		
		case 39:
			InsaneVehiclesGames.setDirrection('d');
			System.out.println("droite");
			break;
		
		case 37:
			InsaneVehiclesGames.setDirrection('g');
			System.out.println("gauche");
			break;
		default:
			InsaneVehiclesGames.setDirrection('a'); //qu'importe tant que != d ou g
			System.out.println("front");
			break;
			
		}
		
		
	}

}
