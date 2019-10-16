package br.pucminas.crud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO
{
	private static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	public static void print(Object msg)
	{
		System.out.print(msg);
	}
	
	public static void println(Object msg)
	{
		print(msg + LINE_SEPARATOR);
	}
	
	public static void println()
	{
		println("");
	}
	
	/**
	 * Lê uma linha do console.
	 * 
	 * @return {@code null} caso ocorra um erro na leitura. Caso contrário,
	 * retorna a linha lida.
	 */
	public static String readLine()
	{
		String line = null;
		
		try
		{
			line = console.readLine();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return line;
	}
	
	/**
	 * Mostra a mensagem na tela e então lê uma linha do console.
	 * 
	 * @param msg Mensagem a ser mostrada antes da leitura.
	 * 
	 * @return {@code null} caso ocorra um erro na leitura. Caso contrário,
	 * retorna a linha lida.
	 */
	public static String readLine(Object msg)
	{
		print(msg);
		return readLine();
	}
	
	/**
	 * Mostra a mensagem na tela e então lê uma linha do console pausando-o.
	 * 
	 * @param msg Mensagem a ser mostrada antes da leitura.
	 */
	public static void pause(Object msg)
	{
		readLine(msg);
	}
	
	/**
	 * Mostra a mensagem na tela e então lê uma linha do console pausando-o.
	 * 
	 * @param msg Mensagem a ser mostrada antes da leitura.
	 */
	public static void pause()
	{
		pause("Pressione ENTER");
	}

	/**
	 * Lê uma linha do console e converte-a para um inteiro.
	 * 
	 * @return {@code -1} caso ocorra um erro na leitura ou na conversão.
	 * Caso contrário, retorna o inteiro correspondente.
	 */
	public static int readint()
	{
		int value = -1;
		String line = readLine();
		
		try
		{
			if (line != null) value = Integer.parseInt(line);
		}
		
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		return value;
	}

	/**
	 * Mostra a mensagem na tela e então lê uma linha do console e converte-a
	 * para um inteiro.
	 * 
	 * @param msg Mensagem a ser mostrada antes da leitura.
	 * 
	 * @return {@code -1} caso ocorra um erro na leitura ou na conversão.
	 * Caso contrário, retorna o inteiro correspondente.
	 */
	public static int readint(Object msg)
	{
		print(msg);
		return readint();
	}

	/**
	 * Lê uma linha do console e converte-a para um short.
	 * 
	 * @return {@code -1} caso ocorra um erro na leitura ou na conversão.
	 * Caso contrário, retorna o short correspondente.
	 */
	public static short readshort()
	{
		short value = -1;
		String line = readLine();
		
		try
		{
			if (line != null) value = Short.parseShort(line);
		}
		
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		return value;
	}

	/**
	 * Mostra a mensagem na tela e então lê uma linha do console e converte-a
	 * para um short.
	 * 
	 * @param msg Mensagem a ser mostrada antes da leitura.
	 * 
	 * @return {@code -1} caso ocorra um erro na leitura ou na conversão.
	 * Caso contrário, retorna o short correspondente.
	 */
	public static short readshort(Object msg)
	{
		print(msg);
		return readshort();
	}
}
