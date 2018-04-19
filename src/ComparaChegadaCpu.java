/**
 * Aluno: Edson Alves Pereira Filho
 * Matrícula: 11512960
 */

import java.util.Comparator;


public class ComparaChegadaCpu implements Comparator<Processo>{
    
    @Override
    public int compare(Processo p1, Processo p2){
        
        if((p1.getChegada() == p2.getChegada()) && (p1.getCicloCpu() > p2.getCicloCpu()))
            return 1;
        
        else if((p1.getChegada() == p2.getChegada()) && (p1.getCicloCpu() < p2.getCicloCpu()))
            return -1;
            
        return 0;
    }
    
}
