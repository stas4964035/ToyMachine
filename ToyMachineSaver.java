import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ToyMachineSaver implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    HashMap toyList;
    LinkedHashMap slots;

    public ToyMachineSaver(HashMap toyList, LinkedHashMap slotList) {
        this.toyList = toyList;
        this.slots = slotList;

    }


}
