package br.pucminas.crud;

import java.util.Scanner;

public class CRUD
{    
	private static Scanner console = new Scanner(System.in);
	private static Arquivo<Filme> arqFilmes;
	
	public static void main(String[] args)
	{    
		try
		{
			arqFilmes = new Arquivo<>(Filme.class.getConstructor(), "filmes.db");
		
			// Menu
			int opcao;
	
			do
			{
				System.out.println("\n\n------------------------------------------------");
				System.out.println("                    MENU");
				System.out.println("------------------------------------------------");
				System.out.println("1 - Listar filmes");
				System.out.println("2 - Buscar filme");
				System.out.println("3 - Incluir filme");
				System.out.println("4 - Excluir filme");
				System.out.println("5 - Modificar filme");
				System.out.println("6 - Limpar arquivo");
				System.out.println("9 - Povoar BD");
				System.out.println("0 - Sair");
				System.out.print("\nOpção: ");

				try
				{
					opcao = Integer.valueOf(console.nextLine());
				}
				catch(NumberFormatException e)
				{
					opcao = -1;
				}

				try
				{
					switch(opcao)
					{
						case 1: listarFilmes();  break;
						case 2: buscarFilme();   break;
						case 3: incluirFilme();  break;
						case 4: excluirFilme();  break;
						case 5: alterarFilme();  break;
						case 6: limparArquivo(); break;
						case 9: povoar();        break;
						case 0:                  break;
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
		console.nextLine();
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

		id = Integer.valueOf(console.nextLine());
		if(id <= 0) return;

		Filme buscado = (Filme)arqFilmes.buscar(id);

		if(buscado != null)
			System.out.println(buscado);
		else
			System.out.println("Filme não encontrado");

		System.out.print("Pressione ENTER");
		console.nextLine();
	}

	/**
	 * Cadastra um novo filme
	 * @throws Exception
	 */
	public static void incluirFilme() throws Exception
	{
		String titulo, categoria;
		short ano;
		char confirma;

		System.out.println("\nINCLUSÃO");

		System.out.print("Título: ");
		titulo = console.nextLine();

		System.out.print("Categoria: ");
		categoria = console.nextLine();

		System.out.print("Ano: ");
		try
		{
			ano = Short.valueOf(console.nextLine());
		}
		catch (Exception e)
		{
			ano = 0;
		}

		System.out.print("\nConfirma inclusão? ");
		try
		{
			confirma = console.nextLine().charAt(0);
		}
		catch (Exception e)
		{
			confirma = 'n';
		} 

		if(confirma == 's' || confirma == 'S')
		{
			Filme aSerIncluido = new Filme(titulo, categoria, ano);
			int id = arqFilmes.incluir(aSerIncluido);
			System.out.println("Filme incluído com ID: " + id);
		}
		else
			System.out.println("Filme não incluído.");

		System.out.print("Pressione ENTER");
		console.nextLine();
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
		id = Integer.valueOf(console.nextLine());

		if (id <= 0) return;

		Filme aSerExcluido = (Filme)arqFilmes.buscar(id);

		if (aSerExcluido != null)
		{
			System.out.println(aSerExcluido);
			System.out.print("\nConfirma exclusão? ");
			try
			{
				confirma = console.nextLine().charAt(0);
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
		console.nextLine();
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

		id = Integer.valueOf(console.nextLine());
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

		String titulo, categoria;
		short ano;

		System.out.print("Novo título: ");
		titulo = console.nextLine();
		if (titulo.equals(" "))
			titulo = aSerAlterado.getTitulo();

		System.out.print("Nova categoria: ");
		categoria = console.nextLine();
		if (categoria.equals(" "))
			categoria = aSerAlterado.getCategoria();

		System.out.print("Novo Ano: ");
		try
		{
			ano = Short.valueOf(console.nextLine());
		}
		catch (Exception e)
		{
			ano = aSerAlterado.getAno();
		}

		System.out.print("\nConfirma alteração? ");
		try
		{
			confirma = console.nextLine().charAt(0);
		}
		catch (Exception e)
		{
			confirma = 'n';
		}        

		if(confirma == 's' || confirma == 'S')
		{
			aSerAlterado = new Filme(titulo, categoria, ano);
			aSerAlterado.setID(id);

			if (arqFilmes.alterar(aSerAlterado))
				System.out.println("Filme alterado com sucesso.");
			else
				System.out.println("Erro ao alterar filme.");
		}
		else
			System.out.println("Alteração cancelada.");

		System.out.print("Pressione ENTER");
		console.nextLine();
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
