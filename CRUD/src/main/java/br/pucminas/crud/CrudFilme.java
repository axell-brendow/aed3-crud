package br.pucminas.crud;

public class CrudFilme extends CRUDAbstract<Filme>
{	
	public CrudFilme()
	{
		super(Filme.class);
	}

	@Override
	public void printarMenu()
	{
		Menus.menu("FILMES", "",
			new String[]
			{ "Listar filmes", "Buscar filme", "Incluir filme", "Excluir filme", "Modificar filme" },
			new Runnable[]
			{
				() -> listarEPrintar(),
				() -> buscarFilme(),
				() -> incluirFilme(),
				() -> excluirFilme(),
				() -> alterarFilme(),
			}
		);
	}

	//------------------METODOS COM FILME

	/**
	 * Busca um filme presente no arquivo
	 */
	public void buscarFilme()
	{
		IO.println("\nBUSCA");

		int id = IO.readint("ID: ");
		
		if (id <= 0) return;

		buscarEPrintar(id);
	}

	/**
	 * Cadastra um novo filme
	 */
	public void incluirFilme()
	{
		String titulo;
		int idCategoria;
		short ano;
		char confirma;

		IO.println("\nINCLUSÃO");

		titulo = IO.readLine("Título: ");
		idCategoria = IO.readint("Categoria: ");
		
		//Verificar se existe a categoria
		if (CRUD.crudCategoria.buscar(idCategoria) == null)
		{
			IO.println("O id da Categoria não existe");
			
			String resposta = IO.readLine("\nGostaria de Inserir a categoria? ");
			
			if (resposta.isEmpty()) resposta = "n";
			
			confirma = resposta.charAt(0);

			if(confirma == 's' || confirma == 'S')
			{	
				CrudCategoria aSerInserido = new CrudCategoria();
				aSerInserido.incluirCategoria();
			}
		}

		ano = IO.readshort("Ano: ");

		String resposta = IO.readLine("\nConfirma inclusão? ");
		
		if (resposta.isEmpty()) resposta = "n";
		
		confirma = resposta.charAt(0);

		if(confirma == 's' || confirma == 'S')
		{
			Filme aSerIncluido = new Filme(titulo, idCategoria, ano);
			int id = incluir(aSerIncluido);
			IO.println("Filme incluído com ID: " + id);
		}
		else
			IO.println("Filme não incluído.");

		IO.pause();
	}

	/**
	 * Exclui um filme do arquivo
	 */
	public void excluirFilme()
	{
		int id;
		char confirma;

		IO.println("\nEXCLUSÃO");

		id = IO.readint("ID: ");

		if (id <= 0) return;

		Filme aSerExcluido = buscar(id);

		if (aSerExcluido != null)
		{
			IO.println(aSerExcluido);

			String resposta = IO.readLine("\nConfirma exclusão? ");
			
			if (resposta.isEmpty()) resposta = "n";
			
			confirma = resposta.charAt(0);
			
			if(confirma=='s' || confirma=='S')
				if(excluir(id))
					IO.println("Filme excluído.");
				else
					IO.println("Exclusão cancelada.");
		}
		else
			IO.println("Filme não encontrado");

		IO.pause();
	}

	/**
	 * Altera um filme no arquivo
	 */
	public void alterarFilme()
	{
		IO.println("\nALTERAÇÃO");

		int id;
		char confirma;

		id = IO.readint("ID: ");
		
		if (id <= 0) return;

		Filme aSerAlterado = buscar(id);

		if(aSerAlterado != null)
			IO.println(aSerAlterado);
		else
		{
			IO.println("Filme não encontrado");
			return;
		}

		IO.println();

		String titulo;
		int idCategoria;
		short ano;

		titulo = IO.readLine("Novo título: ");
		if (titulo.equals(" "))
			titulo = aSerAlterado.getTitulo();

		idCategoria = IO.readint("Nova categoria: ");
		if (idCategoria != 0)
			idCategoria = aSerAlterado.getCategoria();

		ano = IO.readshort("Novo Ano: ");

		if (ano == -1) ano = aSerAlterado.getAno();

		String resposta = IO.readLine("\nConfirma alteração? ");
		
		if (resposta.isEmpty()) resposta = "n";
		
		confirma = resposta.charAt(0);

		if(confirma == 's' || confirma == 'S')
		{
			aSerAlterado = new Filme(titulo, idCategoria, ano);
			aSerAlterado.setID(id);

			if (alterar(aSerAlterado))
				IO.println("Filme alterado com sucesso.");
			else
				IO.println("Erro ao alterar filme.");
		}
		else
			IO.println("Alteração cancelada.");

		IO.pause();
	}
}
