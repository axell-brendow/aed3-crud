package br.pucminas.crud;
import java.io.*;

public class Livro implements Registro
{
    /**
     * ID do livro
     */
    private int id;

    /**
     * Título do livro
     */
    private String titulo;

    /**
     * Autor do livro
     */
    private String autor;

    /**
     * Preço do livro
     */
    private float preco;

    /**
     * Nome da tabela
     */
    private static final String TABLE_NAME = "livros";
   
    /**
     * Cria um novo livro vazio
     */
    public Livro() 
    {
        id = 0;
        titulo = "";
        autor = "";
        preco = 0;
    }
    
    /**
     * Cria um novo livro com os dados preenchidos
     * @param _titulo Título do livro
     * @param _autor Autor do livro
     * @param _preco Preço do livro
     */
    public Livro(String _titulo, String _autor, float _preco) 
    {
        titulo = _titulo;
        autor  = _autor;
        preco  = _preco;
    }
    
    /**
     * Retorna o ID do livro
     * @return ID do livro
     */
    @Override
    public int getID()
    {
        return id;
    }
    
    /**
     * Grava o ID
     * @param _id ID a ser gravado
     */
    @Override
    public void setID(int _id)
    {
        id = _id;
    }

    /**
     * Título
     * @return Título
     */
    public String getTitulo()
    {
        return titulo;
    }

    /**
     * Autor
     * @return Autor
     */
    public String getAutor()
    {
        return autor;    
    }

    /**
     * Preço
     * @return Preço
     */
    public float getPreco()
    {
        return preco;    
    }

    /**
     * Recupera o nome da tabela
     * @return String com o nome da tabela
     */
    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }
    
    public String toString() 
    {
        return "\nID....: "    + id     + 
               "\nTitulo: "    + titulo + 
               "\nAutor.: "    + autor  + 
               "\nPreço.: R$ " + preco;
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
        dos.writeUTF(autor);
        dos.writeFloat(preco);  

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
        
        id = dis.readInt();
        titulo = dis.readUTF();
        autor = dis.readUTF();
        preco = dis.readFloat();
    }
    
}