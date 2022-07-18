import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException, ClassNotFoundException, NoSuchMethodException {
        Path cryptoPath = Paths.get(args[0]);
        TestAlgsPlus test = new TestAlgsPlus();
        test.readAndLoad(cryptoPath);
        test.checkAlgos(cryptoPath);
    }
}
