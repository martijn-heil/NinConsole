package me.ninjoh.ninconsole;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader implements Runnable
{
    private volatile boolean cancelled;


    public void run()
    {
        while (!cancelled)
        {
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                ServerConsole.sendConsoleCommand(reader.readLine());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void cancel()
    {
        cancelled = true;
    }

    public boolean isCancelled()
    {
        return cancelled;
    }
}
