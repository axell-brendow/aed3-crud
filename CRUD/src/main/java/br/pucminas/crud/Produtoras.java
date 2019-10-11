package br.pucminas.crud;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Essa classe, seria, para a relacao n para n,
 * visto que uma produtora ( DC procutions, Marvel Studios, 21st Fox, etc) tem varios filmes feitos
 * e um filme tem varias produtoras 
 */
public class Produtoras implements Registro {
	
	private String nome;
	private int id;
	
	public Produtoras ( ) {
		nome = "";
		id = 0;
	}
	
	public Produtoras (String _nome)
	{
		nome = _nome;
	}
	
	public Produtoras (String _nome, int _id)
	{
		nome = _nome;
		id = _id;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String _nome)
	{
		this.nome = _nome;
	}

	@Override
	public int getID()
	{
		return id;
	}

	@Override
	public void setID(int _id)
	{
		this.id = _id;
	}

	@Override
	public String getTableName()
	{
		return this.getClass().getName();
	}

	@Override
	public byte[] toByteArray() throws IOException {
		
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		DataOutputStream dataOutput = new DataOutputStream(byteOutput);
		
		dataOutput.writeInt(id);
		dataOutput.writeUTF(nome);
		
		return byteOutput.toByteArray();
	}

	@Override
	public void fromByteArray(byte[] _byteData) throws IOException {
		
		ByteArrayInputStream byteInput = new ByteArrayInputStream(_byteData);
		DataInputStream dataInput = new DataInputStream(byteInput);
		
		id = dataInput.readInt();
		nome = dataInput.readUTF();
		
	}
	
}
