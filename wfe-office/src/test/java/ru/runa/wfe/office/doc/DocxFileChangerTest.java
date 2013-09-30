package ru.runa.wfe.office.doc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.testng.Assert;
import org.testng.annotations.Test;

import ru.runa.wfe.commons.CalendarUtil;
import ru.runa.wfe.commons.ClassLoaderUtil;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.var.FileVariable;
import ru.runa.wfe.var.VariableDefinition;
import ru.runa.wfe.var.dto.WfVariable;
import ru.runa.wfe.var.format.ActorFormat;
import ru.runa.wfe.var.format.DateFormat;
import ru.runa.wfe.var.format.DateTimeFormat;
import ru.runa.wfe.var.format.ListFormat;
import ru.runa.wfe.var.format.MapFormat;
import ru.runa.wfe.var.format.StringFormat;
import ru.runa.wfe.var.format.TimeFormat;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;

public class DocxFileChangerTest extends Assert {
    private static final String[] prefixes = { "lo_" };

    // Map<String, Object> data = Maps.newHashMap();
    // data.put("contractNumber", "2");
    // data.put("listIndex", 2L);
    // data.put("actorList", Lists.newArrayList(new Actor("name", "address"),
    // new Actor("Ivanov", "Pervomayskaya str 30a"), new Actor("Иванов",
    // "улица Первомайская 30А")));
    // data.put("stringList", Lists.newArrayList("Ivanov", "Petrov",
    // "Sidorov"));
    // data.put("addresses", Lists.newArrayList("address1",
    // "Pervomayskaya str 30a", "5 avenu"));
    // data.put("image1", new FileVariable("7822.png",
    // ByteStreams.toByteArray(ClassLoaderUtil.getAsStreamNotNull("7822.png",
    // getClass())), null));
    // Map<String, Actor> actors = Maps.newHashMap();
    // actors.put("1", new Actor("adamov_a", "", "Адамов А.А.", 444L));
    // actors.put("2", new Actor("borisov", "", "Борисов Б.А.", 333L));
    // actors.put("3", new Actor("denisov", "", "Денисов Алексей", 555L));
    // data.put("actorMap", actors);
    // data.put("currentDate", createDateVariable("currentDate",
    // DateFormat.class.getName()));
    // data.put("currentDateTime", createDateVariable("currentDateTime",
    // DateTimeFormat.class.getName()));
    // data.put("currentTime", createDateVariable("currentTime",
    // TimeFormat.class.getName()));

    @Test
    public void testSimpleChange() throws IOException {
        Map<String, Object> data = Maps.newHashMap();
        data.put("contractNumber", "2");
        data.put("listIndex", 2L);
        data.put("currentDate", createVariable("currentDate", DateFormat.class.getName(), new Date()));
        data.put("currentDateTime", createVariable("currentDateTime", DateTimeFormat.class.getName(), new Date()));
        data.put("currentTime", createVariable("currentTime", TimeFormat.class.getName(), new Date()));
        data.put(
                "actorList",
                createVariable("actorList", ListFormat.class.getName() + "(" + ActorFormat.class.getName() + ")",
                        Lists.newArrayList(new Actor("ACTOR1", "address"), new Actor("Ivanov", "Pervomayskaya str 30a"))));
        Map<Object, Actor> actors = Maps.newHashMap();
        actors.put("2", new Actor("adamov_a", "", "Адамов А.А.", 444L));
        actors.put("borisov", new Actor("borisov", "", "Борисов Б.А.", 333L));
        actors.put(2L, new Actor("denisov", "", "Денисов Алексей", 555L));
        data.put(
                "actorMap",
                createVariable("actorMap", MapFormat.class.getName() + "(" + StringFormat.class.getName() + ", " + ActorFormat.class.getName() + ")",
                        actors));
        testDocx(true, "simple_change.docx", data);
    }

    @Test
    public void testTableColumns() throws IOException {
        Map<String, Object> data = Maps.newHashMap();
        data.put(
                "actorList",
                createVariable("actorList", ListFormat.class.getName() + "(" + ActorFormat.class.getName() + ")",
                        Lists.newArrayList(new Actor("name", "address"), new Actor("Ivanov", "Pervomayskaya str 30a"))));
        data.put(
                "stringList",
                createVariable("stringList", ListFormat.class.getName() + "(" + StringFormat.class.getName() + ")",
                        Lists.newArrayList("Ivanov", "Petrov", "Sidorov")));
        data.put(
                "dateList",
                createVariable(
                        "dateList",
                        ListFormat.class.getName() + "(" + DateFormat.class.getName() + ")",
                        Lists.newArrayList(new Date(), CalendarUtil.convertToDate("01.01.2013", CalendarUtil.DATE_WITHOUT_TIME_FORMAT),
                                CalendarUtil.convertToDate("17.02.1982", CalendarUtil.DATE_WITHOUT_TIME_FORMAT))));
        Map<String, Actor> actors = Maps.newHashMap();
        actors.put("1", new Actor("adamov_a", "", "Адамов А.А.", 444L));
        actors.put("2", new Actor("borisov", "", "Борисов Б.А.", 333L));
        actors.put("3", new Actor("denisov", "", "Денисов Алексей", 555L));
        data.put(
                "actorMap",
                createVariable("actorMap", MapFormat.class.getName() + "(" + StringFormat.class.getName() + ", " + ActorFormat.class.getName() + ")",
                        actors));
        testDocx(true, "tables.docx", data);
    }

