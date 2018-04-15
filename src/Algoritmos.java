
import java.util.ArrayList;
import java.util.Collections;

public class Algoritmos {
    
    ArrayList<Processo> proc = new ArrayList<Processo>();
    
    public Algoritmos(ArrayList<Processo> proc){
        this.proc = proc;
    }
       
    /**
     * O algoritmo FCFS escalona sempre de acordo com a ordem de chegada
     * Funciona como uma fila FIFO
     */
    public void algoritmoFCFS(){
        
        ArrayList<Processo> processo = (ArrayList<Processo>) proc.clone();
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
        processo.get(0).setChegada(0);//O primeiro processo sempre vai entrar assim que chegar, então seu tempo de espera é 0
        for(int i = 1; i < processo.size(); i++){
            
            processo.get(i).setTempoEspera(processo.get(i-1).getTempoEspera() + processo.get(i-1).getCicloCpu());
            mediaEspera += processo.get(i).getTempoEspera() - processo.get(i).getChegada() + processo.get(0).getChegada();
            
        }
        
        /**
         * Tempo de Retorno é o tempo que o processo levou para terminar sua execução
         * 
         * Cálculo do Tempo de Retorno:
         * TR = Somatório(Tempo de Espera + Tempo de Ciclos de CPU - Tempo de Chegada do processo atual + Tempo do Chegada do primeiro processo)
         * Somando com o Tempo de Chegada do primeiro processo, garante que só contará a partir do início do primeiro processo
         * 
         */
        for(int i = 0; i < processo.size(); i++){
            
            mediaRetorno += processo.get(i).getTempoEspera() +processo.get(i).getCicloCpu() - processo.get(i).getChegada() + processo.get(0).getChegada();
            
        }
        
        /**
         * Tempo de Resposta é o tempo que o processo demorou para ser executado pela primera vez
         * 
         * Já que no Algoritmo FCFS o algoritmo executa por completo quando responde pela primeira vez
         * então a maneira de calcular o Tempo de Resposta vai ser igual ao cálculo do Tempo de Espera
         */
        for(int i = 0; i < processo.size(); i++){
            
            mediaResposta += processo.get(i).getTempoEspera() - processo.get(i).getChegada() + processo.get(0).getChegada();
            
        }
        
        mediaRetorno = mediaRetorno/processo.size();
        mediaResposta = mediaResposta/processo.size();
        mediaEspera = mediaEspera/processo.size();
        
        System.out.printf("FCFS %.1f %.1f %.1f\n", mediaRetorno, mediaResposta, mediaEspera);
        
        
    }
    
    /**
     * O algoritmo SJF escalona os processos vizualizando quem é o menor que está na fila de prontos
     */
    
