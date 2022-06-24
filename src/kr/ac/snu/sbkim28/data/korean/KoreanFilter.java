package kr.ac.snu.sbkim28.data.korean;

import kr.ac.snu.sbkim28.data.Filterable;

public class KoreanFilter implements Filterable<KoreanWord> {

    public static final int NOUN_PATTERN = 603136;
    public boolean checkValid;
    public boolean checkFaulty;
    public int inClassPattern;
    public int minLength;
    // exclude
    public int outAttributePattern;

    public KoreanFilter() {
        this(true, 2, false, NOUN_PATTERN, 0);
    }

    public KoreanFilter(boolean checkValid, int minLength, boolean checkFaulty, int inClassPattern, int outAttributePattern) {
        this.checkValid = checkValid;
        this.minLength = minLength;
        this.checkFaulty = checkFaulty;
        this.inClassPattern = inClassPattern;
        this.outAttributePattern = outAttributePattern;
    }

    public boolean valid(String word){
        char[] carrs = word.toCharArray();
        for (char c: carrs) {
            if ((c < 'ㄱ' || c > 'ㅎ') &&
                    (c < '가' || c > '힣')) {
                return false;
            }
        }
        return true;
    }

    public boolean isFaulty(String meaning){
        if(meaning.contains("→")){
            System.out.println(meaning);
        }else if(meaning.contains("잘못.")){
            System.out.println(meaning);
        }
        return meaning.contains("→");
    }

    @Override
    public boolean filter(KoreanWord word) {
        if(checkValid && !valid(word.word))
                return false;
        if(word.word.length() < minLength)
            return false;
        KoreanWordSpec[] specs = word.spec;
        specLabel: for (KoreanWordSpec spec: specs) {
            if(!WordClass.matches(inClassPattern, spec.wordClass))
                continue;
            for (String attr:
                 spec.attributes) {
                if (WordAttribute.matches(outAttributePattern, attr))
                    continue specLabel;
            }
            if(checkFaulty) {
                for (String meaning : spec.meanings) {
                    if(!isFaulty(meaning))
                        return true;
                }
                return false;
            }
            return true;
        }

        return false;
    }
}
