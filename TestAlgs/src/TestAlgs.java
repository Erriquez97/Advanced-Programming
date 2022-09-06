import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Scanner;


public class TestAlgs {

    private KeyRegistry keyRegistry;
    private static final String PATH_KEYS_LIST = "/crypto/keys.list";
    private static final String PATH_SECRET_LIST = "/crypto/secret.list";
    private static final String PATH_ALGOS_FOLDER = "/crypto/algos/";

    public TestAlgs() {
        this.keyRegistry = new KeyRegistry();
    }

    // Function that read the file Keys.list and load in the registry the pair made of the class and the key
    public void readAndLoad(Path path) throws IOException, ClassNotFoundException {
        String alg;
        String[] line;
        File file = new File(path + PATH_KEYS_LIST);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(" ");

        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{
                new File(String.valueOf(path)).toURI().toURL()
        });

        while (scanner.hasNext()) {
            alg = scanner.nextLine();
            line = alg.trim().split("\\s+");
            Class c = urlClassLoader.loadClass(line[0]);
            this.keyRegistry.add(c, line[1]);
        }
    }

    // function that checks if the class taken as input has a constructor that takes as a parameter a String
    private Constructor checkConstructor(Class c) throws NoSuchMethodException {

        Constructor constructor = c.getConstructor(String.class);
        if (constructor == null) {
            return null;
        } else {
            return constructor;
        }
    }

    // function that takes as input a class and a prefix and checks if the class has a method that starts with the prefix
    private Method checkPrefixMethods(Class c, String prefix) throws NoSuchMethodException {
        Method[] methods = c.getMethods();
        for (Method method : methods) {
            if (method.getParameterCount() == 1 && (method.getName().startsWith(prefix) && method.getParameterTypes()[0].
                    getSimpleName().equals("String"))) {
                return method;
            }
        }
        return null;
    }

    /* function that checks for each class in the folder "algos" if that class has: a constructor that takes a String as a parameter,
       a method that starts with "enc" and a method that starts with "dec".
       If the class has the 3 conditions listed before then it will be created an instance of the class passing as
       parameter the key saved in the registry corresponding that class.
       Then it will read the file "secret.list" and for each of the word present in it, it will invoke the methods encrypt
       and decrypt. If the decrypt word and the initial word don't match then it will be printed that they don't match  */

    public void checkAlgos(Path path) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException,
            FileNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {

        File[] files = new File(path + PATH_ALGOS_FOLDER).listFiles();

        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{
                new File(String.valueOf(path)).toURI().toURL()
        });

        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                Class c = urlClassLoader.loadClass("crypto.algos." + file.getName().substring(0, (file.getName().length() - 6)));
                Constructor hasConstructor = this.checkConstructor(c);
                Method hasEncMethod = this.checkPrefixMethods(c, "enc");
                Method hasDecMethod = this.checkPrefixMethods(c, "dec");

                if (hasConstructor != null && hasDecMethod != null && hasEncMethod != null) {
                    String key = this.keyRegistry.get(c);

                    Constructor constructor = c.getConstructor(String.class);
                    Method encMethod = hasEncMethod;
                    Method decMethod = hasDecMethod;
                    String secretKey;
                    File secrefile = new File(String.valueOf(path + PATH_SECRET_LIST));
                    Scanner scanner = new Scanner(secrefile);
                    while (scanner.hasNext()) {
                        secretKey = scanner.nextLine();

                        Object algorithm = constructor.newInstance(key);
                        String encwrd = (String) encMethod.invoke(algorithm, secretKey);
                        String decwrd = (String) decMethod.invoke(algorithm, encwrd);
                        if (!decwrd.substring(0, secretKey.length()).equals(secretKey) &&
                                decwrd.substring(secretKey.length(), decwrd.length()).matches("#*")) {
                            System.out.println("KO: " + secretKey + " -> " + encwrd + " -> " + decwrd);
                        }
                    }
                } else {
                    if (hasConstructor == null) {
                        System.out.println("The class: " + file.getName() + " has no public constructor");
                    }
                    if (hasEncMethod == null) {
                        System.out.println("The class: " + file.getName() + " has no methods starting with enc");
                    }
                    if (hasEncMethod == null) {
                        System.out.println("The class: " + file.getName() + " has no methods starting with dec");
                    }
                }


            }

        }
    }

}



