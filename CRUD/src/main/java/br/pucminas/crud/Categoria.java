package br.pucminas.crud;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Categoria implements Registro {
	
    private int id;
    private String nome;
    
    public Categoria() 
    {
    	this(0, null);
	}
    
    public Categoria(String _nome)
    {
    	this(0, _nome);
	}
    
    public Categoria(int _id) {
    	this(_id, "");
	}
    
	public Categoria(int _id, String _nome)
	{
		id = _id;
		nome = _nome;
	}

	@Override
	public int getID()
	{
		return id;
	}
	
	@Override
	public void setID(int _id)
	{
		
		id = _id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String getTableName()
	{
		
		return getClass().getName();
	}

	@Override
	public byte[] toByteArray() throws IOException
	{
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(output);
		
		dataOut.writeInt(id);
		dataOut.writeUTF(nome);
		
		return output.toByteArray();
	}
	@Override
	public void fromByteArray(byte[] _byteData) throws IOException
	{

		ByteArrayInputStream input = new ByteArrayInputStream(_byteData);
		DataInputStream dataInput = new DataInputStream(input);
		
		id = dataInput.readInt();
		nome = dataInput.readUTF();
		
	}
	
	@Override
	public String toString()
	{
		
		return "\nID....:    " + this.id + 
			   "\nNome..:    " + this.nome;
	}
    
}

