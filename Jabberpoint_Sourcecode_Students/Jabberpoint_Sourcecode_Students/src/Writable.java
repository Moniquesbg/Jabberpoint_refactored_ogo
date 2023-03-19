import java.io.IOException;

public interface Writable {
    void saveFile(Presentation p, String fn) throws IOException;
}
