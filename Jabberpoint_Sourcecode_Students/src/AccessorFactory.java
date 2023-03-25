public class AccessorFactory {
    public static Readable createReader(String readerType) {
        return switch (readerType) {
            case "DEMO" -> new DemoPresentation();
            case "XML" -> new XMLReader();
            default -> throw new IllegalArgumentException("Unknown reader type: " + readerType);
        };
    }

    public static Writable createWriter(String writerType) {
        if (writerType.equals("XML")) {
            return new XMLWriter();
        }
        throw new IllegalArgumentException("Unknown writer type: " + writerType);
    }
}