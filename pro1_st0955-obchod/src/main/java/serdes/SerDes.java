package serdes;

import model.Zbozi;

import java.io.IOException;
import java.util.List;

public interface SerDes {
    List<Zbozi> nacti(String soubor) throws IOException;
    void uloz(String soubor, List<Zbozi> seznam) throws IOException;
}
