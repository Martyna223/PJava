package reader;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import com.pixelmed.display.SingleImagePanel;
import com.pixelmed.display.SourceImage;
import com.pixelmed.display.event.FrameSelectionChangeEvent;
import com.pixelmed.event.ApplicationEventDispatcher;
import com.pixelmed.event.EventContext;

/**
 * Obsługuje przełączanie serii zdjęć
 */
public class OverriddenSingleImagePanelForDemo extends SingleImagePanel {

    //initialize these to some default values
    private static final long serialVersionUID = 1L;
    private int frameIndex = 0;
    private int MaxFrames = 1;

    /**
     * Ustawia liczbę klatek
     *
     * @param sImg obraz źródłowy
     */
    public OverriddenSingleImagePanelForDemo(SourceImage sImg) {
        super(sImg);
        MaxFrames = sImg.getNumberOfFrames();
        this.setSideAndViewAnnotationString(getTextToDisplay(1),30, "SansSerif",Font.BOLD, 14, Color.WHITE,true);
    }

    private String getTextToDisplay(int frameIndexNumber) {
        return " Window Width->" + (int) this.windowWidth
                + " Level(or Center)->" + (int) this.windowCenter
                + " Frame Index->" + frameIndexNumber;
    }

    /**
     * Przechodzi pomiędzy zdjęciami
     * @param e wcisnięty przycisk
     */
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if(frameIndex == MaxFrames){ //this is to reset the frame loop
            frameIndex = 0;
        }
        ApplicationEventDispatcher.getApplicationEventDispatcher()
                .processEvent(new FrameSelectionChangeEvent(new EventContext("Pass info here"), frameIndex++));
        UpdateDisplayInformation();

    }

    /**
     * Aktualizuje wyświetlany obraz
     */
    private void UpdateDisplayInformation() {
        this.sideAndViewAnnotationString = getTextToDisplay(frameIndex);
    }

    /**
     * Wywołuje aktualizację obrazu
     * @param e zdarzenie
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        UpdateDisplayInformation();
    }

}