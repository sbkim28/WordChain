package kr.ac.snu.sbkim28.data.korean;

import kr.ac.snu.sbkim28.data.Filterable;

/**
 * 한국어 단어 필터링을 위한 클래스
 * 한국어를 단어의 품사나 단어의 속성 등에 따라서
 * 원하는 단어만을 필터링할 수 있는 기능을 제공.
 * @author sbkim28
 * @see kr.ac.snu.sbkim28.data.Filterable
 */
public class KoreanFilter implements Filterable<KoreanWord> {

    /**
     * WordClass에서 명사를 포함하는 품사 전체로 구성된 패턴.
     * @see WordClass
     * @see WordClass#makePattern(WordClass...)
     */
    public static final int NOUN_PATTERN = 603136;
    /**
     * 단어가 한국어로 구성되어 있는지 검사하도록 함.
     * 옛한글을 포함하는 단어를 제외시킴.
     * Default true
     */
    public boolean checkValid;
    /**
     * 잘못된 단어인지 검사하도록 함.
     * 잘못된 단어일 경우 제외시킴.
     * Default false
     */
    public boolean checkFaulty;
    /**
     * 포함시킬 단어의 품사 패턴
     * Default {@link KoreanFilter#NOUN_PATTERN}
     * @see WordClass#makePattern(WordClass...)
     */
    public int inClassPattern;
    /**
     * 단어의 최소 길이. 주어진 길이보다 짧은 단어는 제외시킴.
     * Default 2
     */
    public int minLength;
    // exclude

    /**
     * 제외시킬 단어의 속성 패턴.
     * 특정 속성을 갖는 단어를 제외함.
     * Default 0, 아무 단어도 제외시키지 않음.
     * @see WordAttribute#makePattern(WordAttribute...)
     */
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
            // 검사를 위하여. 나중에 제거 필요.
            System.out.println(meaning);
        }else if(meaning.contains("잘못.")){
            // 검사를 위하여. 나중에 제거 필요.
            System.out.println(meaning);
        }
        return meaning.contains("→");
    }

    /**
     * 단어가 정해진 기준을 충족시키는지 검사하여 필터링함.
     * 해당 인스턴스의 field에서 설정된 값에 따라서 단어가
     * 원하는 조건을 충족시키는지 검사함.
     */
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
