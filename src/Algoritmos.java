
import java.util.ArrayList;

public class Algoritmos {
    
    private ArrayList<Processo> proc;
    
    public Algoritmos(ArrayList<Processo> proc){
        this.proc = proc;
    }
    
    /**
     * O algoritmo FCFS escalona sempre de acordo com a ordem de chegada
     * Funciona como uma fila FIFO
     */
    public void algoritmoFCFS(){
        int tempoEspera = 0; 
        double mediaEspera = 0, mediaRetorno = 0, mediaResposta = 0;
        
        /**
         * Tempo de Espera é o tempo que o processo ficou esperando na fila de prontos
         * 
         * Cálculo do Tempo de Espera sem contar com o Tempo de Chegada:
         * Tempo de Espera = Somatório(Tempo de Espera Anterior + Tempo de Ciclo de CPU do processo anterior)
         * 
         * Cálculo do Tempo de Espera contando o Tempo de Chegada:
         * Tempo de Espera = Somatório(Tempo de Espera total sem o Tempo de Chegada - Tempo de Chegada do processo atual + Tempo de Chegada do primeiro processo)
         * Somando com o Tempo de Chegada do primeiro processo, garante que só contará a partir do início do primeiro processo
         * 
         */
        for(int i = 1; i < proc.size(); i++){
            
            proc.get(i).setTempoEspera(proc.get(i-1).getTempoEspera() + proc.get(i-1).getCicloCpu());
            mediaEspera += proc.get(i).getTempoEspera() - proc.get(i).getChegada() + proc.get(0).getChegada();
            
        }
        
        /**
         * Tempo de Retorno é o tempo que o processo levou para terminar sua execução
         * 
         * Cálculo do Tempo de Retorno:
         * TR = Somatório(Tempo de Espera + Tempo de Ciclos de CPU - Tempo de Chegada do processo atual + Tempo do Chegada do primeiro processo)
         * Somando com o Tempo de Chegada do primeiro processo, garante que só contará a partir do início do primeiro processo
         * 
         */
        for(int i = 0; i < proc.size(); i++){
            
            mediaRetorno += proc.get(i).getTempoEspera() +proc.get(i).getCicloCpu() - proc.get(i).getChegada() + proc.get(0).getChegada();
            
        }
        
        /**
         * Tempo de Resposta é o tempo que o processo demorou para ser executado pela primera vez
         * 
         * Já que no Algoritmo FCFS o algoritmo executa por completo quando responde pela primeira vez
         * então a maneira de calcular o Tempo de Resposta vai ser igual ao cálculo do Tempo de Espera
         */
        mediaResposta = mediaEspera;
        
        mediaRetorno = mediaRetorno/proc.size();
        mediaResposta = mediaResposta/proc.size();
        mediaEspera = mediaEspera/proc.size();
        
        System.out.printf("FCFS %.1f %.1f %.1f\n", mediaRetorno, mediaResposta, mediaEspera);
        
        
    }
     
}
