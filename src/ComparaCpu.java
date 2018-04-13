
import java.util.Comparator;

public class ComparaCpu implements Comparator<Processo>{
    
    public int compare(Processo p1, Processo p2){
        
        return p1.getCicloCpu() - p2.getCicloCpu();
        
    }
    
}
