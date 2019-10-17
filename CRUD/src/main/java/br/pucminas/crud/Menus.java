package br.pucminas.crud;


import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Classe que gerencia menus de diversos tipos.
 */
public class Menus
{
	public static boolean contem(int opcao, int[] arranjo)
	{
		boolean contem = false;
		
		for (int i = 0; !contem && i < arranjo.length; i++)
		{
			contem = ( arranjo[i] == opcao );
		}
		
		return contem;
	}
	
	public static void gerarCabecalhoEOpcoes(String nome, String[] opcoes)
	{
		IO.println("\n\n------------------------------------------------");
		IO.println("                    MENU " + nome);
		IO.println("------------------------------------------------\n");
		for (int i = 0; i < opcoes.length; i++)
		{
			IO.println((i + 1) + " - " + opcoes[i]);
		}
		IO.println("0 - sair");
		IO.println("");
	}
	
	public static <T> T menu(String nome, String[] opcoes, Function<Integer, T> gerenciarOpcoes, int[] opcoesDeSaida, boolean inverterLogicaDeSaida)
	{
		T result = null;
		int opcao = 0;
		
		do
		{
			gerarCabecalhoEOpcoes(nome, opcoes);
			opcao = IO.readint("Opção: ");
			
			IO.println("");
			
			if (opcao < 0 || opcao > opcoes.length)
			{
				IO.println("Opção inválida");
			}
			
			else if (opcao != 0)
			{
				result = gerenciarOpcoes.apply(opcao);
			}
			
			IO.println("\n--------------------------------------------\n");
	
		} while (contem(opcao, opcoesDeSaida) == inverterLogicaDeSaida);
		
		return result;
	}
	
	public static <T> T menu(String nome, String[] opcoes, Function<Integer, T> gerenciarOpcoes, int[] opcoesDeSaida)
	{
		return menu(nome, opcoes, gerenciarOpcoes, opcoesDeSaida, false);
	}
	
	public static <T> T menu(String nome, String[] opcoes, Function<Integer, T> gerenciarOpcoes)
	{
		return menu(nome, opcoes, gerenciarOpcoes, new int[] { 0 });
	}

	public static void menu(String nome, String[] opcoes, Runnable[] acoes, int[] opcoesDeSaida, boolean inverterLogicaDeSaida)
	{
		menu(nome, opcoes,
			(opcao) ->
			{
				acoes[opcao - 1].run();
				return null;
			},
			opcoesDeSaida,
			inverterLogicaDeSaida
		);
	}

	public static void noBackMenu(String nome, String[] opcoes, Runnable[] acoes)
	{
		menu(nome, opcoes, acoes, new int[] {  }, true);
	}

	public static void menu(String nome, String[] opcoes, Runnable[] acoes, int[] opcoesDeSaida)
	{
		menu(nome, opcoes, acoes, opcoesDeSaida, false);
	}

	public static void menu(String nome, String[] opcoes, Runnable[] acoes)
	{
		menu(nome, opcoes, acoes, new int[] { 0 });
	}
	
	public static <T> T menu(String nome, String[] opcoes, Supplier<T>[] acoes, int[] opcoesDeSaida, boolean inverterLogicaDeSaida)
	{
		return menu(nome, opcoes,
			(opcao) ->
			{
				return acoes[opcao - 1].get();
			},
			opcoesDeSaida,
			inverterLogicaDeSaida
		);
	}
	
	public static <T> T noBackMenu(String nome, String[] opcoes, Supplier<T>[] acoes, int[] opcoesDeSaida)
	{
		return menu(nome, opcoes, acoes, new int[] {  }, true);
	}
	
	public static <T> T menu(String nome, String[] opcoes, Supplier<T>[] acoes, int[] opcoesDeSaida)
	{
		return menu(nome, opcoes, acoes, opcoesDeSaida, false);
	}
	
	public static <T> T menu(String nome, String[] opcoes, Supplier<T>[] acoes)
	{
		return menu(nome, opcoes, acoes, new int[] { 0 });
	}
	
}