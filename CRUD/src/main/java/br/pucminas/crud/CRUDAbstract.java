package br.pucminas.crud;

import java.util.ArrayList;

public abstract class CRUDAbstract<ENTIDADE extends Registro>
{
	// ------------------------ Atributos
	
	protected Arquivo<ENTIDADE> arquivo;
	protected String nomeEntidade;

	// ------------------------ Construtores
	
	public CRUDAbstract(Class<ENTIDADE> _class)
	{
		nomeEntidade = _class.getSimpleName();
		
		try
		{
			arquivo = new Arquivo<ENTIDADE>(_class, nomeEntidade + ".db");
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// ------------------------ Métodos abstratos (interface)
	
	public abstract void printarMenu();
	
	// ------------------------ Métodos
	
	public void fechar()
	{
		try
		{
			arquivo.fecha();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void cleanup()
	{
		try
		{
			arquivo.cleanup();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<ENTIDADE> listar()
	{
		ArrayList<ENTIDADE> lista = new ArrayList<>();
		
		try
		{
			lista = arquivo.listar();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public ArrayList<ENTIDADE> listarEPrintar()
	{
		ArrayList<ENTIDADE> lista = listar();
		
		lista.forEach( (entidade) -> IO.println(entidade) );

		if (lista.isEmpty()) IO.println(nomeEntidade + " esta vazia!");
		
		IO.pause();
		
		return lista;
	}
	
	public ENTIDADE buscar(int id)
	{
		return arquivo.buscar(id);
	}
	
	public ENTIDADE buscarEPrintar(int id)
	{
		ENTIDADE entidade = buscar(id);

		if(entidade != null)
			IO.println(entidade);
		else
			IO.println("Entidade " + nomeEntidade + " não encontrada");

		IO.pause();
		
		return entidade;
	}
	
	public int incluir(ENTIDADE entidade)
	{
		return arquivo.incluir(entidade);
	}
	
	public boolean excluir(int id)
	{
		return arquivo.excluir(id);
	}
	
	public boolean alterar(ENTIDADE entidade)
	{
		return arquivo.alterar(entidade);
	}
}
