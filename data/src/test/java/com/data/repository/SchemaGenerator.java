package com.data.repository;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Created by minisha on 27/1/16.
 */
public class SchemaGenerator {

    private final Configuration cfg;

    public static void main(String args[]) throws ClassNotFoundException {
        SchemaGenerator gen = new SchemaGenerator("sg.com.cic.cicportal.data.entity");
        gen.generate(Dialect.MYSQL);
    }

    private void generate(Dialect dialect) {
        cfg.setProperty("hibernate.dialect", dialect.getDialectClass());
        SchemaExport export = new SchemaExport(cfg);
        export.setOutputFile("ddl_" + dialect.name() + ".sql");
        export.execute(true, false, false, false);
    }

    public SchemaGenerator(String packageName) throws ClassNotFoundException {
        cfg = new Configuration();
        cfg.setProperty("hibernate.hbm2ddl.auto", "create");
        cfg.setProperty("hibernate.default_schema", "CIC");
        for (Class<Object> clazz : getClasses(packageName)) {
            cfg.addAnnotatedClass(clazz);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Class<Object>> getClasses(String packageName) throws ClassNotFoundException {
        List<Class<Object>> classes = new ArrayList<>();
        File directory;
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Cant get class loaded");
            }
            String path = packageName.replace('.', '/');
            URL resource = cld.getResource(path);
            if (resource == null) {
                throw new ClassNotFoundException("No Resource for" + path);
            }
            directory = new File(resource.getFile());
        } catch (NullPointerException ex) {
            throw new ClassNotFoundException(packageName + " does not appear to be valid");
        }
        if (directory.exists()) {
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                if (files[i].endsWith(".class")) {
                    classes.add((Class<Object>) Class
                            .forName(packageName + '.' + files[i].substring(0, files[i].length() - 6)));
                }
            }
        }

        return classes;
    }

    private static enum Dialect {
        MYSQL("org.hibernate.dialect.MySQLDialect"), //
        HSQL("org.hibernate.dialect.HSQLDialect");

        private final String dialectClass;

        private Dialect(String dialectClass) {
            this.dialectClass = dialectClass;
        }

        public String getDialectClass() {
            return dialectClass;
        }
    }
}
