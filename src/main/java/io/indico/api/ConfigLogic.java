package io.indico.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import org.ini4j.Ini;

public class ConfigLogic{

    public ConfigLogic() {
    }

    public String getVariable(String sectionHeader, String varName,
        String envVarName, Ini iniFile) {
        String variable = this.getEnvVariable(envVarName);

        if (variable == null && iniFile != null) {
            Ini.Section section = iniFile.get(sectionHeader);
            if (section != null) {
                variable = section.get(varName);
            }
        }

        return variable;
    }

    public String getEnvVariable(String envVarName) {
        return System.getenv(envVarName);
    }
}
