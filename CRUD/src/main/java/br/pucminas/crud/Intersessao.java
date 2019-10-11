package br.pucminas.crud;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * para a relacao de 1 para n,
 * Nesta classe, ira ser onde os filme e produtoras "vao se encontrar", entao aqui apenas tera dois atributos,
 * o ID do filme i o ID da protudora
 * @author 1135188
 *
 */
public class Intersessao implements Registro{

	private int idFilme;
	private int idProdutora;
	
	public Intersessao (int _idFilme, int _idProdutora)
	{
		idFilme = _idFilme;
		idProdutora = _idProdutora;
	}
	
	public Intersessao ( )
	{
		idFilme = 0;
		idProdutora = 0;
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setID(int _id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] toByteArray() throws IOException {
		
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
		DataOutputStream dataOutput = new DataOutputStream(byteOutput);
		
		dataOutput.writeInt(idFilme);
		dataOutput.writeInt(idProdutora);
		
		return byteOutput.toByteArray();
	}

	@Override
	public void fromByteArray(byte[] _byteData) throws IOException {
		
		ByteArrayInputStream byteInput = new ByteArrayInputStream(_byteData);
		DataInputStream dataInput = new DataInputStream(byteInput);

		idFilme = dataInput.readInt();
		idProdutora = dataInput.readInt();
	}
}
