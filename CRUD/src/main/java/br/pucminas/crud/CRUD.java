package br.pucminas.crud;

import java.io.BufferedReader;
import java.io.IOException;
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
		
			escolherMenus();

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
	
	//------------------METODOS PARA PRINTAR O MENU
	
	/**
	 * Metodo para escolher sub menus principal
	 * @throws Exception
	 */
	public static void escolherMenus() throws Exception
	{
		
		int opcao = 0;
		
		do
		{
			printarMenuPrincial();
			
			try
			{
				opcao = Integer.valueOf(console.readLine());
			}
			catch(NumberFormatException | IOException a)
			{
				opcao = -1;
				System.out.println("Metodo: menu principal");
				a.printStackTrace();
			}
			
			switch(opcao)
			{
			case 1:	printarMenuFilmes();						break;
			case 2:	printarMenuCategorias();					break;
			case 3:	povoar();									break;
			case 4: limparArquivo();							break;
			case 0:  											break;
			default: System.out.println("Menu não encontrado");	break;		
			}
		} while (opcao != 0);
	}
	
	/**
	 * metodo para apenas printar o menu princial
	 */
	public static void printarMenuPrincial()
	{
		System.out.println("\n\n------------------------------------------------");
		System.out.println("                    MENU PRINCIPAL");
		System.out.println("------------------------------------------------");
		System.out.println("1 - Filmes");
		System.out.println("2 - Categorias");
		System.out.println("3 - Povoar Banco de Dados");
		System.out.println("4 - Limpar Banco de Dados");
		System.out.println("0 - Sair");
		System.out.print("\nOpção: ");
	}
	
	/**
	 * Metodo para printar o menu dos filmes com suas opecaoes
	 * @throws Exception
	 */
	public static void printarMenuFilmes() throws Exception
	{
		
		int opcaoFilme = 0;
		
		do
		{
			
			System.out.println("\n\n------------------------------------------------");
			System.out.println("                    MENU FILMES");
			System.out.println("------------------------------------------------");
			System.out.println("1 - Listar 	filmes");
			System.out.println("2 - Buscar 	filme");
			System.out.println("3 - Incluir filme");
			System.out.println("4 - Excluir filme");
			System.out.println("5 - Modificar filme");
			System.out.println("0 - voltar para o menu principal");
			System.out.print("\nOpção: ");
			
			try
			{
				opcaoFilme = Integer.valueOf(console.readLine());
			}
			catch(NumberFormatException | IOException a)
			{
				opcaoFilme = -1;
				System.out.println("Metodo: menu filmes");
				a.printStackTrace();
			}
			
			switch(opcaoFilme)
			{
				case 1:	listarFilmes();		break;
				case 2:	buscarFilme();		break;
				case 3:	incluirFilme();		break;
				case 4:	excluirFilme();		break;
				case 5:	alterarFilme();		break;
				case 0:						break;
				default:					break;
			}
			
		}  while (opcaoFilme != 0);
		
	}
	
	/**
	 * Metodo para printar o menu das categorias com suas operacaoes
	 * @throws Exception
	 */
	public static void printarMenuCategorias () throws Exception
	{
		int opcaoCategoria = 0;
		
		do
		{
			
			System.out.println("\n\n------------------------------------------------");
			System.out.println("                    MENU CATEGORIAS");
			System.out.println("------------------------------------------------");
			System.out.println("1 - Listar 	Categorias");
			System.out.println("2 - Buscar 	Categoria");
			System.out.println("3 - Incluir Categoria");
			System.out.println("4 - Excluir Categoria");
			System.out.println("5 - Modificar Categoria");
			System.out.println("0 - voltar para o menu principal");
			System.out.print("\nOpção: ");
			
			try
			{
				opcaoCategoria = Integer.valueOf(console.readLine());
			}
			catch(NumberFormatException | IOException a)
			{
				opcaoCategoria = -1;
				System.out.println("Metodo: menu filmes");
				a.printStackTrace();
			}
			
			switch(opcaoCategoria)
			{
				case 1:	listarCatetegorias();	break;
				case 2:	buscarCategoria();		break;
				case 3:	incluirCategoria();		break;
				case 4:	excluirCategoria();		break;
				case 5:	alterarCategoria();		break;
				case 0:							break;
				default:						break;
			}
			
		}  while (opcaoCategoria != 0);
	}

	//------------------METODOS COM FILME
	
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

	//------------------METODOS COM CATEGORIA
	
	/**
	 * Listar Categorias gravados no arquivo
	 * @throws Exception
	 */
	public static void listarCatetegorias() throws Exception
	{
		Object[] categorias =  arqCategoria.listar();
		
		for (int i = 0; i < categorias.length; i++) {
			
			System.out.println(categorias[i]);
		}
		
		System.out.print("Pressione ENTER");
		console.readLine();
	}

	/**
	 * Busca uma Categoria presente no arquivo
	 * @throws Exception
	 */
	public static void buscarCategoria() throws Exception
	{
		//FIXME: concertar erro ao inserir filme (retorna valor nao conhecido)
		System.out.println("\nBUSCA");

		int id;
		System.out.print("ID: ");

		id = Integer.valueOf(console.readLine());
		if(id <= 0) return;

		Categoria buscado = (Categoria)arqCategoria.buscar(id);

		if(buscado != null)
			System.out.println(buscado);
		else
			System.out.println("Categoria não encontrada");

		System.out.print("Pressione ENTER");
		console.readLine();
	}

	/**
	 *  Cadastra uma nova Categoria
	 * @throws Exception
	 */
	public static void incluirCategoria() throws Exception
	{
		String nome = "";
		char confirma;
		
		System.out.println("\nINCLUSÃO CATEGORIA");
		
		try
		{
			System.out.print("Nome: ");
			nome = console.readLine();
			
		} catch (Exception e) {
			
			e.printStackTrace();
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
			Categoria aSerIncluido = new Categoria(nome);
			int id = arqCategoria.incluir(aSerIncluido);
			System.out.println("Categoria incluído com ID: " + id);
		}
		else
		{
			System.out.println("Categoria não incluído.");
		}

		System.out.print("Pressione ENTER");
		console.readLine();
		
	}

	/**
	 * Exclui uma categoria do arquivo
	 * @throws Exception
	 */
	public static void excluirCategoria() throws Exception
	{
		int id;
		char confirma;

		System.out.println("\nEXCLUSÃO");

		System.out.print("ID: ");
		id = Integer.valueOf(console.readLine());

		if (id <= 0) return;

		Categoria aSerExcluido = (Categoria)arqCategoria.buscar(id);

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
				if(arqCategoria.excluir(id))
					System.out.println("Categoria excluído.");
				else
					System.out.println("Exclusão cancelada.");
		}
		else
			System.out.println("Categoria não encontrado");

		System.out.print("Pressione ENTER");
		console.readLine();
	}
	
	/**
	 * Altera uma categoria no arquivo
	 * @throws Exception
	 */
	public static void alterarCategoria() throws Exception
	{
		System.out.println("\nALTERAÇÃO");

		int id;
		char confirma;

		System.out.print("ID: ");

		id = Integer.valueOf(console.readLine());
		if (id <= 0) return;

		Categoria aSerAlterado = (Categoria)arqCategoria.buscar(id);

		if(aSerAlterado != null)
			System.out.println(aSerAlterado);
		else
		{
			System.out.println("Categoria não encontrado");
			return;
		}

		System.out.println();

		String nome;

		System.out.print("Novo Nome: ");
		nome = console.readLine();
		if (nome.equals(" "))
			nome = aSerAlterado.getNome();

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
			aSerAlterado = new Categoria(nome);
			aSerAlterado.setID(id);

			if (arqCategoria.alterar(aSerAlterado))
				System.out.println("Categoria alterado com sucesso.");
			else
				System.out.println("Erro ao alterar Categoria.");
		}
		else
			System.out.println("Alteração cancelada.");

		System.out.print("Pressione ENTER");
		console.readLine();
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
