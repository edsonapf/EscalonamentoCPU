/**
 * Aluno: Edson Alves Pereira Filho
 * Matrícula: 11512960
 */

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
        
        ArrayList<Processo> filaEsperando =(ArrayList<Processo>) proc.clone();
        ArrayList<Processo> filaPronto = new ArrayList<Processo>();
        ArrayList<Processo> filaFinalizado = new ArrayList<Processo>();
        Processo processoExecutando = null;
        boolean entrouUm = false;
        double mediaEspera = 0, mediaRetorno = 0, mediaResposta = 0;
        
        
        /**
         * Loop onde será executado o escalonamento ciclo por ciclo
         * até que todos os processos tenham sido executados.
         */
        for(int ciclo=0; filaFinalizado.size() < proc.size(); ciclo++){
            
            /**
             * Fica nesse loop enquanto tiver algum processo na fila de processos
             * e esses processos têm tempo de chegada menor ou igual do quê o ciclo atual.
             */
            while((!filaEsperando.isEmpty()) && (filaEsperando.get(0).getChegada() <= ciclo)){
                /**
                 * Quando encontra um processo, adiciona ele na fila de prontos
                 * e remove da fila de processos que estavam esperando sua hora
                 * de chegar no sistema.
                 */
                entrouUm= true;
                filaPronto.add(filaEsperando.get(0));
                filaEsperando.remove(0);
                
            }
            
            if(entrouUm){
                
                /**
                 * Quando chegar o primeiro processo ou a cpu tiver ficado ociosa por algum tempo,
                 * entrará no if e executará o processo. Sempre verificando se tem algum processo na fila de prontos. 
                 */
                if(processoExecutando == null){
                    
                    if(!filaPronto.isEmpty()){
                        
                        processoExecutando = filaPronto.get(0);
                        processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());
                        processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                        filaPronto.remove(0);
                        
                    }
                                        
                }else{
                    
                    // Verifica se o processo já terminou sua execução.
                    if(processoExecutando.getTempoExecucao() == processoExecutando.getCicloCpu()){
                        
                        // O processo que terminou a execução é adicionado na fila de finalizado.
                        filaFinalizado.add(processoExecutando);
                        
                        // Se tiver algum processo na fila de prontos, ele é colocado para execução.
                        // Setando o tempo de resposta e espera do processo que está sendo executado.
                        if(!filaPronto.isEmpty()){
                            
                            processoExecutando = filaPronto.get(0);
                            processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());
                            processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                            filaPronto.remove(0);   
                            
                        }else{
                            
                            // A variável fica nula, pois o processador pode ficar ociosa,
                            // caso não tenha executado todos os processos.
                            processoExecutando = null;
                            
                        }
                    }
                    
                    
                }
                
                // Incrementa o tempo de execução para saber se foi executado todo
                if(processoExecutando != null)
                    processoExecutando.setTempoExecucao(processoExecutando.getTempoExecucao()+1);
                
            }
            
        }
        
        // Faz a soma de todos os tempos de cada processo finalizado
        for(Processo p : filaFinalizado){
            
            mediaEspera += p.getTempoEspera();
            mediaResposta += p.getTempoResposta();
            mediaRetorno += p.getTempoResposta() + p.getCicloCpu();
            
        }
        
        mediaEspera = mediaEspera / proc.size();
        mediaResposta = mediaResposta / proc.size();
        mediaRetorno = mediaRetorno / proc.size();
        
        System.out.printf("FCFS %.1f %.1f %.1f\n", mediaRetorno, mediaResposta, mediaEspera);
        
    }
    
    /**
     * O algoritmo SJF escalona os processos vizualizando quem é o menor que está na fila de prontos
     */
    
    public void algoritmoSJF(){
        
        ArrayList<Processo> filaEsperando = (ArrayList<Processo>) proc.clone();
        ArrayList<Processo> filaPronto = new ArrayList<Processo>();
        ArrayList<Processo> filaFinalizado = new ArrayList<Processo>();
        Processo processoExecutando = null;
        double mediaEspera = 0, mediaResposta = 0, mediaRetorno = 0;
        boolean entrouUm = false;
        
        ComparaCpu compCpu = new ComparaCpu();
        ComparaChegadaCpu compChegadaCpu = new ComparaChegadaCpu();
        
        // Ordena com critério de chegada e ciclo de cpu de cada processo
        Collections.sort(filaEsperando, compChegadaCpu);
        
        // Reseta os tempos de espera, retorno e respostas dos processos
        for(Processo p : filaEsperando)
            p.resetaTempos();
        
        /**
         * Loop onde será executado o escalonamento ciclo por ciclo
         * até que todos os processos tenham sido executados.
         */
        for(int ciclo=0; filaFinalizado.size() < proc.size(); ciclo++){
            
            /**
             * Fica nesse loop enquanto tiver algum processo na fila de processos
             * e esses processos têm tempo de chegada menor ou igual do quê o ciclo atual.
             */    
            while((!filaEsperando.isEmpty()) && (filaEsperando.get(0).getChegada() <= ciclo)){
                
                /**
                 * Quando encontra um processo, adiciona ele na fila de prontos
                 * e remove da fila de processos que estavam esperando sua hora
                 * de chegar no sistema.
                 */               
                entrouUm= true;
                filaPronto.add(filaEsperando.get(0));
                filaEsperando.remove(0);
                
            }
            
            /**
             * Ordena sempre a fila de pronto para que o processo
             * com menor tempo de cpu execute primeiro
             */
            Collections.sort(filaPronto, compCpu);
            
            if(entrouUm){
                
                /**
                 * Quando chegar o primeiro processo ou a cpu tiver ficado ociosa por algum tempo,
                 * entrará no if e executará o processo. Sempre verificando se tem algum processo na fila de prontos. 
                 */               
                if(processoExecutando == null){
                    
                    if(!filaPronto.isEmpty()){
                        
                        processoExecutando = filaPronto.get(0);
                        processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());
                        processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                        filaPronto.remove(0);
                        
                    }
                    

                }else{

                    // Verifica se o processo já terminou sua execução.
                    if(processoExecutando.getTempoExecucao() == processoExecutando.getCicloCpu()){

                        // O processo que terminou a execução é adicionado na fila de finalizado.
                        filaFinalizado.add(processoExecutando);
                        
                        // Se tiver algum processo na fila de prontos, ele é colocado para execução.
                        // Setando o tempo de resposta e espera do processo que está sendo executado.
                        if(!filaPronto.isEmpty()){
                            
                            processoExecutando = filaPronto.get(0);
                            processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());
                            processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                            filaPronto.remove(0);
                            
                        }else{
                            
                            // A variável fica nula, pois o processador pode ficar ociosa,
                            // caso não tenha executado todos os processos.
                            processoExecutando = null;
                            
                        }
                    } 


                }
                
                // Incrementa o tempo de execução para saber se foi executado todo
                if(processoExecutando != null)
                    processoExecutando.setTempoExecucao(processoExecutando.getTempoExecucao()+1);
                
            }
                   
        }
        
        // Faz a soma de todos os tempos de cada processo finalizado
        for(Processo p : filaFinalizado){
            
            mediaEspera += p.getTempoEspera();
            mediaResposta += p.getTempoResposta();
            mediaRetorno += p.getTempoResposta() + p.getCicloCpu();
            
        }
        
        mediaEspera = mediaEspera / proc.size();
        mediaResposta = mediaResposta / proc.size();
        mediaRetorno = mediaRetorno / proc.size();
        
        System.out.printf("SJF %.1f %.1f %.1f\n", mediaRetorno, mediaResposta, mediaEspera);
        
    }
    
    /**
     * O algoritmo RR escalona os processos por ondem de chegada na fila de prontos
     * e deixa executar o processo por um certo tempo(quantum)
     */
    public void algoritmoRR(){
                
        ArrayList<Processo> filaEsperando =(ArrayList<Processo>) proc.clone();
        ArrayList<Processo> filaPronto = new ArrayList<Processo>();
        ArrayList<Processo> filaFinalizado = new ArrayList<Processo>();
        Processo processoExecutando = null;
        double mediaEspera = 0, mediaRetorno = 0, mediaResposta = 0;
        boolean entrouUm = false;
        int quantum = 0;
        
        // Ordena com critério de chegada e ciclo de cpu de cada processo
        for(Processo p : filaEsperando)
            p.resetaTempos(); 
        
        /**
         * Loop onde será executado o escalonamento ciclo por ciclo
         * até que todos os processos tenham sido executados.
         */
        for(int ciclo = 0; filaFinalizado.size() < proc.size(); ciclo++){
            
            /**
             * Fica nesse loop enquanto tiver algum processo na fila de processos
             * e esses processos têm tempo de chegada menor ou igual do quê o ciclo atual.
             */ 
            while((!filaEsperando.isEmpty()) && (filaEsperando.get(0).getChegada() <= ciclo)){
                
                /**
                 * Quando encontra um processo, adiciona ele na fila de prontos
                 * e remove da fila de processos que estavam esperando sua hora
                 * de chegar no sistema.
                 */  
                entrouUm= true;
                filaPronto.add(filaEsperando.get(0));
                filaEsperando.remove(0);
                
            }
            
            if(entrouUm){
                
                /**
                 * Quando chegar o primeiro processo ou a cpu tiver ficado ociosa por algum tempo,
                 * entrará no if e executará o processo. Sempre verificando se tem algum processo na fila de prontos. 
                 */ 
                if(processoExecutando == null){
                    
                    if(!filaPronto.isEmpty()){
                        
                        processoExecutando = filaPronto.get(0);

                        // Condição para saber se é a primeira vez que o processo está executando.
                        // Caso for verdadeira, seta os tempo inicial de espera e o tempo de resposta.
                        if(processoExecutando.getTempoResposta() < 0){

                            processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                            processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());

                        }

                        filaPronto.remove(0);
                        
                    }
                        
                    
                }else{
                    
                    // Verifica se foi passado 2 quantum's.
                    if(quantum == 2){
                        
                        // Caso o processo ainda não tenha terminado, coloca-o de volta na fila de prontos.
                        if(processoExecutando.getTempoExecucao() != processoExecutando.getCicloCpu()){
                            
                            filaPronto.add(processoExecutando);
                            
                        }else{
                            
                            // Caso o processo tenha terminado de executar, coloca-o na fila de finalizados.
                            filaFinalizado.add(processoExecutando);
                            
                        }
                        
                        // Se tiver algum processo na fila de prontos, ele é colocado para execução.
                        // Setando o tempo de resposta e espera do processo que está sendo executado.
                        if(!filaPronto.isEmpty()){
                            
                            processoExecutando = filaPronto.get(0);
                            
                            // Condição para saber se é a primeira vez que o processo está executando.
                            // Caso for verdadeira, seta os tempo inicial de espera e o tempo de resposta.
                            if(processoExecutando.getTempoResposta() < 0){

                                processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                                processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());

                            }

                            filaPronto.remove(0);    
                            
                        }else{
                            
                            // A variável fica nula, pois o processador pode ficar ociosa,
                            // caso não tenha executado todos os processos.
                            processoExecutando = null;
                            
                        }
                        
                        //reseta o valor do quantum.
                        quantum = 0;
                        
                    }
                    
                    // Verifica se o processo já terminou sua execução.
                    else if(processoExecutando.getTempoExecucao() == processoExecutando.getCicloCpu()){
                        
                        // O processo que terminou a execução é adicionado na fila de finalizado.
                        filaFinalizado.add(processoExecutando);
                        
                        // Se tiver algum processo na fila de prontos, ele é colocado para execução.
                        // Setando o tempo de resposta e espera do processo que está sendo executado.
                        if(!filaPronto.isEmpty()){
                            
                            processoExecutando = filaPronto.get(0);
                            
                            // Condição para saber se é a primeira vez que o processo está executando.
                            // Caso for verdadeira, seta os tempo inicial de espera e o tempo de resposta.
                            if(processoExecutando.getTempoResposta() < 0){

                                processoExecutando.setTempoResposta(ciclo - processoExecutando.getChegada());
                                processoExecutando.setTempoEspera(ciclo - processoExecutando.getChegada());

                            }

                            filaPronto.remove(0);
                            
                        }else{
                            
                            // A variável fica nula, pois o processador pode ficar ociosa,
                            // caso não tenha executado todos os processos.
                            processoExecutando = null;
                            
                        }
                        
                        //reseta o valor do quantum.
                        quantum = 0;
                        
                    }
                    
                }
                
                // Incrementa o tempo de execução para saber se foi executado todo
                if(processoExecutando != null)
                    processoExecutando.setTempoExecucao(processoExecutando.getTempoExecucao()+1);//incrementa o tempo de execução
                
                //incrementa sempre que tiver um processo executando
                quantum++;
                
            }
            
            //Incrementa o tempo de espera de todos os processos que estão na fila de pronto
            for(int i=0; i<filaPronto.size(); i++){
                
                filaPronto.get(i).setTempoEspera(filaPronto.get(i).getTempoEspera()+1);
                
            }
            
            
        }
        
        for(Processo p : filaFinalizado){
            
            mediaResposta += p.getTempoResposta();
            mediaEspera += p.getTempoEspera();
            mediaRetorno += p.getTempoEspera() + p.getCicloCpu();
            
        }
        
        mediaRetorno = mediaRetorno / proc.size();
        mediaResposta = mediaResposta / proc.size();
        mediaEspera = mediaEspera / proc.size();
        
        System.out.printf("RR %.1f %.1f %.1f\n", mediaRetorno, mediaResposta, mediaEspera);
                
    }
     
}
