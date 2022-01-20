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
        exec("Festival", "Hola desde el Script de Java");
        // ...and hangup.
        hangup();               
    }          
}


