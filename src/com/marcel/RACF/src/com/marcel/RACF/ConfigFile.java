package com.marcel.RACF.src.com.marcel.RACF;

import java.util.List;

public class ConfigFile {

	List<ConfigTokens.ConfigObject> objectList;

	public ConfigFile(String filename) throws Exception {
		objectList = ConfigReader.ReadConfigFile(filename);
	}

	public void ReadFromFile(String filename) throws Exception {
		objectList = ConfigReader.ReadConfigFile(filename);
	}

	public void WriteToFile(String filename)
	{
		ConfigReader.WriteConfigFile(filename, objectList);
	}

	public void DisplayTokens()
	{
		ConfigReader.PrintTokens(objectList);
	}

	public ConfigTokens.ConfigVariableObjectType GetConfigValue(String configPath, boolean resolveReference) throws Exception {
		return ConfigReader.GetValue(configPath, objectList, resolveReference);
	}

	public ConfigTokens.ConfigVariableObjectType GetConfigValue(String configPath) throws Exception {
		return ConfigReader.GetValue(configPath, objectList, true);
	}

	public String GetConfigValueString(String configPath, boolean resolveReference) throws Exception {
		return ConfigReader.GetValue(configPath, objectList, resolveReference).toString();
	}

	public String GetConfigValueString(String configPath) throws Exception {
		return ConfigReader.GetValue(configPath, objectList, true).genericToString();
	}

}
