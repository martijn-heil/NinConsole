package me.ninjoh.ninconsole;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SocketWatcher implements Runnable
{
    private volatile boolean cancelled;


    public void run()
    {
        while (!cancelled)
        {
            String inputLine;
            try
            {
                if(NinConsole.getJSONAPI().isClosed())
                {
                    continue;
                }

                while ((inputLine = NinConsole.getJSONAPI().in().readLine()) != null)
                {
                        JSONObject jo = new JSONObject(inputLine);
                        if (jo.getString("result").equals("error"))
                        {
                            if (jo.getJSONObject("error").getString("message").contains("Invalid username, password or salt"))
                            {
                                System.out.println("\nError: Invalid credentials supplied");
                                System.exit(-1);
                                return;
                            }
                            else
                            {
                                System.out.println("A JSON API request failed: " + jo.getJSONObject("error").getString("message") +
                                        "(" + jo.getJSONObject("error").getInt("code") + ")");
                            }
                        }


                        // this will be called on every message send by JSONAPI
                        String s = new JSONObject(inputLine).getJSONObject("success").getString("line");

                        // Temporary hard coded thing.. bloody console spam.
                        if (!s.contains("[JSONAPI] Task") &&
                                !s.contains("[JSONAPI] [API Request] " + NinConsole.getJSONAPI().getUsername() + " requested:") &&
                                !s.contains("[JSONAPI] [Stream Request]"))
                        {
                            System.out.print(s);
                        }
                }
            }
            catch (IOException | JSONException ignored)
            {

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
