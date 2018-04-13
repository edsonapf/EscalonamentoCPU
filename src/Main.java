
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Main {
    
    public static void main(String[] args){
        
        ArrayList<Processo> processos = new ArrayList<Processo>();
        ArrayList<Processo> processosAux = null;//lista de processos auxiliar apenas para ordenar por ordem de ciclo de cpu
        Processo proc;
        Algoritmos executaAlgoritmos;
        ComparaChegada compChegada = new ComparaChegada();
        ComparaChegadaCpu compCpu = new ComparaChegadaCpu();
        
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
        
        Collections.sort(processos, compChegada);
        processosAux = processos;
        
        executaAlgoritmos = new Algoritmos();
        executaAlgoritmos.algoritmoFCFS(processos);

        Collections.sort(processosAux, compCpu);
        executaAlgoritmos.algoritmoSJF(processosAux);
       
        executaAlgoritmos.algoritmoRR(processos);
        
        

        
    }
    
}
