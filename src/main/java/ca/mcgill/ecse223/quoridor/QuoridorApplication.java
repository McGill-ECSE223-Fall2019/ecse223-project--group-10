package ca.mcgill.ecse223.quoridor;

import ca.mcgill.ecse223.quoridor.model.Quoridor;

public class QuoridorApplication {
	private static Quoridor quoridor;

	public enum Side {
		up, down, left, right
	};

	public static void main(String[] args) {

	}

	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
		return quoridor;
	}
	
}
