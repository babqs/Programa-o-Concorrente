package frequenciapalavra;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FrequenciaPalavra extends RecursiveTask<ArrayList> {

    FileReader arquivo;
    FileReader stopwords;
    String maiusculo, maiusculoSW;
    String linha, linhaSW;
    ArrayList<String> lista;
    int inicio, fim;
    Map<String, Integer> contar = new TreeMap<>();

    public FrequenciaPalavra(ArrayList<String> lista, int inicio, int fim) throws FileNotFoundException {
        arquivo = new FileReader("texto.txt");
        stopwords = new FileReader("stopwords.txt");
        lista = new ArrayList<>();
        this.inicio = inicio;
        this.fim = fim;
    }

    public FrequenciaPalavra(ArrayList<String> lista) throws FileNotFoundException {
        this(lista, 0, lista.size());
    }

    public void leArquivo() {
        try (BufferedReader buff = new BufferedReader(arquivo)) {
            BufferedReader buffSW = new BufferedReader(stopwords);
            //lê a primeira linha do arquivo
            linha = buff.readLine();
            linhaSW = buffSW.readLine();
            while (linha != null) {
                modificaArquivo();
                //lê as próximas linhas do arquivo
                linha = buff.readLine();
            }
            buff.close();

            imprimeFrequencia();
        } catch (IOException ex) {
            Logger.getLogger(FrequenciaPalavra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modificaArquivo() {
        //transforma as letras em maiúsculo
        maiusculo = linha.toUpperCase();
        maiusculoSW = linhaSW.toUpperCase();
        
        String caracteres = maiusculo.replaceAll("\\.", "").replaceAll("\\,", "").replaceAll("\\)", "").replaceAll("\\(", "").replaceAll("\\:", "").replaceAll("\"", "");

        //separa em palavras
        String[] splited = caracteres.split(" ", 0);
        String[] splitedSW = maiusculoSW.split(" ", 0);

        List<String> lista = asList(splited);
        List<String> listaSW = asList(splitedSW);
        
        calcularFrequencia((ArrayList<String>) lista, 0, lista.size());
    }
    
    //FILTER PARA REMOVER AS STOPWORDS
    public static List<String> removeStopWords(List<String> lista, List<String> listaSW){
        return lista.stream().filter((p) -> !listaSW.contains(p)).collect(Collectors.toList());
    }

    public Integer calcularFrequencia(ArrayList<String> lista, int inicio, int fim) {
        //conta a frequencia de cada palavra
        for (int i = 0; i < lista.size(); i++) {
            String p = lista.get(i);
            Integer frequencia = contar.get(p);
            if (frequencia != null) {
                contar.put(p, frequencia + 1);
            } else {
                contar.put(p, 1);
            }
        }
        return 0;
    }

    public void imprimeFrequencia() {
        //imprime a frequencia de cada palavra e em ordem alfabética
        contar.entrySet().forEach((f) -> {System.out.println(f.getKey() + "\t Frequência: " + f.getValue());});   
    }

    @Override
    protected ArrayList<FrequenciaPalavra> compute() {
        leArquivo();
        int m;
        if (inicio < fim) {
            try {
                m = calcularFrequencia(lista, 0, lista.size());
                FrequenciaPalavra f1 = new FrequenciaPalavra(lista, 0, m - 1);
                FrequenciaPalavra f2 = new FrequenciaPalavra(lista, m + 1, lista.size());

                f1.fork();
                f2.fork();

                ArrayList<FrequenciaPalavra> r1 = f1.join();
                ArrayList<FrequenciaPalavra> r2 = f2.join();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(FrequenciaPalavra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new ArrayList<>();
    }
}