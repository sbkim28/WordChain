package kr.ac.snu.sbkim28.data.korean;

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
    public final String wordClass;
    WordClass(String wordClass, int value) {
        this.value = value;
        this.wordClass = wordClass;
    }

    public static WordClass getClass(String keys){
        for (WordClass wc : values()){
            if(wc.wordClass.equals(keys))
                return wc;
        }
        return DEFAULT;
    }

    public static int makePattern(WordClass... classes){
        int sum = 0;
        for (WordClass clazz : classes){
            sum |= clazz.value;
        }
        return sum;
    }

    public static boolean matches(int pattern, String key){
        return matches(pattern, getClass(key));
    }

    public static boolean matches(int pattern, WordClass key){
        return (pattern & key.value) != 0;
    }

}
