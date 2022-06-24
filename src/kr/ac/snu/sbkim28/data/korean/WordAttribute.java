package kr.ac.snu.sbkim28.data.korean;

public enum WordAttribute {
    DEFAULT("", 1),
    ARCHAISM("옛말", 2),
    DIALECT("방언", 4),
    NORTHKOREAN("북한어", 8);


    public final String wordAttribute;
    public final int value;

    WordAttribute(String wordAttribute, int value) {
        this.wordAttribute = wordAttribute;
        this.value = value;
    }

    public static WordAttribute getClass(String keys){
        for (WordAttribute wc : values()){
            if(wc.wordAttribute.equals(keys))
                return wc;
        }
        return DEFAULT;
    }


    public static int makePattern(WordAttribute... classes){
        int sum = 0;
        for (WordAttribute clazz : classes){
            sum |= clazz.value;
        }
        return sum;
    }

    public static boolean matches(int pattern, String key){
        return matches(pattern, getClass(key));
    }

    public static boolean matches(int pattern, WordAttribute key){
        return (pattern & key.value) != 0;
    }

}
