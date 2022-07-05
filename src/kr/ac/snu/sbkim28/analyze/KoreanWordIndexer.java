package kr.ac.snu.sbkim28.analyze;

import kr.ac.snu.sbkim28.util.KoreanUtils;

import static kr.ac.snu.sbkim28.util.KoreanUtils.KOREAN_LENGTH;

public class KoreanWordIndexer implements Indexer{
    private int[] indexTable;
    private int[] valueTable;

    private int length;

    public KoreanWordIndexer() {
        this(KOREAN_LENGTH);
    }

    public KoreanWordIndexer(int capacity){
        if(capacity <= 0 || capacity > KOREAN_LENGTH)
            throw new IllegalArgumentException("Invalid capacity:" + capacity);
        indexTable = new int[KOREAN_LENGTH];
        valueTable = new int[capacity];
        int i;
        for (i = 0; i<capacity; ++i){
            indexTable[i] = -1;
            valueTable[i] = -1;
        }
        for (; i<KOREAN_LENGTH; ++i){
            indexTable[i] = -1;
        }
    }

    @Override
    public int getIndex(char c) {
        return indexTable[KoreanUtils.convert2int(c)];
    }

    @Override
    public char getChar(int index) {
        return KoreanUtils.convert2char(valueTable[index]);
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public boolean addChar(char c) {
        int value = KoreanUtils.convert2int(c);
        if (indexTable[value] == -1) {
            indexTable[value] = length;
            valueTable[length++] = value;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsChar(char c) {
        return indexTable[KoreanUtils.convert2int(c)] != -1;
    }

    @Override
    public boolean removeChar(char c) {
        int value = KoreanUtils.convert2int(c);

        int index = indexTable[value];

        if(index == -1)
            return false;

        indexTable[value] = -1;
        valueTable[index] = -1;

        for (int i = index + 1; i < length; ++i){
            --indexTable[valueTable[i]];
            valueTable[i - 1] = valueTable[i];
        }

        valueTable[length - 1] = -1;
        --length;
        return true;
    }
}
