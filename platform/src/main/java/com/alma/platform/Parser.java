package com.alma.platform;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Classe représentant un parseur de fichier de configuration
 */
public class Parser {

	public Map<String, Plugin> parseIt(String formatFichier) throws IOException{
        String aLine;

        FileReader fileReader = new FileReader(formatFichier);
		BufferedReader reader = new BufferedReader(fileReader);

        Map<String, Plugin> res = new HashMap<>();

        Properties properties = new Properties();
        while (reader.ready()) {
			aLine = reader.readLine();
            Plugin plug = new Plugin();
			String[] OneAttributeOneValue = aLine.split(";");
            for(int c = 0; c < OneAttributeOneValue.length; c++){
				String[] property = OneAttributeOneValue[c].split("=");
                properties.setProperty(property[0], property[1]);
            }

			plug.setProperties(properties);
            res.put(plug.getProperties().getProperty("name"), plug);
		}
		reader.close();
		return res;
	}

    public Properties parseConfig(String configFile) throws IOException {
        Properties config = new Properties();

        FileReader fileReader = new FileReader(configFile);
        BufferedReader reader = new BufferedReader(fileReader);

        config.load(reader);

        return config;
    }

}
