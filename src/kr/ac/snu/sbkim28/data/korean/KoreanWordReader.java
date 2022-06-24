package kr.ac.snu.sbkim28.data.korean;

import kr.ac.snu.sbkim28.data.IWordReader;

import java.io.IOException;
import java.util.List;

public abstract class KoreanWordReader implements IWordReader {
    public final String path;

    public KoreanWordReader(String path) throws IOException {
        this.path = path;
        initialize();
    }

    public abstract void initialize() throws IOException;

    @Override
    public abstract List<KoreanWord> readAll() throws IOException;

    @Override
    public abstract boolean hasNext();

    @Override
    public abstract KoreanWord next();
}
