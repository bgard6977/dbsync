package com.zenplanner.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");

        if(args.length < 4) {
            FormMain frm = new FormMain();
            frm.setVisible(true);
        } else {
            Map<String, List<Object>> filters = new HashMap<>();
            List<Object> vals = Arrays.asList(args[3].split(","));
            filters.put(args[2], vals);
            List<String> ignoreTables = new ArrayList<>(); // TODO: Allow command line input
            sync(args[0], args[1], filters, ignoreTables);
        }
    }

    private static void sync(String srcCon, String dstCon, Map<String, List<Object>> filters, List<String> ignoreTables) throws Exception {
        try (Connection scon = DriverManager.getConnection(srcCon)) {
            try (Connection dcon = DriverManager.getConnection(dstCon)) {
                DbComparator comp = new DbComparator();
                comp.synchronize(scon, dcon, filters, ignoreTables, true);
            }
        }
    }

}
