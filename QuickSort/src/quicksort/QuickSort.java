/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quicksort;


//import java.util.ArrayList;
//import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author Uso Exclusivo
 */
public class QuickSort extends RecursiveTask{

    private int[] vetor;
    private int inicio;
    private int fim;
    
    public QuickSort(int[] vetor, int inicio, int fim) {
        this.vetor = vetor;
        this.inicio = inicio;
        this.fim = fim;
    }

    public QuickSort(int[] vetor) {
        this(vetor, 0, vetor.length-1);
    }
    
   public int particao(int vetor[], int inicio, int fim){
        int pivo = vetor[inicio];
        int i = inicio +1;
        int f = fim;
        while(i<=f){
            if(vetor[i] <= pivo){
                i++;
            }
            else if(pivo < vetor[f]){
                f--;
            }
            else{
                int aux = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = aux;
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;
        return f;
    }
    
    public void quickSort(int vetor[], int inicio, int fim){
        if(inicio < fim){
            int posicaoPivo = particao(vetor, inicio, fim);
            quickSort(vetor, inicio, posicaoPivo-1);
            quickSort(vetor, posicaoPivo+1, fim);
        }
    }
    
    @Override
    protected Integer compute(){
        int q;
        if(inicio < fim){
            q = particao(vetor, inicio, fim);
            invokeAll(new QuickSort(vetor, inicio, q-1).fork(), new QuickSort(vetor, q+1, fim).fork());
//            List<QuickSort> subtarefa = new ArrayList<>();
//            subtarefa.forEach((sub) -> {
//                sub.fork();
//            });
        }
        
       return 0;
    }

//    private List<QuickSort> splitWork(int p) {
//        List<QuickSort> subtarefa = new ArrayList<>();
//        subtarefa.add(new QuickSort(vetor,inicio,p-1));
//        subtarefa.add(new QuickSort(vetor, p+1, fim));
//        return subtarefa;   
//    }
}
