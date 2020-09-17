package frequenciapalavra;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Principal {

    public static void main(String[] args) throws FileNotFoundException, IOException {
         
        FrequenciaPalavra f = new FrequenciaPalavra();
        
        f.compute();
    }
}
