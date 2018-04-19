/**
 * Aluno: Edson Alves Pereira Filho
 * Matrícula: 11512960
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Main {
    
    public static void main(String[] args){
        
        ArrayList<Processo> processos = new ArrayList<Processo>();
        Processo proc;
        Algoritmos executaAlgoritmos;
        ComparaChegada compChegada = new ComparaChegada();
        ComparaChegadaCpu compChegadaCpu = new ComparaChegadaCpu();
        
        /**
         * Trecho de código onde será lido o arquivo contendo o tempo de entrada e tempo de cpu dos processos
         * Os tempos serão colocados em uma lista
         */
        String linha;
        String separaLinha[] = new String[2];
        File arquivo = new File("processos.txt");
        try{
            FileReader lerArquivo = new FileReader(arquivo);
            BufferedReader bufferArquivo = new BufferedReader(lerArquivo);
            
            while(bufferArquivo.ready()){
                
                linha = bufferArquivo.readLine();
                separaLinha = linha.split(" ");
                proc = new Processo(Integer.parseInt(separaLinha[0]), Integer.parseInt(separaLinha[1]));
                processos.add(proc);
                
            }
            
            bufferArquivo.close();
            lerArquivo.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        
        // Ordena os processos por tempo de chegada
        Collections.sort(processos, compChegada);
              
        executaAlgoritmos = new Algoritmos(processos);
        
        executaAlgoritmos.algoritmoFCFS();
        
        executaAlgoritmos.algoritmoSJF();
        
        executaAlgoritmos.algoritmoRR();
     
    }
    
}
