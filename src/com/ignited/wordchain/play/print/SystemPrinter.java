package com.ignited.wordchain.play.print;

public class SystemPrinter implements GamePrintable {

    private String system;


    public SystemPrinter() {this("System"); }

    public SystemPrinter(String system) {
        this.system = system;
    }

    @Override
    public void failMsg(FailType ft) {
        System.out.print(system + ":");
        switch (ft){
            case UNKNOWN:
                System.out.println("알 수 없는 오류가 발생했습니다."); break;
            case TOO_SHORT:
                System.out.println("단어의 길이가 너무 짧습니다."); break;
            case USED_WORD:
                System.out.println("이미 사용한 단어입니다"); break;
            case UNKNOWN_WORD:
                System.out.println("사전에 없는 단어입니다."); break;
            case UNMATCHING_KEY:
                System.out.println("첫글자가 일치하지 않습니다"); break;
            case INVALID_LETTERS:
                System.out.println("올바르지 않은 문자입니다."); break;
        }
    }

    @Override
    public void finishMsg(String winner) { }

    @Override
    public void playerStateMsg(String player, char... chainKey) {
        System.out.print(player + ":");
        if(chainKey[1] == 0){
            System.out.print(chainKey[0]);
        } else {
            System.out.print(chainKey[0] + "(" + chainKey[1] + ")");
        }
        System.out.print("> ");
    }

    @Override
    public void playerSubmit(String sub) {
        System.out.println(sub);
    }

}