    public void algoritmoSJF(){
        
        ComparaCpu compCpu = new ComparaCpu();
        ComparaChegadaCpu compChegadaCpu = new ComparaChegadaCpu();
        ArrayList<Processo> filaEsperando = (ArrayList<Processo>) proc.clone();
        ArrayList<Processo> filaPronto = new ArrayList<Processo>();
        ArrayList<Processo> filaFinalizado = new ArrayList<Processo>();
        Processo processoExecutando = null;
        Collections.sort(filaEsperando, compChegadaCpu);
        
        double mediaEspera = 0, mediaResposta = 0, mediaRetorno = 0;
        int tempoTotal = 0, ciclo;
        
        int executou = 0;//para saber quantas vezes o processo que está sendo executado foi executado
        boolean entrouUm = false;
        
        //Tempo total salvará o tempo necessário para executar todos os processos
        for(Processo p : proc)
            tempoTotal += p.getCicloCpu();
        
        /**
         * Processo de escalonamento dos processos para calcular
         * seus tempos de espera, retorno e resposta
         */
        for(ciclo=0; ciclo <= tempoTotal+proc.get(0).getChegada(); ciclo++){
            
            //Laço que verifica qual processo chegou no sistema
            for(int j = 0; j < filaEsperando.size(); j++){
                
                if(filaEsperando.get(j).getChegada() > ciclo)
                    break;
                
                if(filaEsperando.get(j).getChegada() <= ciclo){
                    //System.out.println("Entrou");
                    entrouUm = true;
                    filaPronto.add(filaEsperando.get(j));
                    filaEsperando.remove(j);
                    
                }
                
            }
            
            Collections.sort(filaPronto, compCpu);
            
            
            //Verifica se existe algum processo executando
            if(entrouUm){
                if(processoExecutando == null){

                    processoExecutando = filaPronto.get(0);
                    //System.out.println(ciclo+"\t"+(ciclo - processoExecutando.getChegada()));
                    processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());
                    processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                    filaPronto.remove(0);

                }else{
                    executou++;
                    //verifica se o processo que estava executando foi concluido
                    if(executou == processoExecutando.getCicloCpu()){
                        executou = 0;

                        filaFinalizado.add(processoExecutando);
                        if(!filaPronto.isEmpty()){
                            processoExecutando = filaPronto.get(0);
                            processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());
                            processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                            filaPronto.remove(0);
                        }
                    } 


                }
            }
                   
        }//fim do for ciclo
        
        //System.out.println(filaFinalizado.get(filaFinalizado.size()-1).getChegada()+"\t"+filaFinalizado.get(filaFinalizado.size()-1).getCicloCpu());
        
        //Cálculo do Tempo de Espera
        for(int i=0; i<filaFinalizado.size(); i++){
            
            mediaEspera += filaFinalizado.get(i).getTempoEspera();
            
        }
        
        //Cálculo do Tempo de Resposta
        for(int i=0; i<filaFinalizado.size(); i++){
            
            mediaResposta += filaFinalizado.get(i).getTempoResposta();
            
        }
        
        //Cálculo do Tempo de Retorno
        for(int i=0; i<filaFinalizado.size(); i++){
            
            mediaRetorno += filaFinalizado.get(i).getTempoResposta() + filaFinalizado.get(i).getCicloCpu();
            
        }
        
        mediaEspera = mediaEspera / proc.size();
        mediaResposta = mediaResposta / proc.size();
        mediaRetorno = mediaRetorno / proc.size();
        
        System.out.printf("SJF %.1f %.1f %.1f\n", mediaRetorno, mediaResposta, mediaEspera);
        
    }//fim algoritmo sjf
    
    /**
     * O algoritmo RR escalona os processos por ondem de chegada na fila de prontos
     * e deixa executar o processo por um certo tempo(quantum)
     */
    public void algoritmoRR(){
        
        boolean entrouUm = false;
        int quantum = 0, tempoTotal = 0;
        ArrayList<Processo> filaEsperando =(ArrayList<Processo>) proc.clone();
        ArrayList<Processo> filaPronto = new ArrayList<Processo>();
        ArrayList<Processo> filaFinalizado = new ArrayList<Processo>();
        Processo processoExecutando = null;
        
        double mediaEspera = 0, mediaRetorno = 0, mediaResposta = 0;
        
        for(Processo p : proc)
            tempoTotal += p.getCicloCpu();
        
        for(int ciclo = 0; ciclo <= tempoTotal + proc.get(0).getChegada(); ciclo++){
            
            for(int i = 0; i < filaEsperando.size(); i++){
                
                if(filaEsperando.get(i).getChegada() > ciclo)
                    break;
                
                if(filaEsperando.get(i).getChegada() <= ciclo){
                    
                    entrouUm = true;
                    filaPronto.add(filaEsperando.get(i));
                    filaEsperando.remove(i);
                    
                }
                
            }
            
            if(entrouUm){
                
                if(processoExecutando == null){
                    
                    processoExecutando = filaPronto.get(0);
                    
                    //Condição para saber se já entrou alguma vez para executar
                    if(processoExecutando.getTempoResposta() < 0){
                        
                        processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                        processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());
                        
                    }
                    
                    filaPronto.remove(0);
                    
                }else{
                    
                    
                    
                }
                
            }
            
        }
        
        
        
    }//fim algoritmo rr
     
}