    @Test
    public void testImages() throws IOException {
        Map<String, Object> data = Maps.newHashMap();
        data.put("image1",
                new FileVariable("image1.jpg", ByteStreams.toByteArray(ClassLoaderUtil.getAsStreamNotNull("image1.jpg", getClass())), null));
        data.put("image2",
                new FileVariable("image2.png", ByteStreams.toByteArray(ClassLoaderUtil.getAsStreamNotNull("image2.png", getClass())), null));
        testDocx(true, "images.docx", data);
    }

    @Test
    public void testLoops() throws IOException {
        Map<String, Object> data = Maps.newHashMap();
        data.put(
                "actorList",
                createVariable("actorList", ListFormat.class.getName() + "(" + ActorFormat.class.getName() + ")",
                        Lists.newArrayList(new Actor("name", "address"), new Actor("Ivanov", "Pervomayskaya str 30a"))));
        data.put(
                "stringList",
                createVariable("stringList", ListFormat.class.getName() + "(" + ActorFormat.class.getName() + ")",
                        Lists.newArrayList("Ivanov", "Petrov", "Sidorov")));
        data.put(
                "dateList",
                createVariable(
                        "dateList",
                        ListFormat.class.getName() + "(" + DateFormat.class.getName() + ")",
                        Lists.newArrayList(new Date(), CalendarUtil.convertToDate("01.01.2013", CalendarUtil.DATE_WITHOUT_TIME_FORMAT),
                                CalendarUtil.convertToDate("17.02.1982", CalendarUtil.DATE_WITHOUT_TIME_FORMAT))));
        Map<String, Actor> actors = Maps.newHashMap();
        actors.put("1", new Actor("adamov_a", "", "Адамов А.А.", 444L));
        actors.put("2", new Actor("borisov", "", "Борисов Б.А.", 333L));
        actors.put("3", new Actor("denisov", "", "Денисов Алексей", 555L));
        data.put(
                "actorMap",
                createVariable("actorMap", MapFormat.class.getName() + "(" + StringFormat.class.getName() + ", " + ActorFormat.class.getName() + ")",
                        actors));
        data.put("currentDateTime", createVariable("currentDateTime", DateTimeFormat.class.getName(), new Date()));
        testDocx(true, "loops.docx", data);
    }

    // @Test
    public void testIf() throws IOException {
        Map<String, Object> data = Maps.newHashMap();
        data.put("contractNumber", "2");
        data.put("listIndex", 2L);
        data.put("actorList", Lists.newArrayList(new Actor("name", "address"), new Actor("Ivanov", "Pervomayskaya str 30a")));
        data.put("stringList", Lists.newArrayList("Ivanov", "Petrov", "Sidorov"));
        data.put(
                "dateList",
                Lists.newArrayList(new Date(), CalendarUtil.convertToDate("01.01.2013", CalendarUtil.DATE_WITHOUT_TIME_FORMAT),
                        CalendarUtil.convertToDate("17.02.1982", CalendarUtil.DATE_WITHOUT_TIME_FORMAT)));
        data.put("currentDate", createVariable("currentDate", DateFormat.class.getName(), new Date()));
        data.put("currentDateTime", createVariable("currentDateTime", DateTimeFormat.class.getName(), new Date()));
        data.put("currentTime", createVariable("currentTime", TimeFormat.class.getName(), new Date()));
        // testDocx(true, "decisions.docx", data);
    }

    private WfVariable createVariable(String name, String format, Object value) {
        VariableDefinition definition = new VariableDefinition(true, name, name);
        definition.setFormat(format);
        WfVariable variable = new WfVariable(definition, value);
        return variable;
    }

    private void testDocx(boolean strictMode, String templateFileName, Map<String, Object> data) throws IOException {
        for (String appPrefix : prefixes) {
            String appTemplateFileName = appPrefix + templateFileName;
            InputStream templateInputStream = ClassLoaderUtil.getAsStreamNotNull(appTemplateFileName, getClass());
            DocxConfig config = new DocxConfig();
            config.setStrictMode(strictMode);
            DocxFileChanger changer = new DocxFileChanger(config, new TestVariableProvider(data), templateInputStream);
            XWPFDocument document = changer.changeAll();
            try {
                document.write(new FileOutputStream("result_" + appTemplateFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
