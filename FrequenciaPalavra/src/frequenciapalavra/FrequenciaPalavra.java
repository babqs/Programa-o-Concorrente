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
    ArrayList<String> lista;
    int inicio, fim;

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
        calcularFrequencia(lista, 0, lista.size());
    }

    public Integer calcularFrequencia(ArrayList<String> lista, int inicio, int fim) {
        //conta a frequencia de cada palavra
        String p;
        Integer frequencia = 0;
        for (int i = 0; i < lista.size(); i++) {
            p = lista.get(i);
            frequencia = contar.get(p);
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
        contar.entrySet().forEach((f) -> {
            System.out.println(f.getKey() + "\t Frequência: " + f.getValue());
        });
    }

    @Override
    protected Integer compute() {
        leArquivo();
        int m;
        try {
            if (inicio < fim) {
                m = calcularFrequencia(lista, 0, lista.size());
                invokeAll(new FrequenciaPalavra(lista, 0, m - 1).fork(), new FrequenciaPalavra(lista, m + 1, lista.size()).fork());
            }
        } catch (FileNotFoundException e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return 0;
    }
}
