package br.pucminas.crud;
import java.io.IOException;

public interface Registro {
	
	/**
	 * Retorna o ID do registro
	 * @return ID do registro
	 */
	public int getID();

	/**
	 * Grava o ID
	 * @param _id ID
	 */
	public void setID(int _id);

	/**
	 * Recupera o nome da tabela
	 * @return String com o nome da tabela
	 */
	public String getTableName();

	/**
	 * Converte o registro em um array de bytes, com seus dados
	 * @return O array de bytes
	 * @throws IOException
	 */
	public byte[] toByteArray() throws IOException;

	/**
	 * Preenche os campos a partir de um array de bytes
	 * @param _byteData Array com os dados
	 * @throws IOException
	 */
	public void fromByteArray( byte[] _byteData ) throws IOException;
	
}
