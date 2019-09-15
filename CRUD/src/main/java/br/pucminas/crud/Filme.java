package br.pucminas.crud;

import java.io.*;

public class Filme implements Registro
{
    private int id;
    private String titulo;
    private String categoria;
    private short ano;
    private final String TABLE_NAME = "filme";

    public Filme()
    {
        this.id = 0;
        this.titulo = "";
        this.categoria = "";
        this.ano = 0;
	}
	
    public Filme(String _titulo, String _categoria, short _ano)
    {
        this.titulo = _titulo;
        this.categoria = _categoria;
        this.ano = _ano;
    }

//---Metodos get:

    @Override
	public int getID()
	{
		return this.id;
	}
	  
    public String getTitulo()
	{
		return this.titulo;
	}

	public String getCategoria()
	{
        return this.categoria;
    }
    
	public short getAno()
	{
        return this.ano;
	}

	@Override
	public String getTableName()
	{
		return this.TABLE_NAME;
	}
    
    
//---Metodos Set:
    
	/**
	 * Grava o ID
	 * @param _id ID a ser gravado
	 */
    @Override
    public void setID(int _id)
    {
        this.id = _id;
    }

    public void setTitulo(String _titulo)
	{
		this.titulo = _titulo;
	}

	public void setCategoria(String _categoria)
	{
        this.categoria = _categoria;
    }
    
    public void setAno(short _ano)
	{
        this.ano = _ano;
	}

//---Outros metodos:

	public String toString() 
	{
		return "\nID....:    " + this.id + 
			   "\nTitulo:    " + this.titulo + 
			   "\nCategoria: " + this.categoria + 
			   "\nAno.:      " + this.ano;
	}
	
	/**
	 * Transforma o objeto em um array de bytes
	 * @return Array de bytes com os dados
	 */
    @Override
	public byte[] toByteArray() throws IOException 
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		dos.writeInt(id);
		dos.writeUTF(titulo);
		dos.writeUTF(categoria);
		dos.writeShort(ano);  

		return baos.toByteArray();
	}

	/**
	 * Preenche os campos a partir de um array de bytes
	 * @param _byteData Array de bytes com os dados
	 */
	@Override
	public void fromByteArray(byte[] _byteData) throws IOException 
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(_byteData);
		DataInputStream dis = new DataInputStream(bais);
		
		this.id = dis.readInt();
		this.titulo = dis.readUTF();
		this.categoria = dis.readUTF();
		this.ano = dis.readShort();
	}
	
}

/*
    *Observacoes:

- Adicionar o metodo getTableName();
- Adicionar mais variaveis a classe Filme,
tais como Duracao, Pais, dataLancamento etc.

*/
