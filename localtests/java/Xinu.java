/* Copyright (C) 2009, Marquette University.  All rights reserved. */
import java.util.Scanner;

/**
 * Helper class for modeling Xinu I/O functions
 */

public class Xinu
{
    public static int readint()
    {
	Scanner scanner = new Scanner(System.in);
	return scanner.nextInt();
    }

    public static void printint(int x)
    {
	System.out.println(x);
    }

    public static void print(String s)
    {
	System.out.print(s);
    }

    public static void println()
    {
	System.out.println();
    }

	public static void yield()
	{
		Thread thisThread = Thread.currentThread();

		try
		{
			thisThread.yield();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	public static void sleep(int time)
	{
		Thread thisThread = Thread.currentThread();
		
		try
		{
			thisThread.sleep(time);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	public static void threadCreate(Thread t)
	{
		t.start();
	}


    
}
