
import java.util.ArrayList;
import java.util.Collections;

public class Algoritmos {
       
    /**
     * O algoritmo FCFS escalona sempre de acordo com a ordem de chegada
     * Funciona como uma fila FIFO
     */
    public void algoritmoFCFS(ArrayList<Processo> proc){
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
        for(int i = 0; i < proc.size(); i++){
            
            mediaResposta += proc.get(i).getTempoEspera() - proc.get(i).getChegada() + proc.get(0).getChegada();
            
        }
        
        mediaRetorno = mediaRetorno/proc.size();
        mediaResposta = mediaResposta/proc.size();
        mediaEspera = mediaEspera/proc.size();
        
        System.out.printf("FCFS %.1f %.1f %.1f\n", mediaRetorno, mediaResposta, mediaEspera);
        
        
    }
    
    /**
     * O algoritmo SJF escalona os processos vizualizando quem é o menor que está na fila de prontos
     */
    
    public void algoritmoSJF(ArrayList<Processo> proc){
        
        ComparaCpu compCpu = new ComparaCpu();
        ArrayList<Processo> filaEsperando = proc;
        ArrayList<Processo> filaPronto = new ArrayList<Processo>();
        ArrayList<Processo> filaFinalizado = new ArrayList<Processo>();
        Processo processoExecutando = null;
        
        double mediaEspera = 0, mediaResposta = 0, mediaRetorno = 0;
        int tempoTotal = 0, ciclo;
        
        //Tempo total salvará o tempo necessário para executar todos os processos
        for(Processo p : proc)
            tempoTotal += p.getCicloCpu();
        
        /**
         * Processo de escalonamento dos processos para calcular
         * seus tempos de espera, retorno e resposta
         */
        for(ciclo=0; ciclo <= tempoTotal; ciclo++){
            
            //Laço que verifica qual processo chegou no sistema
            for(int j = 0; j < filaEsperando.size(); j++){
                
                if(filaEsperando.get(j).getChegada() > ciclo)
                    break;
                
                if(filaEsperando.get(j).getChegada() <= ciclo){
                    //System.out.println("Entrou");
                    filaPronto.add(filaEsperando.get(j));
                    filaEsperando.remove(j);
                    
                }
                
            }
            
            Collections.sort(filaPronto, compCpu);
            //System.out.println(filaPronto.get(0).getChegada());
            /*if(!filaPronto.isEmpty()){
                
                if(processoExecutando == null){
                    processoExecutando = filaPronto.get(0);
                    filaPronto.remove(0);
                    processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());
                    processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                }
                
            }*/
            
            
            //Verifica se existe algum processo executando
            if(processoExecutando == null){
                
                processoExecutando = filaPronto.get(0);
                //System.out.println(ciclo+"\t"+(ciclo - processoExecutando.getChegada()));
                processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());
                processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                filaPronto.remove(0);
                
            }else{
                
                //System.out.println(processoExecutando.getChegada()+"  "+processoExecutando.getCicloCpu()+"  "+processoExecutando.getTempoEspera());
                //verifica se o processo que estava executando foi concluido
                if(processoExecutando.getCicloCpu() == (ciclo - processoExecutando.getTempoEspera())){
                    
                    System.out.println(processoExecutando.getCicloCpu());
                    
                    filaFinalizado.add(processoExecutando);
                    if(!filaPronto.isEmpty()){
                        processoExecutando = filaPronto.get(0);
                        //System.out.println(ciclo+"\t"+(ciclo - processoExecutando.getChegada()));
                        processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());
                        processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                        filaPronto.remove(0);
                    }
                    
                }
                
            }
            
                   
        }//fim do for ciclo
        
        /*System.out.println(proc.get(0).getChegada());
        //Cálculo do Tempo de Espera
        for(int i=0; i<filaFinalizado.size(); i++){
            
            mediaEspera += filaFinalizado.get(i).getTempoEspera() + proc.get(0).getChegada();
            
        }
        
        //Cálculo do Tempo de Resposta
        for(int i=0; i<filaFinalizado.size(); i++){
            
            mediaResposta += filaFinalizado.get(i).getTempoResposta()+ proc.get(0).getChegada();
            
        }
        
        //Cálculo do Tempo de Retorno
        for(int i=0; i<filaFinalizado.size(); i++){
            
            mediaRetorno += filaFinalizado.get(i).getTempoResposta()+ proc.get(0).getChegada() +filaFinalizado.get(i).getCicloCpu();
            
        }
        
        mediaEspera = mediaEspera / proc.size();
        mediaResposta = mediaResposta / proc.size();
        mediaRetorno = mediaRetorno / proc.size();
        
        System.out.printf("SJF %.1f %.1f %.1f\n", mediaRetorno, mediaResposta, mediaEspera);*/
        
    }//fim algoritmo sjf
    
    /**
     * O algoritmo RR escalona os processos por ondem de chegada na fila de prontos
     * e deixa executar o processo por um certo tempo(quantum)
     */
    public void algoritmoRR(ArrayList<Processo> proc){
        
    }
     
}
