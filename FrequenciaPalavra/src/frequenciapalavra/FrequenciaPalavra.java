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

public class FrequenciaPalavra extends RecursiveTask {

    FileReader arquivo;
    FileReader stopwords;
    String maiusculo, maiusculoSW;
    String linha, linhaSW;
    Map<String, Integer> contar = new TreeMap<>();

    public FrequenciaPalavra() throws FileNotFoundException {
        arquivo = new FileReader("texto.txt");
        stopwords = new FileReader("stopwords.txt");
    }

    public void modificaArquivo() {
        //transforma as letras em maiúsculo
        maiusculo = linha.toUpperCase();
        maiusculoSW = linhaSW.toUpperCase();
        String caracteres = maiusculo;

        caracteres = caracteres.replaceAll("\\.", "");
        caracteres = caracteres.replaceAll("\\,", "");
        caracteres = caracteres.replaceAll("\\)", "");
        caracteres = caracteres.replaceAll("\\(", "");
        caracteres = caracteres.replaceAll("\\:", "");
        caracteres = caracteres.replaceAll("\"", "");
        // System.out.println(caracteres);

        //separa em palavras
        String[] splited = caracteres.split(" ", 0);
        String[] splitedSW = maiusculoSW.split(" ", 0);

        ArrayList<String> lista = new ArrayList<>();
        List<String> listaSW = asList(splitedSW);

        //remove as stopwords
        for (String p : splited) {
            String compara = p;
            if (!listaSW.contains(compara)) {
                lista.add(p);
            }
        }
        calcularFrequencia(lista);
    }

    public void calcularFrequencia(ArrayList<String> lista) {
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
    }

    public void imprimeFrequencia() {
        //imprime uma nova lista sem as stopwords
        contar.entrySet().forEach((f) -> {
            System.out.println(f.getKey() + "\t Frequência: " + f.getValue());
        });
    }

    @Override
    protected Integer compute(){
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
    return 0;
}
    
    
}
