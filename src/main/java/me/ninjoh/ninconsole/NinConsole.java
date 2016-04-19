package me.ninjoh.ninconsole;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.*;
import java.util.Properties;

public class NinConsole
{
    private static JSONAPI JSONAPI;
    private static boolean DEBUG;


    public static void main(String[] args) throws UnirestException, IOException
    {
        try
        {
            Properties prop1 = new Properties();
            prop1.load(NinConsole.class.getResourceAsStream("/info.properties"));

            System.out.println("NinConsole v" + prop1.getProperty("project.version") + " by Ninjoh\n");

            Properties prop = new Properties();

            FileInputStream file;

            //the base folder is ./, the root of the main.properties file
            String path = "./main.properties";

            try
            {
                file = new FileInputStream(path);
            }
            catch (FileNotFoundException e)
            {
                System.out.println("Error: could not find main.properties, terminating..");
                System.exit(-1);
                return;
            }

            prop.load(file);
            file.close();

            String host = prop.getProperty("host");
            int port = Integer.parseInt(prop.getProperty("port"));
            DEBUG = Boolean.parseBoolean(prop.getProperty("debug"));

            String username;
            String password;

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Please enter your username: ");
            username = reader.readLine();

            if (System.console() == null) // System.console() may return null in IDE's.
            {
                // Will display the written password as plain text.
                System.out.print("Please enter your password: ");
                password = reader.readLine();
            }
            else
            {
                // Won't display the writen password as plain text.
                password = new String(System.console().readPassword("Please enter your password: "));
            }


            JSONAPI = new JSONAPI(host, port, username, password, "OPSaLtYuUs");

            JSONAPI.subscribe("console", true);
            JSONAPI.subscribe("chat", true);


            // Spawn CLI input reader.
            Thread thread = new Thread(new Reader());
            thread.start();

            Thread connWatcher = new Thread(new ConnectionWatcher());
            connWatcher.start();

            Thread socketWatcher = new Thread(new SocketWatcher());
            socketWatcher.start();

            while (true) // Make sure the process does not exit
            {

            }
        }
        catch(Exception e)
        {
            ExceptionHandler.log(e);
        }
    }

    public static JSONAPI getJSONAPI()
    {
        return JSONAPI;
    }


    public static boolean getDebug()
    {
        return DEBUG;
    }
}
