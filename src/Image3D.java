import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Klasa umozliwiajaca wczytywanie obrazu warstwowego - tomogramu
 */
public class Image3D  {

    /**
     * kontener z seria obrazow
     */
    ArrayList<MyImage> myImages= new ArrayList<>();

    /**
     * Podstawowy konstruktor wczytujacy serie obrazow do kontenera
     * @param dir sciezka do folderu z plikami
     */
    public Image3D(String dir) {
        String dirPathname = dir;
        File directory = new File(dirPathname);
        if (!directory.isDirectory()) {
            System.out.println(dirPathname + " is not directory");
            return;
        }
        File[] files = directory.listFiles();

        for (int i = 0; i < directory.listFiles().length; i++) {
            try {
                myImages.add(new MyImage(files[i].toString().replace("\\", "\\\\")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Arrays.stream(files).close();
    }
}