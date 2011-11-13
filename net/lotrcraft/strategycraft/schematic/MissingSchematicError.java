package net.lotrcraft.strategycraft.schematic;

public class MissingSchematicError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6437949250535704785L;
	
	public MissingSchematicError(){
		super();
	}
	

	public MissingSchematicError(String s){
		super(s);
	}

}
