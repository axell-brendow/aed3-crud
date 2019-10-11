package br.pucminas.crud;

public class CrudCategoria extends CRUDAbstract<Categoria>
{	
	public CrudCategoria()
	{
		super(Categoria.class);
	}

	@Override
	public void printarMenu()
	{
		Menus.menu("CATEGORIAS", "",
			new String[]
			{
				"Listar categorias", "Buscar categoria", "Incluir categoria",
				"Excluir categoria", "Modificar categoria" },
			new Runnable[]
			{
				() -> listarEPrintar(),
				() -> buscarCategoria(),
				() -> incluirCategoria(),
				() -> excluirCategoria(),
				() -> alterarCategoria()
			}
		);
	}

	//------------------METODOS COM CATEGORIA

	/**
	 * Busca uma categoria presente no arquivo
	 */
	public void buscarCategoria()
	{
		IO.println("\nBUSCA");

		int id = IO.readint("ID: ");
		
		if (id <= 0) return;

		buscarEPrintar(id);
	}

	/**
	 * Cadastra uma nova categoria
	 */
	public void incluirCategoria()
	{
		String nome;
		char confirma;

		IO.println("\nINCLUSÃO CATEGORIA");

		nome = IO.readLine("Nome: ");
		
		String resposta = IO.readLine("\nConfirma inclusão? ");
		
		if (resposta.isEmpty()) resposta = "n";
		
		confirma = resposta.charAt(0);

		if(confirma == 's' || confirma == 'S')
		{
			Categoria aSerIncluido = new Categoria(nome);
			int id = incluir(aSerIncluido);
			IO.println("Categoria incluída com ID: " + id);
		}
		else
			IO.println("Categoria não incluída.");

		IO.pause();
	}

	/**
	 * Exclui um categoria do arquivo
	 */
	public void excluirCategoria()
	{
		int id;
		char confirma;

		IO.println("\nEXCLUSÃO");

		id = IO.readint("ID: ");

		if (id <= 0) return;

		Categoria aSerExcluida = buscar(id);

		if (aSerExcluida != null)
		{
			IO.println(aSerExcluida);

			String resposta = IO.readLine("\nConfirma exclusão? ");
			
			if (resposta.isEmpty()) resposta = "n";
			
			confirma = resposta.charAt(0);
			
			if(confirma=='s' || confirma=='S')
				if(excluir(id))
					IO.println("Categoria excluída.");
				else
					IO.println("Exclusão cancelada.");
		}
		else
			IO.println("Categoria não encontrada");

		IO.pause();
	}

	/**
	 * Altera um categoria no arquivo
	 */
	public void alterarCategoria()
	{
		IO.println("\nALTERAÇÃO");

		int id;
		char confirma;

		id = IO.readint("ID: ");
		
		if (id <= 0) return;

		Categoria aSerAlterada = buscar(id);

		if(aSerAlterada != null)
			IO.println(aSerAlterada);
		else
		{
			IO.println("Categoria não encontrada");
			return;
		}

		IO.println();

		String nome = IO.readLine("Novo nome: ");
		if (nome.equals(" "))
			nome = aSerAlterada.getNome();

		String resposta = IO.readLine("\nConfirma alteração? ");
		
		if (resposta.isEmpty()) resposta = "n";
		
		confirma = resposta.charAt(0);

		if(confirma == 's' || confirma == 'S')
		{
			aSerAlterada = new Categoria(nome);
			aSerAlterada.setID(id);

			if (alterar(aSerAlterada))
				IO.println("Categoria alterada com sucesso.");
			else
				IO.println("Erro ao alterar categoria.");
		}
		else
			IO.println("Alteração cancelada.");

		IO.pause();
	}
}
