package me.ninjoh.ninconsole;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ServerConsole
{
    public static void sendConsoleCommand(String command) throws UnsupportedEncodingException
    {
        JSONObject object = new JSONObject();

        object.put("name", "server.run_command");
        object.put("key", NinConsole.getJSONAPI().getKey("server.run_command"));
        object.put("username", NinConsole.getJSONAPI().getUsername());

        JSONArray arguments = new JSONArray();
        arguments.put(command);

        List<String> list = new ArrayList<String>();
        list.add(command);

        object.put("arguments", list);


        // the string that should be written to the JSONAPI
        // this subscribes to the stream in JSONAPI
        String outString = "/api/2/call?json=" + URLEncoder.encode(object.toString(), "UTF-8");

        // print (send) it to the JSONAPI
        NinConsole.getJSONAPI().out().println(outString);
    }
}
