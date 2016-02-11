package com.alma.plateform;

import com.alma.plateform.Plugin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Parser {

	String fileName;
	String aLine;

    public static void main(String[] args) {

        Parser myParser = new Parser();
        Plugin p= new Plugin();
        try {
            p = myParser.parseIt("formatFichier.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(p.getProperties().getProperty("class"));

    }


	public Plugin parseIt(String formatFichier) throws IOException{

		FileReader fileReader = new FileReader(fileName);
		BufferedReader reader = new BufferedReader(fileReader);
		Plugin plug = new Plugin();
		//aLine = reader.readLine();
		int i = 0;
        Properties properties = new Properties();
        while (reader.ready()) {

			aLine = reader.readLine();

			String[] OneAttributeOneValue = aLine.split(";");
            for (int c =0; c< OneAttributeOneValue.length; c++){
                properties.setProperty(OneAttributeOneValue[c].split("=")[1],OneAttributeOneValue[c].split("=")[2]);
            }

			plug.setProperties(properties);
		}
		reader.close();
		return plug;
	}



}
