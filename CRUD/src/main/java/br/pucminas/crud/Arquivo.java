package br.pucminas.crud;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Arquivo<T extends Registro> {
    
    public String nomeArquivo;
    public RandomAccessFile arquivo;
    Constructor<T> construtor;
    
    public Arquivo(Constructor<T> c, String n) throws Exception {
        construtor = c;
        nomeArquivo = n;
        File d = new File("dados");
        if( !d.exists() )
            d.mkdir();
        arquivo = new RandomAccessFile("dados/"+nomeArquivo, "rw");
        if(arquivo.length()<4)
            arquivo.writeInt(0);        
    }
    
    public int incluir(T obj) throws Exception {
        this.arquivo.seek(0);
        int ultimoID = this.arquivo.readInt();
        ultimoID++;
        arquivo.seek(0);
        arquivo.writeInt(ultimoID);

        arquivo.seek(arquivo.length());
        obj.setID(ultimoID);
        arquivo.writeByte(' ');             // lápide
        byte[] byteArray = obj.toByteArray();
        arquivo.writeInt(byteArray.length); // indicador de tamanho do registro
        arquivo.write(byteArray);           // vetor de bytes que representa o registro
        return obj.getID();
    }
    
    // Método apenas para testes, pois geralmente a memória principal raramente
    // será suficiente para manter todos os registros simultaneamente
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
            if(lapide==' ')
                lista.add(obj);
        }
        return lista.toArray();
    }
    
    public Object buscar(int id) throws Exception {
        arquivo.seek(4);
        byte lapide;
        byte[] byteArray;
        boolean encontrou = false;
        int s;
        T obj = null;
        while(arquivo.getFilePointer()<arquivo.length()) {
            obj = construtor.newInstance();
            lapide = arquivo.readByte();
            s = arquivo.readInt();
            byteArray = new byte[s];
            arquivo.read(byteArray);
            obj.fromByteArray(byteArray);
            if(lapide==' ' && obj.getID()==id) {
                encontrou = true;
                break;
            }
        }
        return (encontrou?obj:null);
    }
    
    public boolean excluir(int id) throws Exception {
        arquivo.seek(4);
        byte lapide;
        byte[] byteArray;
        boolean excluiu = false;
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
            if(lapide==' ' && obj.getID()==id) {
                arquivo.seek(endereco);
                arquivo.writeByte('*');
                excluiu = true;
                break;
            }
        }
        return excluiu;
    }
    
}
