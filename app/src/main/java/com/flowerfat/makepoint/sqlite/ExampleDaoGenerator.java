package com.flowerfat.makepoint.sqlite;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by 明明大美女 on 2015/10/10.
 */
public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.flowerfat.makepoint.sqlite");

        addPoint(schema);
        addNote(schema);

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }


    private static void addPoint(Schema schema) {
        Entity note = schema.addEntity("Point");
        note.addIdProperty();
        note.addStringProperty("point1");
        note.addStringProperty("point2");
        note.addStringProperty("point3");
        note.addStringProperty("point4");
        note.addDateProperty("date");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

}
