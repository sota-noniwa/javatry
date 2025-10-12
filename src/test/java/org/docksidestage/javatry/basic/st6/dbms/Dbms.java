package org.docksidestage.javatry.basic.st6.dbms;

public abstract class Dbms {

    public String buildPagingQuery(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);
        return "offset " + offset + " limit " + pageSize;
    }
}
