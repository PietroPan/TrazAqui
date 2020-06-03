package Common;

import java.io.IOException;
import java.util.Properties;

public class Const {
    Properties configFile;
    public static String fileToRead;

    public Const() throws IOException {
        configFile = new Properties();
        configFile.load(this.getClass().getClassLoader().getResourceAsStream("Constantes.cfg"));
    }

    public String getConst(String id) {
        return this.configFile.getProperty(id);
    }

    public void initConsts() {
        fileToRead=getConst("fileLogs");
    }
}
