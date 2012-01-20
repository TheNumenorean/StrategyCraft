package net.lotrcraft.strategycraft.schematic;

public class SchematicReadException extends Exception{

	private static final long serialVersionUID = 5670152821922242317L;
	
	public SchematicReadException(String string) {
		super(string);
	}
	
	public SchematicReadException(){
		super("Unknown error");
	}
}
