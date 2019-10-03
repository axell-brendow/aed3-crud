package br.pucminas.crud;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jdk.jshell.spi.ExecutionControl.ExecutionControlException;

public class CRUD
{    
	private static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	private static Arquivo<Filme> arqFilmes;
	private static Arquivo<Categoria> arqCategoria;
	
	public static void main(String[] args)
	{    
		try
		{
			arqFilmes	 = new Arquivo<>(Filme.class.getConstructor(), "filmes.db");
			arqCategoria = new Arquivo<>(Categoria.class.getConstructor(), "Categoria.db");
		
			// Menu
			int opcao;
			
			do
			{
				System.out.println("\n\n------------------------------------------------");
				System.out.println("                    MENU");
				System.out.println("------------------------------------------------");
				System.out.println("11 - Listar filmes		21 - Listar Categorias");
				System.out.println("12 - Buscar filme		22 - Buscar Categoria");
				System.out.println("13 - Incluir filme		23 - Incluir Categoria");
				System.out.println("14 - Excluir filme		24 - Excluir Categoria");
				System.out.println("15 - Modificar filme	25 - Modificar Categoria");
				System.out.println("16 - Limpar arquivo		26 - Limpar Categorias");
				System.out.println("9 - Povoar BD");
				System.out.println("0 - Sair");
				System.out.print("\nOpção: ");

				try
				{
					opcao = Integer.valueOf(console.readLine());
				}
				catch(NumberFormatException e)
				{
					opcao = -1;
				}

				try
				{
					switch(opcao)
					{
						case 11: listarFilmes();  		break;
						case 12: buscarFilme();   		break;
						case 13: incluirFilme();  		break;
						case 14: excluirFilme();  		break;
						case 15: alterarFilme();  		break;
						case 16: limparArquivo();		break;
//						case 21: listarFilmes(); 		break;
//						case 22: buscarFilme();   		break;
						case 23: incluirCategoria();  	break;
//						case 24: excluirFilme();  		break;
//						case 25: alterarFilme();  		break;
//						case 26: limparArquivo(); 		break;
						case 9: povoar();        		break;
						case 0:                  		break;
						default: System.out.println("Opção inválida");
					}
				} 
				catch (Exception e)
				{
				   System.out.println("Valor não reconhecido.");
				}

			}
			while(opcao!=0);

			arqFilmes.fecha();

		}
		catch(Exception e)
		{
	   		e.printStackTrace();
		}        
	}    
	
