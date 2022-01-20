import org.asteriskjava.fastagi.*;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
/**
 *
 * @author Sebastian
 */
public class Prueba extends BaseAgiScript {
    public void service(AgiRequest request, AgiChannel channel)
            throws AgiException
    {
        // Answer the channel...
        answer();
        // ...say hello...
        exec("Festival", "Hola desde el Script de Java presione una tecla para ser leida " +
                "o no haga nada, y se cuelga");
        
        String dato; 
        boolean ban = true; 
        while (ban) {
         
        dato = this.getData(null,20000L,2);
            
            if (dato.equals("45")) {
                
                ban=false;
            }else{
                System.out.print("El numero presionado fue" + dato);
                exec("Festival", "la tecla que presionó fue " + dato);
            }
            //streamFile("tt-monkeys");
        }
        
        // ...and hangup.
        hangup();               
    }          
}


