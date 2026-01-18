package org.docksidestage.javatry.basic.st6.os;

/**
 * @author noniwa
 */
public class OldWindows extends St6OperationSystem {

    public OldWindows(String loginId) {
        super(loginId);
    }

    @Override
    public String getFileSeparator() {
        return "\\";
    }

    @Override
    public String getUserDirectory() {
        return "/Documents and Settings/" + loginId;
    }
}
