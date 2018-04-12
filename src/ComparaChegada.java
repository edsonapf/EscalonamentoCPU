
import java.util.Comparator;


public class ComparaChegada implements Comparator<Processo>{

    @Override
    public int compare(Processo p1, Processo p2) {
        
        return p1.getChegada() - p2.getChegada();
        
    }
    
}
