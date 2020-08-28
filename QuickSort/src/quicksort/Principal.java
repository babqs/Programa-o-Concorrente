/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quicksort;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import java.util.concurrent.RecursiveTask;


/**
 *
 * @author Uso Exclusivo
 */
public class Principal {
    public static void main(String[] args) {
        // TODO code application logic here
        int quantidade = 20;
        int v[] = new int[quantidade];
        Random aleatorio = new Random();
        System.out.println("Vetor desordenado");
        for(int i = 0; i < quantidade; i++){
            v[i] = aleatorio.nextInt(100);
            System.out.println(v[i]);
        }
        ForkJoinPool pool = new ForkJoinPool();
        QuickSort qs = new QuickSort(v); 
        
        pool.invoke(qs);
        System.out.println("Ordenado: " + arrayToString(v));
        
//        for(int i = 0; i < v.length-1; i++){
//            System.out.println(v[i]);
//        }
        //System.out.println("Vetor ordenado:" + qs.compute() );
    }
    private static String arrayToString(int[] array){
        String aux = "[" + array[0];
        for(int i = 1; i < array.length; i++){
            aux += ", " + array[i];
        }
        return aux + "]";
    }
}