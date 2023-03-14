//-----------------------------------------------------
// Title: Player class
// Author: Ecenaz Güngör
// Description: This class This class contains necessary attributes for the player.
//-----------------------------------------------------


// Each player has name, surname and transferFee properties
public class Player {
	private String name;
	private String surname;
	private int transferFee;
	
	public Player(String name, String surname, int transferFee) {
		this.name = name;
		this.surname = surname;
		this.transferFee = transferFee;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public int getTransferFee() {
		return transferFee;
	}

	@Override
	public String toString() {
		return name + " " + surname + " " + transferFee;
	}
	
	// Some operations (SearchFeeRange and FindKSmallest) 
	// do not have transfer fee
	public String PrintWithoutFee() {
		return name + " " + surname;
	}
	
}

