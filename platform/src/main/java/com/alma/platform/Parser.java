package com.alma.platform;

import com.alma.platform.exceptions.PropertyNotFoundException;
import com.alma.platform.plugins.Plugin;

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

    /**
     * Méthode qui parse un fichier de config d'extensions
     * @param formatFichier
     * @return
     * @throws IOException
     * @throws PropertyNotFoundException
     */
	public Map<String, Plugin> parseIt(String formatFichier) throws IOException, PropertyNotFoundException {
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

    /**
     * Méthode qui charge un fichier de config de la plateforme
     * @param filename
     * @return
     * @throws IOException
     */
    public Properties loadConfig(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        Properties config = new Properties();
        config.load(fileReader);
        return config;
    }

    /**
     * Méthode qui assure si un ensemble de propriété contient les propriétés obligatoires
     * @param properties
     * @return
     * @throws PropertyNotFoundException
     */
    private boolean isValidated(Properties properties) throws PropertyNotFoundException {
        if(! properties.containsKey("name")) {
            throw new PropertyNotFoundException("Property not found : name");
        }

        if(! properties.containsKey("class")) {
            throw new PropertyNotFoundException("Property not found : class");
        }

        if(! properties.containsKey("interface")) {
            throw new PropertyNotFoundException("Property not found : interface");
        }

        if(! properties.containsKey("directory")) {
            throw new PropertyNotFoundException("Property not found : directory");
        }

        return true;
    }
}
