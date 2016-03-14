package com.alma.platform;

import com.alma.platform.exceptions.PropertyNotFound;
import com.alma.platform.plugins.Plugin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Classe représentant un parseur de fichier de configuration
 */
public class Parser {

	public Map<String, Plugin> parseIt(String formatFichier) throws IOException, PropertyNotFound {
        String aLine;

        FileReader fileReader = new FileReader(formatFichier);
		BufferedReader reader = new BufferedReader(fileReader);

        Map<String, Plugin> res = new HashMap<>();

        while (reader.ready()) {
            Properties properties = new Properties();
            Plugin plug = new Plugin();
            aLine = reader.readLine();

			String[] OneAttributeOneValue = aLine.split(";");
            for(int c = 0; c < OneAttributeOneValue.length; c++){
				String[] property = OneAttributeOneValue[c].split("=");
                properties.setProperty(property[0], property[1]);
            }
            isValidated(properties);
			plug.setProperties(properties);
            res.put(plug.getProperties().getProperty("name"), plug);
		}
		reader.close();
		return res;
	}

    public boolean isValidated(Properties properties) throws PropertyNotFound {
        if(! properties.containsKey("name")) {
            throw new PropertyNotFound("Property not found : name");
        }

        if(! properties.containsKey("class")) {
            throw new PropertyNotFound("Property not found : class");
        }

        if(! properties.containsKey("interface")) {
            throw new PropertyNotFound("Property not found : interface");
        }

        if(! properties.containsKey("directory")) {
            throw new PropertyNotFound("Property not found : directory");
        }

        return true;
    }
}
