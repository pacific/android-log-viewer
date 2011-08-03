package org.bitbucket.mlopatkin.android.logviewer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class Configuration {

    private Properties properties = new Properties();

    private static final String CONFIG_FILE_NAME = "logview.properties";

    private void loadFromFile() {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(CONFIG_FILE_NAME));
            try {
                properties.load(in);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Configuration() {
        loadFromFile();
    }

    private static Configuration instance = new Configuration();

    public static class ui {
        private static final String PREFIX = "ui.";
        private static List<String> columns_;

        private static void initColumns() {
            String columnsValue = instance.properties.getProperty(PREFIX + "columns",
                    "time, pid, priority, tag, message");
            String[] columnNames = StringUtils.split(columnsValue, ",");
            List<String> result = new ArrayList<String>();
            for (String s : columnNames) {
                result.add(s.toLowerCase().trim());
            }
            columns_ = Collections.unmodifiableList(result);
        }

        public synchronized static List<String> columns() {
            if (columns_ == null) {
                initColumns();
            }
            return columns_;
        }
    }
}
