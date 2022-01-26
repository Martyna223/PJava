import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Klasa umożliwiająca wczytywanie obrazu warstwowego - tomogramu
 */
public class Image3D  {

    /**
     * kontener z serią obrazów
     */
    ArrayList<MyImage> myImages= new ArrayList<>();

    /**
     * Podstawowy konstruktor wczytujący serię obrazów do kontenera
     * @param dir ścieżka do folderu z plikami
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