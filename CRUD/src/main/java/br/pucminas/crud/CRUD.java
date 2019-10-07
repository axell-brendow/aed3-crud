package br.pucminas.crud;

public class CRUD
{
	public static CrudFilme crudFilme = new CrudFilme();
	public static CrudCategoria crudCategoria = new CrudCategoria();
	
	public static void main(String[] args)
	{    
		try
		{
			escolherMenus();
			crudFilme.fechar();
		}
		
		catch(Exception e)
		{
	   		e.printStackTrace();
		}        
	}    
	
	public static void limparArquivo()
	{
		crudFilme.cleanup();
	}
	
	//------------------METODOS PARA PRINTAR O MENU
	
	/**
	 * Metodo para escolher sub menus principal
	 */
	public static void escolherMenus()
	{
		Menus.menu("PRINCIPAL", "",
			new String[]
			{ "Filmes", "Categorias", "Povoar Banco de Dados", "Limpar Banco de Dados" },
			new Runnable[]
			{
				() -> crudFilme.printarMenu(),
				() -> crudCategoria.printarMenu(),
				() -> povoar(),
				() -> limparArquivo(),
			}
		);
	}
	
	/**
	 * Gerar uma lista de filmes
	 */
	public static void povoar()
	{
		try
		{
			//arqFilmes.incluir(new Filme("The Godfather","Crime",(short)1972));
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}	
}
