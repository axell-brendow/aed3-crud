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

        File d = new File("dados"); // Diretório para o arquivo

        if(!d.exists())
            d.mkdir();

        arquivo = new RandomAccessFile("dados/" + nomeArquivo, "rw"); // Abrir o arquivo

        // Se o arquivo for menor do que o tamanho do cabeçalho, logo não possuir cabeçalho
        // Escreve 0 para representar o último ID utilizado
        if(arquivo.length() < HEADER_SIZE)
            arquivo.writeInt(0);        
    }
    
    /**
     * Inclui novo registro
     * @param _obj Registro
     * @return ID do novo registro
     * @throws Exception
     */
    public int incluir(T _obj) throws Exception
    {
        arquivo.seek(0);

        int ultimoID = arquivo.readInt() + 1;

        arquivo.seek(0);
        arquivo.writeInt(ultimoID);

        arquivo.seek(arquivo.length());
        _obj.setID(ultimoID);

        arquivo.writeByte(' ');
        byte[] byteArray = _obj.toByteArray();

        arquivo.writeInt(byteArray.length); // Tamanho do registro
        arquivo.write(byteArray);

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

        while(arquivo.getFilePointer() < arquivo.length())
        {
            obj = construtor.newInstance();
            lapide = arquivo.readByte();
            size = arquivo.readInt();

            byteArray = new byte[size];

            arquivo.read(byteArray);
            obj.fromByteArray(byteArray);

            if(lapide == ' ' && obj.getID() == _id)
                return obj;
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
        arquivo.seek(HEADER_SIZE);

        byte lapide;
        byte[] byteArray;
        int size;
        T obj = null;
        long endereco;

        while(arquivo.getFilePointer() < arquivo.length())
        {
            obj      = construtor.newInstance();
            endereco = arquivo.getFilePointer();
            lapide   = arquivo.readByte();
            size     = arquivo.readInt();

            byteArray = new byte[size];

            arquivo.read(byteArray);
            obj.fromByteArray(byteArray);

            if(lapide==' ' && obj.getID()==_id)
            {
                arquivo.seek(endereco);
                arquivo.writeByte('*');

                return true;
            }
        }

        return false;
    }
}
