package Common;

import java.io.IOException;
import java.util.Properties;

public class Const {
    Properties configFile;
    public static String fileToRead;
    public static String fileFeedback;

    /**
     * Construtor vazio
     * @throws IOException caso o ficheiro de constantes esteja corrompido ou não exista
     */
    public Const() throws IOException {
        configFile = new Properties();
        configFile.load(this.getClass().getClassLoader().getResourceAsStream("Constantes.cfg"));
    }

    /**
     * Getter de constante do ficheiro
     * @param id nome da constante
     * @return valor da constante no Constantes.cfg
     */
    public String getConst(String id) {
        return this.configFile.getProperty(id);
    }

    /**
     * Inicia constantes
     */
    public void initConsts() {
        fileToRead=getConst("fileLogs");
        fileFeedback=getConst("fileFeedback");
    }
}
