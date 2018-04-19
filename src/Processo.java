/**
 * Aluno: Edson Alves Pereira Filho
 * Matrícula: 11512960
 */

public class Processo {
    
    private int chegada;
    private int cicloCpu;
    private int tempoExecucao;
    private int tempoEspera;
    private int tempoResposta;
    
    public Processo(int chegada, int cicloCpu){
        this.chegada = chegada;
        this.cicloCpu = cicloCpu;
        this.tempoEspera = 0;
        this.tempoExecucao = 0;
        this.tempoResposta = -1;
    }
    
    /**
     * Método que reinicializa os tempo de resposta para saber se ele já foi executado alguma vez
     */
    public void resetaTempos(){
        this.tempoExecucao = 0;
        this.tempoEspera = 0;
        this.tempoResposta = -1;
    }
    
    public void setChegada(int chegada){
        this.chegada = chegada;
    }
    
    public int getChegada(){
        return this.chegada;
    }
    
    public void setCicloCpu(int cicloCpu){
        this.cicloCpu = cicloCpu;
    }
    
    public int getCicloCpu(){
        return this.cicloCpu;
    }
    
    public void setTempoExecucao(int tempoExecucao){
        this.tempoExecucao = tempoExecucao;
    }
    
    public int getTempoExecucao(){
        return this.tempoExecucao;
    }
    
    public void setTempoEspera(int tempoEspera){
        this.tempoEspera = tempoEspera;
    }
    
    public int getTempoEspera(){
        return this.tempoEspera;
    }
    
    public void setTempoResposta(int tempoResposta){
        this.tempoResposta = tempoResposta;
    }
    
    public int getTempoResposta(){
        return this.tempoResposta;
    }
    
}
