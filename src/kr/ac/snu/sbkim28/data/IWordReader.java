package kr.ac.snu.sbkim28.data;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public interface IWordReader extends Iterator<IWord> {
    List<? extends IWord> readAll() throws IOException;

    @Override
    boolean hasNext();

    @Override
    IWord next();
}
