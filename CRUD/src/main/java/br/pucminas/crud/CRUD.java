package br.pucminas.crud;
import java.util.Scanner;

public class CRUD {
    
    private static Scanner console = new Scanner(System.in);
    private static Arquivo<Livro> arqLivros;
    
    public static void main(String[] args) {

        
        try {
            arqLivros = new Arquivo<>(Livro.class.getConstructor(), "livros.db");
            
           // menu
           int opcao;
           do {
                System.out.println("\n\n------------------------------------------------");
                System.out.println("                    MENU");
                System.out.println("------------------------------------------------");
                System.out.println("1 - Listar livros");
                System.out.println("2 - Buscar livro");
                System.out.println("3 - Incluir livro");
                System.out.println("4 - Excluir livro");
                System.out.println("9 - Povoar BD");
                System.out.println("0 - Sair");
                System.out.print("\nOpcao: ");
                try {
                    opcao = Integer.valueOf(console.nextLine());
                } catch(NumberFormatException e) {
                    opcao = -1;
                }

                switch(opcao) {
                    case 1: listarLivro(); break;
                    case 2: buscarLivro(); break;
                    case 3: incluirLivro(); break;
                    case 4: excluirLivro(); break;
                    case 9: povoar(); break;
                    case 0: break;
                    default: System.out.println("Opção inválida");
                }
               
           } while(opcao!=0);
            
            
            
        } catch(Exception e) {
            e.printStackTrace();
        }        
    }    
    
    public static void listarLivro() throws Exception {
        Object[] livros = arqLivros.listar();
        for(int i=0; i<livros.length; i++) {
            System.out.println((Livro)livros[i]);
        }
     }
   
    public static void buscarLivro() throws Exception {
        System.out.println("\nBUSCA");
        int id;
        System.out.print("ID: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return;
        Livro l;
        if( (l = (Livro)arqLivros.buscar(id))!=null )
            System.out.println(l);
        else
            System.out.println("Livro não encontrado");
    }
    
   public static void incluirLivro() throws Exception {
        String titulo, autor;
        float preco;
        System.out.println("\nINCLUSÃO");
        System.out.print("Título: ");
        titulo = console.nextLine();
        System.out.print("Autor: ");
        autor = console.nextLine();
        System.out.print("Preço: ");
        preco = Float.valueOf(console.nextLine());
        System.out.print("\nConfirma inclusão? ");
        char confirma = console.nextLine().charAt(0);
        if(confirma=='s' || confirma=='S') {
            Livro l = new Livro(-1, titulo, autor, preco);
            int id = arqLivros.incluir(l);
            System.out.println("Livro incluído com ID: "+id);
        }
   }
   
   public static void excluirLivro() throws Exception {
        System.out.println("\nEXCLUSÃO");
        int id;
        System.out.print("ID: ");
        id = Integer.valueOf(console.nextLine());
        if(id <=0) 
            return;
        Livro l;
        if( (l = (Livro)arqLivros.buscar(id))!=null ) {
            System.out.println(l);
            System.out.print("\nConfirma exclusão? ");
            char confirma = console.nextLine().charAt(0);
            if(confirma=='s' || confirma=='S') {
                if( arqLivros.excluir(id) ) {
                    System.out.println("Livro excluído.");
                }
            }
        }
        else
            System.out.println("Livro não encontrado");
    }
   
    public static void povoar() throws Exception {
        arqLivros.incluir(new Livro(-1,"O Pequeno Príncipe","Antoine de Saint-Exupéry",(float)27.9));
        arqLivros.incluir(new Livro(-1,"Número Zero","Humberto Eco",(float)14.9));
        arqLivros.incluir(new Livro(-1,"A Garota no Trem","Paula Hawkins",(float)20.9));
        arqLivros.incluir(new Livro(-1,"A Rainha Vermelha","Victoria Aveyard",(float)22.1));
        arqLivros.incluir(new Livro(-1,"O Sol É Para Todos","Harper Lee",(float)27));
        /*
        arqLivros.incluir(new Livro(-1,"1984","George Orwell",(float)32.8));
        arqLivros.incluir(new Livro(-1,"A Odisséia","Homero",(float)35.9));
        arqLivros.incluir(new Livro(-1,"Sherlock Holmes","Arthur Conan Doyle",(float)24));
        arqLivros.incluir(new Livro(-1,"Joyland","Stephen King",(float)17.9));
        arqLivros.incluir(new Livro(-1,"Objetos Cortantes","Gillian Flynn",(float)16.9));
        arqLivros.incluir(new Livro(-1,"A Lista Negra","Jennifer Brown",(float)16.9));
        arqLivros.incluir(new Livro(-1,"Garota Exemplar","Gillian Flynn",(float)14.9));
        arqLivros.incluir(new Livro(-1,"O Iluminado","Stephen King",(float)14.9));
        arqLivros.incluir(new Livro(-1,"Queda de Gigantes","Ken Follett",(float)23.67));
        arqLivros.incluir(new Livro(-1,"Eternidade Por Um Fio","Ken Follett",(float)30.1));
        arqLivros.incluir(new Livro(-1,"Inverno do Mundo","Ken Follett",(float)29.99));
        arqLivros.incluir(new Livro(-1,"A Guerra dos Tronos","George R. R. Martin",(float)22.76));
        arqLivros.incluir(new Livro(-1,"A Revolução dos Bichos","George Orwell",(float)27.36));
        arqLivros.incluir(new Livro(-1,"O Mundo de Sofia","Jostein Gaarder",(float)28.2));
        arqLivros.incluir(new Livro(-1,"O Velho e o Mar","Ernest Hemingway",(float)16.9));
        arqLivros.incluir(new Livro(-1,"Escuridão Total Sem Estrelas","Stephen King",(float)28.4));
        arqLivros.incluir(new Livro(-1,"O Pintassilgo","Donna Tartt",(float)21.63));
        arqLivros.incluir(new Livro(-1,"Se Eu Ficar","Gayle Forman",(float)13.54));
        arqLivros.incluir(new Livro(-1,"Toda Luz Que Não Podemos Ver","Anthony Doerr",(float)24.38));
        arqLivros.incluir(new Livro(-1,"Eu, Você e a Garota Que Vai Morrer","Jesse Andrews",(float)14.9));
        arqLivros.incluir(new Livro(-1,"Na Natureza Selvagem","Jon Krakauer",(float)14.9));
        arqLivros.incluir(new Livro(-1,"Eu, Robô","Isaac Asimov",(float)20.15));
        arqLivros.incluir(new Livro(-1,"O Demonologista","Andrew Pyper",(float)32.47));
        arqLivros.incluir(new Livro(-1,"O Último Policial","Ben Winters",(float)27.6));
        arqLivros.incluir(new Livro(-1,"A Febre","Megan Abbott",(float)27.9));   
        */
    }
    
}
