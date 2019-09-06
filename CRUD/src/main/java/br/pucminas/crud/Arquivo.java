package br.pucminas.crud;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Arquivo<T extends Registro> {
    
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
     * Cria arquivo de dados para a entidaded
     * @param _construtor Construtor da classe da entidade
     * @param _nomeArquivo Nome do arquivo
     * @throws Exception
     */
    public Arquivo(Constructor<T> _construtor, String _nomeArquivo) throws Exception {
        construtor = _construtor;
        nomeArquivo = _nomeArquivo;

        File d = new File("dados");

        if( !d.exists() )
            d.mkdir();

        arquivo = new RandomAccessFile("dados/"+nomeArquivo, "rw");

        if(arquivo.length()<4)
            arquivo.writeInt(0);        
    }
    
    /**
     * Inclui novo registro
     * @param _obj Registro
     * @return ID do novo registro
     * @throws Exception
     */
    public int incluir(T _obj) throws Exception {
        this.arquivo.seek(0);

        int ultimoID = this.arquivo.readInt() + 1;

        arquivo.seek(0);
        arquivo.writeInt(ultimoID);

        arquivo.seek(arquivo.length());
        _obj.setID(ultimoID);

        arquivo.writeByte(' ');             // lápide
        byte[] byteArray = _obj.toByteArray();

        arquivo.writeInt(byteArray.length); // indicador de tamanho do registro
        arquivo.write(byteArray);           // vetor de bytes que representa o registro

        return _obj.getID();
    }
    
    // Método apenas para testes, pois geralmente a memória principal raramente
    // será suficiente para manter todos os registros simultaneamente
    /**
     * Lista os registros do arquivo
     * @return Lista com os objetos
     * @throws Exception
     */
    public Object[] listar() throws Exception {

        ArrayList<T> lista = new ArrayList<>();

        arquivo.seek(4);
        
        byte lapide;
        byte[] byteArray;
        int s;
        T obj;
        
        while(arquivo.getFilePointer()<arquivo.length()) {
        
            obj = construtor.newInstance();
            lapide = arquivo.readByte();
            s = arquivo.readInt();
        
            byteArray = new byte[s];
        
            arquivo.read(byteArray);
            obj.fromByteArray(byteArray);
        
            if(lapide == ' ')
                lista.add(obj);
        }
        
        return lista.toArray();
    }
    
    /**
     * Encontra um registro
     * @param id ID do registro
     * @return Objeto genérico com os dados do registro
     * @throws Exception
     */
    public Object buscar(int id) throws Exception {
        arquivo.seek(4);

        byte lapide;
        byte[] byteArray;
        int s;
        T obj = null;

        while(arquivo.getFilePointer() < arquivo.length()) {

            obj = construtor.newInstance();
            lapide = arquivo.readByte();
            s = arquivo.readInt();

            byteArray = new byte[s];

            arquivo.read(byteArray);
            obj.fromByteArray(byteArray);

            if(lapide == ' ' && obj.getID() == id)
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
    public boolean excluir(int _id) throws Exception {

        arquivo.seek(4);

        byte lapide;
        byte[] byteArray;
        int s;
        T obj = null;
        long endereco;

        while(arquivo.getFilePointer()<arquivo.length()) {
            obj = construtor.newInstance();
            endereco = arquivo.getFilePointer();
            lapide = arquivo.readByte();
            s = arquivo.readInt();

            byteArray = new byte[s];

            arquivo.read(byteArray);
            obj.fromByteArray(byteArray);

            if(lapide==' ' && obj.getID()==_id) {
                arquivo.seek(endereco);
                arquivo.writeByte('*');
                return true;
            }
        }

        return false;
    }
    
}
