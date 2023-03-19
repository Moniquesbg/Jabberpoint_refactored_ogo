import java.io.IOException;

public interface Readable {
    void loadFile(Presentation p, String fn) throws IOException;
}
