package com.ignited.wordchain.util;

public class KoreanUtil {

    public static String ruleOfThumb(String str){
        String ret;
        if (str.isEmpty()) {
            ret = str;
        }else {
            char c = str.charAt(0);
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
            ret = Character.toString(c);

        }
        return ret;
    }

    public static String[] reverseRuleOfThumb(String str){
        String [] ret = new String[]{str};
        if (!str.isEmpty()) {
            char c = str.charAt(0);
            if (c >= '가' && c <= '힣') {
                char[] inf = splitKorean(c);
                if (inf[0] == 0x1102) {
                    if (inf[1] == 0x1161 || inf[1] == 0x1162 || inf[1] == 0x1169 || inf[1] == 0x116C || inf[1] == 0x116E || inf[1] == 0x1173) {
                        inf[0] = 0x1105;
                        ret = new String[]{Character.toString(addKorean(inf))};
                    }
                } else if (inf[0] == 0x110B) {
                    if (inf[1] == 0x1167 || inf[1] == 0x116D || inf[1] == 0x1172 || inf[1] == 0x1175) {
                        inf[0] = 0x1102;
                        String s1 = Character.toString(addKorean(inf));
                        inf[0] = 0x1105;
                        String s2 = Character.toString(addKorean(inf));
                        ret = new String[]{s1, s2};
                    } else if (inf[1] == 0x1163 || inf[1] == 0x1168) {
                        inf[0] = 0x1105;
                        ret = new String[]{Character.toString(addKorean(inf))};
                    }
                }
            }
        }
        return ret;
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
