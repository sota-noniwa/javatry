package org.docksidestage.javatry.basic.st6.os;

/**
 * @author noniwa
 */
public class Windows extends St6OperationSystem {

    public Windows(String loginId) {
        super(loginId);
    }

    @Override
    public String getFileSeparator() {
        return "\\";
    }

    @Override
    public String getUserDirectory() {
        return "/Users/" + loginId;
    }
}
