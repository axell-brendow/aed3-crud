package br.pucminas.crud;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Arquivo<T extends Registro>
{
	/**
	 * Nome do arquivo
	 */
	public String nomeArquivo;

	/**
	 * Objeto arquivo
	 */
	public RandomAccessFile arquivo;

	/**
	 * Index do arquivo
	 */
	public RandomAccessFile arquivoIndexId;

	/**
	 * Construtor da classe genérica
	 */
	private Constructor<T> construtor;

	/**
	 * Tamanho em bytes do cabeçalho do arquivo
	 */
	private final int HEADER_SIZE = 4;
	
	/**
	 * Cria arquivo de dados para a entidaded
	 * @param _construtor Construtor da classe da entidade
	 * @param _nomeArquivo Nome do arquivo
	 * @throws Exception
	 */
	public Arquivo(Constructor<T> _construtor, String _nomeArquivo) throws Exception
	{
		construtor = _construtor;
		nomeArquivo = _nomeArquivo;

		T obj = construtor.newInstance();

		File d = new File("dados"); // Diretório para o arquivo

		if(!d.exists())
			d.mkdir();

		arquivo        = new RandomAccessFile("dados/" + nomeArquivo, "rw"); // Abrir o arquivo
		arquivoIndexId = new RandomAccessFile("dados/" + obj.getTableName() + "_idIndex.db", "rw"); // Abrir o arquivo de index

		// Se o arquivo for menor do que o tamanho do cabeçalho, logo não possuir cabeçalho
		// Escreve 0 para representar o último ID utilizado
		if(arquivo.length() < HEADER_SIZE)
			arquivo.writeInt(0);        
	}
	
	/**
	 * Inclui um registro
	 * @param _obj Registro
	 * @return ID do um registro
	 * @throws Exception
	 */
	public int incluir(T _obj) throws Exception
	{
		return incluir(_obj, 0, arquivo.length());
	}

	/**
	 * Inclui um registro
	 * @param _obj Registro
	 * @param _lixo Tamanho do lixo após o registro
	 * @param _pos Posição a ser escrita
	 * @return ID do um registro
	 * @throws Exception
	 */
	private int incluir(T _obj, int _lixo, long _pos) throws Exception
	{
		if (_obj.getID() <= 0)
		{
			arquivo.seek(0);
			
			int ultimoID = arquivo.readInt() + 1;
			_obj.setID(ultimoID);
			
			arquivo.seek(0);
			arquivo.writeInt(ultimoID);

			inserirIndex(_obj.getID(), _pos);
		}
		else
			alterarIndex(_obj.getID(), _pos);
		
		arquivo.seek(_pos);

		arquivo.writeByte(' ');
		byte[] byteArray = _obj.toByteArray();

		arquivo.writeInt(byteArray.length); // Tamanho do registro
		arquivo.write(byteArray);

		arquivo.writeInt(_lixo);

		return _obj.getID();
	}
	
	// Método apenas para testes, pois geralmente a memória principal raramente
	// será suficiente para manter todos os registros simultaneamente
	/**
	 * Lista os registros do arquivo
	 * @return Array com os registros
	 * @throws Exception
	 */
	public Object[] listar() throws Exception
	{
		ArrayList<T> lista = new ArrayList<>();

		arquivo.seek(HEADER_SIZE);
		
		byte lapide;
		byte[] byteArray;
		int size;
		T obj;
		
		while(arquivo.getFilePointer() < arquivo.length())
		{
			obj = construtor.newInstance();
			lapide = arquivo.readByte();
			size = arquivo.readInt();
		
			byteArray = new byte[size];
		
			arquivo.read(byteArray);
		
			if(lapide == ' ')
			{
				obj.fromByteArray(byteArray);
				lista.add(obj);
			}

			arquivo.skipBytes(arquivo.readInt()); // Pular o lixo
		}
		
		return lista.toArray();
	}
	
	/**
	 * Encontra um registro
	 * @param _id ID do registro
	 * @return Objeto genérico com os dados do registro
	 * @throws Exception
	 */
	public Object buscar(int _id) throws Exception
	{
		arquivo.seek(HEADER_SIZE);

		byte lapide;
		byte[] byteArray;
		int size;
		T obj = null;

		long pos = getPosicao(_id);
		

		if (pos > 0)
		{
			arquivo.seek(pos);
			
			lapide = arquivo.readByte();

			if (lapide == ' ')
			{
				obj = construtor.newInstance();

				size = arquivo.readInt();

				byteArray = new byte[size];

				arquivo.read(byteArray);
				obj.fromByteArray(byteArray);

				return obj;
			}
		}

		return null;
	}
	
	/**
	 * Exclui um registro
	 * @param _id ID do registro
	 * @return Se excluiu
	 * @throws Exception
	 */
	public boolean excluir(int _id) throws Exception
	{
		long endereco = getPosicao(_id);

		if (endereco < HEADER_SIZE)
			return false;

		arquivo.seek(endereco);
		arquivo.writeByte('*');
		alterarIndex(_id, -1);

		return true;
	}

	/**
	 * Insere novo registro no arquivo de index
	 * @param _id ID do registro
	 * @param _pos Posição do registro
	 * @throws Exception
	 */
	public void inserirIndex(int _id, long _pos) throws Exception
	{
		arquivoIndexId.seek(arquivoIndexId.length());

		arquivoIndexId.writeInt(_id);
		arquivoIndexId.writeLong(_pos);
	}

	/**
	 * Altera posição de um registro armazenada no index
	 * @param _id ID do registro
	 * @param _newPos Nova posição do registro
	 * @return Se conseguiu fazer a alteração
	 */
	public boolean alterarIndex(int _id, long _newPos) throws Exception
	{
		if (_id < 1) return false;

		long startPos = 0;
		long endPos   = arquivoIndexId.length() / 12;
		long indexPos = 0;
		int idLido    = -1;

		while (_id           != idLido &&
			   startPos      <= endPos &&
			   indexPos * 12 <= arquivoIndexId.length() - 12)
		{
			indexPos = startPos + ((endPos - startPos) / 2);

			arquivoIndexId.seek(indexPos * 12);
			idLido = arquivoIndexId.readInt();

			if      (_id < idLido) endPos   = indexPos - 1;
			else if (idLido < _id) startPos = indexPos + 1;
		}

		if (idLido != _id) return false;

		arquivoIndexId.writeLong(_newPos);

		return true;
	}

	/**
	 * Recupera a posição de um registro
	 * @param _id ID do registro
	 * @return Posição do registro
	 * @throws Exception
	 */
	public long getPosicao(int _id) throws Exception
	{
		if (_id < 1) return -1;

		long startPos = 0;
		long endPos   = arquivoIndexId.length() / 12;
		long indexPos = 0;
		int idLido    = -1;

		while (_id           != idLido &&
			   startPos      <= endPos &&
			   indexPos * 12 <= arquivoIndexId.length() - 12)
		{
			indexPos = startPos + ((endPos - startPos) / 2);

			arquivoIndexId.seek(indexPos * 12);
			idLido = arquivoIndexId.readInt();

			if      (_id < idLido) endPos   = indexPos - 1;
			else if (idLido < _id) startPos = indexPos + 1;
		}

		if (idLido != _id) return -1;

		return arquivoIndexId.readLong();
	}

	/**
	 * Altera um registro
	 * @param _obj Dados do registro
	 * @return Se houve sucesso
	 * @throws Exception
	 */
	public boolean alterar(T _obj) throws Exception
	{
		if (_obj.getID() <= 0)
			return false;

		long endereco = getPosicao(_obj.getID());

		if (endereco <= 0)
			return false;

		byte[] objData = _obj.toByteArray();

		arquivo.seek(endereco);

		arquivo.skipBytes(1);

		int size = arquivo.readInt(); // Tamanho do registro

		arquivo.skipBytes(size);
		size += arquivo.readInt(); // O tamanho disponível é o tamanho do registro, mais o lixo após o registro

		arquivo.seek(endereco);

		if (size >= objData.length)
			this.incluir(_obj, size - objData.length, endereco);
		else
		{
			arquivo.writeByte('*');
			this.incluir(_obj);
		}

		return true;
	}

	/**
	 * Remove registros desnecessários do arquivo
	 * @throws Exception
	 */
	public void cleanup() throws Exception
	{
		ArrayList<byte[]> list;

		byte[] buffer;
		T obj;

		byte lapide;
		int size, lixoSize;

		arquivo.seek(0);

		buffer = new byte[HEADER_SIZE];
		arquivo.read(buffer);

		RandomAccessFile newArquivo = new RandomAccessFile("dados/tmp.db", "rw");
		newArquivo.write(buffer);

		while (arquivo.getFilePointer() < arquivo.length())
		{
			list    = new ArrayList<>();

			for (int i = 0; i < 1024; i++)
			{
				lapide = arquivo.readByte();
				size = arquivo.readInt();
	
				buffer = new byte[size];
				arquivo.read(buffer);
	
				if (lapide == ' ')
				{
					obj = construtor.newInstance();
					obj.fromByteArray(buffer);
	
					list.add(buffer);
				}
				else i--;
	
				lixoSize = arquivo.readInt();
				arquivo.skipBytes(lixoSize);

				if (arquivo.getFilePointer() >= arquivo.length())
					break;
			}

			Object[] arr = list.toArray();

			for (int i = 0; i < arr.length; i++)
			{
				buffer = (byte[])arr[i];

				obj = construtor.newInstance();
				obj.fromByteArray(buffer);

				alterarIndex(obj.getID(), newArquivo.getFilePointer());

				newArquivo.writeByte(' ');
				newArquivo.writeInt(buffer.length);

				newArquivo.write(buffer);

				newArquivo.writeInt(0);
			}
		}

		newArquivo.close();
		arquivo.close();

		File tmpF = new File("dados/tmp.db");
		tmpF.renameTo(new File("dados/" + nomeArquivo));

		arquivo = new RandomAccessFile("dados/" + nomeArquivo, "rw");
	}

	/**
	 * Fecha os arquivos abertos na instância
	 * @throws Exception
	 */
	public void fecha() throws Exception
	{
		arquivo.close();
		arquivoIndexId.close();
	}
}
