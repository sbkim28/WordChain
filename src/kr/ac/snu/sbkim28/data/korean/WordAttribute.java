package kr.ac.snu.sbkim28.data.korean;

/**
 * 단어 속성 enum.
 * 표준국어대사전에 존재하는 단어 속성 중
 * 필요한 일부만을 작성하였음.
 * 단어 속성 검사를 편리하게 할 수 있도록 함.
 *
 * @author sbkim28
 * @version 1.0
 */
public enum WordAttribute {
    DEFAULT("", 1),
    ARCHAISM("옛말", 2),
    DIALECT("방언", 4),
    NORTHKOREAN("북한어", 8);

    /**
     * 단어가 가질 수 있는 속성.
     */
    public final String wordAttribute;
    public final int value;

    WordAttribute(String wordAttribute, int value) {
        this.wordAttribute = wordAttribute;
        this.value = value;
    }
    /**
     * String으로부터 해당하는 값을 찾아서 반환함.
     * @param keys String. keys의 값과 일치하는
     *            {@link WordAttribute#wordAttribute}를 가진
     *             enum 상수를 찾음.
     * @return 일치하는 enum 상수를 반환함. 일치하는 값이 없으면 {@link WordAttribute#DEFAULT}
     * 값을 반환함.
     */
    public static WordAttribute getClass(String keys){
        for (WordAttribute wc : values()){
            if(wc.wordAttribute.equals(keys))
                return wc;
        }
        return DEFAULT;
    }

    /**
     * 속성 검사에 사용되는 pattern을 만들고 반환함.
     * 만든 pattern은 {@link WordAttribute#matches(int, String)}에서
     * 사용가능.
     * @param attributes 단어의 품사. matches에서 어떤 단어의 속성이 주어진 attributes 중에 있는지
     *                확인할 수 있음.
     * @see WordAttribute#matches(int, WordAttribute)
     * @see WordAttribute#matches(int, String)
     */
    public static int makePattern(WordAttribute... attributes){
        int sum = 0;
        for (WordAttribute clazz : attributes){
            sum |= clazz.value;
        }
        return sum;
    }
    /**
     * 단어의 속성이 pattern에 부합하는지 검사함.
     * String으로 받은 단어의 품사를 enum 상수로 변환한 후,
     * {@link WordAttribute#matches(int, WordAttribute)}를 호출하여 그 결과를 반환함.
     * @param pattern 품사 검사에 사용되는 pattern.
     * @param key 단어의 속성.
     * @return 단어의 속성이 pattern에 포함되었다면 true, 아니라면 false.
     * @see WordAttribute#makePattern(WordAttribute...)
     * @see WordAttribute#matches(int, WordAttribute)
     */
    public static boolean matches(int pattern, String key){
        return matches(pattern, getClass(key));
    }
    /**
     * 단어의 속성이 pattern에 부합하는지 검사함.
     * 상수시간 내에 검사가 가능함.
     * @param pattern 속성 검사에 사용되는 pattern. {@link WordAttribute#makePattern(WordAttribute...)} 참고
     * @param key 단어의 속성.
     * @return 단어의 속성이 pattern에 포함되었다면 true, 아니라면 false.
     * @see WordAttribute#makePattern(WordAttribute...)
     * @see WordAttribute#matches(int, String)
     */
    public static boolean matches(int pattern, WordAttribute key){
        return (pattern & key.value) != 0;
    }

}
