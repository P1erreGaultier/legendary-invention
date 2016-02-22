package com.alma.plateform;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Parser {

	String aLine;

	public Map<String, Plugin> parseIt(String formatFichier) throws IOException{

		FileReader fileReader = new FileReader(formatFichier);
		BufferedReader reader = new BufferedReader(fileReader);

        Map<String, Plugin> res = new HashMap<>();
		int i = 0;
        Properties properties = new Properties();
        while (reader.ready()) {
			aLine = reader.readLine();
            Plugin plug = new Plugin();
			String[] OneAttributeOneValue = aLine.split(";");
            for (int c =0; c< OneAttributeOneValue.length; c++){
                properties.setProperty(OneAttributeOneValue[c].split("=")[0],OneAttributeOneValue[c].split("=")[1]);
            }

			plug.setProperties(properties);
            res.put(plug.getProperties().getProperty("name"),plug);
		}
		reader.close();
		return res;
	}



}
