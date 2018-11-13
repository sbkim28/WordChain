package com.ignited.wordchain.util;

public class KoreanUtil {

    public static String ruleOfThumb(String str){
        String ret = null;
        if (!str.isEmpty()) {
            char c = str.charAt(0);

            ret = Character.toString(ruleOfThumb(c));

        }
        return ret;
    }

    public static String[] reverseRuleOfThumb(String str){
        String [] ret = null;
        if (!str.isEmpty()) {
            char c = str.charAt(0);
            char[] cs = reverseRuleOfThumb(c);
            ret = new String[cs.length];
            for (int i = 0; i < cs.length; ++i){
                ret[i] = Character.toString(cs[i]);
            }
        }
        return ret;
    }

    public static char[] reverseRuleOfThumb(char c){
        char[] ret = new char[]{c};
        if (c >= '가' && c <= '힣') {
            char[] inf = splitKorean(c);
            if (inf[0] == 0x1102) {
                if (inf[1] == 0x1161 || inf[1] == 0x1162 || inf[1] == 0x1169 || inf[1] == 0x116C || inf[1] == 0x116E || inf[1] == 0x1173) {
                    inf[0] = 0x1105;
                    ret[0] = addKorean(inf);
                }
            } else if (inf[0] == 0x110B) {
                if (inf[1] == 0x1167 || inf[1] == 0x116D || inf[1] == 0x1172 || inf[1] == 0x1175) {
                    inf[0] = 0x1102;
                    char c1 = addKorean(inf);
                    inf[0] = 0x1105;
                    char c2 = addKorean(inf);
                    ret = new char[]{c1, c2};
                } else if (inf[1] == 0x1163 || inf[1] == 0x1168) {
                    inf[0] = 0x1105;
                    ret[0] = addKorean(inf);
                }
            }
        }
        return ret;
    }

    public static char ruleOfThumb(char c){
        if (c >= '가' && c <= '힣') {
            char[] inf = splitKorean(c);
            if(inf[0] == 0x1102){
                if(inf[1] == 0x1167 || inf[1] == 0x116D || inf[1] == 0x1172 || inf[1] == 0x1175){
                    inf[0] = 0x110B;
                }
            }else if(inf[0] == 0x1105){
                if(inf[1] == 0x1163 || inf[1] == 0x1167 || inf[1] == 0x1168 || inf[1] == 0x116D || inf[1] == 0x1172 || inf[1] == 0x1175 ){
                    inf[0] = 0x110B;
                }else if(inf[1] == 0x1161 ||inf[1] == 0x1162 ||inf[1] == 0x1169 ||inf[1] == 0x116C ||inf[1] == 0x116E ||inf[1] == 0x1173 ){
                    inf[0] = 0x1102;
                }
            }
            c = addKorean(inf);
        }
        return c;
    }



    public static char[] splitKorean(char kor){
        int c = kor - 0xAC00;

        char first = (char) ((((c - (c % 28)) / 28) / 21) + 0x1100);
        char middle = (char) ((((c - (c % 28)) / 28) % 21) + 0x1161);
        char last = (char) ((c % 28) + 0x11a7);
        return new char[]{first, middle,last};
    }

    public static char addKorean(char[] c){
        return (char) (0xAC00 + (c[0] - 0x1100) * 28 * 21 + (c[1] - 0x1161) * 28  + c[2] - 0x11a7);
    }
}
