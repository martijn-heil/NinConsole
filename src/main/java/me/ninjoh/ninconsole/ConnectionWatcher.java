package me.ninjoh.ninconsole;


public class ConnectionWatcher implements Runnable
{
    private volatile boolean cancelled = false;
    private volatile boolean lastConnWasLost;

    public void run()
    {
        while (!cancelled)
        {
            try
            {
                Thread.sleep(10000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            boolean success = NinConsole.getJSONAPI().reConnect();

            if(!success)
            {
                if(lastConnWasLost) // This is an attempt
                {
                    System.out.println("\n Failed attempt. trying again in 10 seconds..");
                }
                else // First time we notice the connection is lost.
                {
                    lastConnWasLost = true;
                    System.out.println("\n Connection lost. re-attempting every 10 seconds..");
                }
            }
            else if(lastConnWasLost)
            {
                System.out.println("\n Connection is re-established.\n");
                lastConnWasLost = false;
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
