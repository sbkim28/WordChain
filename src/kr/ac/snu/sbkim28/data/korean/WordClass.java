package kr.ac.snu.sbkim28.data.korean;

/**
 * 단어 품사 enum.
 * 표준국어대사전에 존재하는 모든 단어 품사를 작성하였음.
 * 단어 품사 검사를 편리하게 할 수 있도록 함.
 *
 * @author sbkim28
 * @version 1.0
 */
public enum WordClass {
    ADJECTIVE("형용사", 1),
    AFFIX("접사", 2),
    AUXILIARY_ADJECTIVE("보조형용사", 4),
    AUXILIARY_VERB("보조동사", 8),
    ADVERB("부사", 16),
    ADVERB_INTERJECTION("부사·감탄사", 32),
    BOUNDNOUN("의존명사", 64),
    BOUNDNOUN_POSTPOSITION("의존명사·조사", 128),
    ENDING("어미", 256),
    INTERJECTION("감탄사", 512),
    INTERJECTION_NOUN("감탄사·명사", 1024),
    NONE("", 2048),
    NOUN("명사",4096),
    NOUN_ADVERB("명사·부사", 8192),
    NUMERAL("수사", 16384),
    NUMERAL_PRENOUN("수사·관형사", 32768),
    NUMERAL_PRENOUN_NOUN("수사·관형사·명사", 65536),
    POSTPOSITION("조사", 131072),
    PRENOUN("관형사", 262144),
    PRENOUN_NOUN("관형사·명사", 524288),
    PRENOUN_INTERJECTION("관형사·감탄사", 1048576),
    PRONOUN("대명사", 2097152),
    PRONOUN_PRENOUN("대명사·관형사", 4194304),
    PRONOUN_ADVERB("대명사·부사", 8388608),
    PRONOUN_INTERJECTION("대명사·감탄사", 16777216),
    VERB("동사", 33554432),
    VERB_ADJECTIVE("동사·형용사", 67108864),
    DEFAULT(null, 0);
    public final int value;

    /**
     * 단어가 가질 수 있는 품사.
     */
    public final String wordClass;
    WordClass(String wordClass, int value) {
        this.value = value;
        this.wordClass = wordClass;
    }

    /**
     * String으로부터 해당하는 값을 찾아서 반환함.
     * @param keys String. keys의 값과 일치하는
     *            {@link WordClass#wordClass}를 가진
     *             enum 상수를 찾음.
     * @return 일치하는 enum 상수를 반환함. 일치하는 값이 없으면 {@link WordClass#DEFAULT}
     * 값을 반환함.
     */
    public static WordClass getClass(String keys){
        for (WordClass wc : values()){
            if(wc.wordClass.equals(keys))
                return wc;
        }
        return DEFAULT;
    }

    /**
     * 품사 검사에 사용되는 pattern을 만들고 반환함.
     * 만든 pattern은 {@link WordClass#matches(int, String)}에서
     * 사용가능.
     * @param classes 단어의 품사. matches에서 어떤 단어의 품사가 주어진 classes 중에 있는지
     *                확인할 수 있음.
     * @see WordClass#matches(int, WordClass)
     * @see WordClass#matches(int, String)
     */
    public static int makePattern(WordClass... classes){
        int sum = 0;
        for (WordClass clazz : classes){
            sum |= clazz.value;
        }
        return sum;
    }

    /**
     * 단어의 품사가 pattern에 부합하는지 검사함.
     * String으로 받은 단어의 품사를 enum 상수로 변환한 후,
     * {@link WordClass#matches(int, WordClass)}를 호출하여 그 결과를 반환함.
     * @param pattern 품사 검사에 사용되는 pattern.
     * @param key 단어의 품사.
     * @return 단어의 품사가 pattern에 포함되었다면 true, 아니라면 false.
     * @see WordClass#makePattern(WordClass...)
     * @see WordClass#matches(int, WordClass)
     */
    public static boolean matches(int pattern, String key){
        return matches(pattern, getClass(key));
    }

    /**
     * 단어의 품사가 pattern에 부합하는지 검사함.
     * 상수시간 내에 검사가 가능함.
     * @param pattern 품사 검사에 사용되는 pattern. {@link WordClass#makePattern(WordClass...)} 참고
     * @param key 단어의 품사.
     * @return 단어의 품사가 pattern에 포함되었다면 true, 아니라면 false.
     * @see WordClass#makePattern(WordClass...)
     * @see WordClass#matches(int, String)
     */
    public static boolean matches(int pattern, WordClass key){
        return (pattern & key.value) != 0;
    }

}
