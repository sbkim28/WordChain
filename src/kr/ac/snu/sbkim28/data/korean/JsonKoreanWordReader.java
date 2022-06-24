package kr.ac.snu.sbkim28.data.korean;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class JsonKoreanWordReader extends KoreanWordReader  {

    private static final String KEY_HOMONYM = "homonym";
    private static final String KEY_WORD = "word";
    private static final String KEY_PROPERTIES = "properties";
    private static final String KEY_WORDCLASS = "wordClass";
    private static final String KEY_ATTRIBUTES = "attributes";
    private static final String KEY_MEANINGS = "meanings";

    private Iterator<JSONObject> jsonWordIterator;

    public JsonKoreanWordReader(String path) throws IOException{
        super(path);
    }

    @Override
    public void initialize() throws IOException {
        JSONParser parser = new JSONParser();

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            //noinspection unchecked
            jsonWordIterator = ((JSONArray) parser.parse(br)).iterator();
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }

    @Override
    public List<KoreanWord> readAll() {
        List<KoreanWord> wordList = new LinkedList<>();
        while (hasNext()) {
            KoreanWord kw = next();
            if(kw != null)
                wordList.add(next());
        }
        return wordList;
    }

    private KoreanWord parse(JSONObject wordInfo) throws ParseException{
        int homonym = ((Long) wordInfo.get(KEY_HOMONYM)).intValue();
        String word = (String) wordInfo.get(KEY_WORD);

        JSONArray properties = (JSONArray) wordInfo.get(KEY_PROPERTIES);
        KoreanWordSpec[] kws = new KoreanWordSpec[properties.size()];
        int i = 0;
        int index = 0;
        for(Object obj : properties){
            JSONObject property = (JSONObject) obj;
            String wordClass = (String) property.get(KEY_WORDCLASS);

            JSONArray attributesJA = (JSONArray) property.get(KEY_ATTRIBUTES);
            String[] attributes = new String[attributesJA.size()];
            index = 0;
            for (Object attr: attributesJA) {
                attributes[index++] = (String) attr;
            }

            JSONArray meaningsJA = (JSONArray) property.get(KEY_MEANINGS);
            String[] meanings = new String[meaningsJA.size()];
            index = 0;
            for (Object meaning: meaningsJA) {
                meanings[index++] = (String) meaning;
            }
            kws[i++] = new KoreanWordSpec(wordClass, attributes, meanings);
        }
        KoreanWord kw = new KoreanWord(homonym, word);
        kw.spec = kws;
        return kw;
    }

    @Override
    public KoreanWord next() {
        KoreanWord ret = null;
        if(hasNext()) {
            JSONObject wordInfo = jsonWordIterator.next();
            try {
                ret = parse(wordInfo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    public boolean hasNext() {
        return jsonWordIterator.hasNext();
    }
}
