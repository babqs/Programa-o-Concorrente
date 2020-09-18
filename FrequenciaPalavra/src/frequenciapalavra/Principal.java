package frequenciapalavra;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Principal {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        ArrayList<String> tarefa = new ArrayList<>();
        FrequenciaPalavra f = new FrequenciaPalavra(tarefa);
        
       ForkJoinPool pool = new ForkJoinPool();
       pool.invoke(f);
     
    }
}