	public static void limparArquivo()
	{
		try
		{
			arqFilmes.cleanup();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Listar filmes gravados no arquivo
	 * 
	 * @throws Exception
	 */
	public static void listarFilmes() throws Exception
	{
		Object[] filmes = arqFilmes.listar();

		for(int i = 0; i < filmes.length; i++)
			System.out.println((Filme)filmes[i]);

		
		System.out.print("Pressione ENTER");
		console.readLine();
	}

	/**
	 * Busca um filme presente no arquivo
	 * @throws Exception
	 */
	public static void buscarFilme() throws Exception
	{
		System.out.println("\nBUSCA");

		int id;
		System.out.print("ID: ");

		id = Integer.valueOf(console.readLine());
		if(id <= 0) return;

		Filme buscado = (Filme)arqFilmes.buscar(id);

		if(buscado != null)
			System.out.println(buscado);
		else
			System.out.println("Filme não encontrado");

		System.out.print("Pressione ENTER");
		console.readLine();
	}

	/**
	 * Cadastra um novo filme
	 * @throws Exception
	 */
	public static void incluirFilme() throws Exception
	{
		//FIXME: concertar erro ao inserir filme (retorna valor nao conhecido)
		String titulo;
		int idCategoria;
		short ano;
		char confirma;

		System.out.println("\nINCLUSÃO");

		System.out.print("Título: ");
		titulo = console.readLine();

		System.out.print("Categoria: ");
		idCategoria = Integer.valueOf(console.readLine());
		
		//Verificar se existe a categoria
		if (arqCategoria.buscar(idCategoria) == null)
		{
			System.out.println("O id da Categoria não existe");
			
			//XXX:Serial legal adicionar uma opcao para perguntar se quer adicionar a categoria?
			return;
		}

		System.out.print("Ano: ");
		try
		{
			ano = Short.valueOf(console.readLine());
		}
		catch (Exception e)
		{
			ano = 0;
		}

		System.out.print("\nConfirma inclusão? ");
		try
		{
			confirma = console.readLine().charAt(0);
		}
		catch (Exception e)
		{
			confirma = 'n';
		} 

		if(confirma == 's' || confirma == 'S')
		{
			Filme aSerIncluido = new Filme(titulo, idCategoria, ano);
			int id = arqFilmes.incluir(aSerIncluido);
			System.out.println("Filme incluído com ID: " + id);
		}
		else
			System.out.println("Filme não incluído.");

		System.out.print("Pressione ENTER");
		console.readLine();
	}

	/**
	 * Exclui um filme do arquivo
	 * @throws Exception
	 */
	public static void excluirFilme() throws Exception
	{
		int id;
		char confirma;

		System.out.println("\nEXCLUSÃO");

		System.out.print("ID: ");
		id = Integer.valueOf(console.readLine());

		if (id <= 0) return;

		Filme aSerExcluido = (Filme)arqFilmes.buscar(id);

		if (aSerExcluido != null)
		{
			System.out.println(aSerExcluido);
			System.out.print("\nConfirma exclusão? ");
			try
			{
				confirma = console.readLine().charAt(0);
			}
			catch (Exception e)
			{
				confirma = 'n';
			} 
				
			if(confirma=='s' || confirma=='S')
				if(arqFilmes.excluir(id))
					System.out.println("Filme excluído.");
				else
					System.out.println("Exclusão cancelada.");
		}
		else
			System.out.println("Filme não encontrado");

		System.out.print("Pressione ENTER");
		console.readLine();
	}

	/**
	 * Altera um filme no arquivo
	 * @throws Exception
	 */
	public static void alterarFilme() throws Exception
	{
		System.out.println("\nALTERAÇÃO");

		int id;
		char confirma;

		System.out.print("ID: ");

		id = Integer.valueOf(console.readLine());
		if (id <= 0) return;

		Filme aSerAlterado = (Filme)arqFilmes.buscar(id);

		if(aSerAlterado != null)
			System.out.println(aSerAlterado);
		else
		{
			System.out.println("Filme não encontrado");
			return;
		}

		System.out.println();

		String titulo;
		int idCategoria;
		short ano;

		System.out.print("Novo título: ");
		titulo = console.readLine();
		if (titulo.equals(" "))
			titulo = aSerAlterado.getTitulo();

		System.out.print("Nova categoria: ");
		idCategoria = Integer.valueOf(console.readLine());
		if (idCategoria != 0)
			idCategoria = aSerAlterado.getCategoria();

		System.out.print("Novo Ano: ");
		try
		{
			ano = Short.valueOf(console.readLine());
		}
		catch (Exception e)
		{
			ano = aSerAlterado.getAno();
		}

		System.out.print("\nConfirma alteração? ");
		try
		{
			confirma = console.readLine().charAt(0);
		}
		catch (Exception e)
		{
			confirma = 'n';
		}        

		if(confirma == 's' || confirma == 'S')
		{
			aSerAlterado = new Filme(titulo, idCategoria, ano);
			aSerAlterado.setID(id);

			if (arqFilmes.alterar(aSerAlterado))
				System.out.println("Filme alterado com sucesso.");
			else
				System.out.println("Erro ao alterar filme.");
		}
		else
			System.out.println("Alteração cancelada.");

		System.out.print("Pressione ENTER");
		console.readLine();
	}

	
	public static void incluirCategoria() throws Exception
	{
		String nome;
		int idCategoria;
		char confirma;
		
		System.out.println("\nINCLUSÃO CATEGORIA");
		
		try
		{
			
			nome = console.readLine();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gerar uma lista de filmes
	 * @throws Exception
	 */
	public static void povoar() throws Exception
	{
		//arqFilmes.incluir(new Filme("The Godfather","Crime",(short)1972));
	}	
}
