package me.ninjoh.ninconsole;


public class ExceptionHandler
{
    public static void log(Exception e)
    {
        if(NinConsole.getDebug())
        {
            e.printStackTrace();
        }
        else
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
