package contacts;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.Scanner;

public abstract class Contact implements Serializable {
    private String number;

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    protected String creatTime;

    protected String lastEditTime;
    protected String getNumber() {
        return number;
    }

    protected boolean setNumber(String number) {
        String reg = "^\\+?(\\(\\w+\\)|\\w+[ -]\\(\\w{2,}\\)|\\w+)([ -]\\w{2,})*";
        if (number.matches(reg)) {
            this.number = number;
            return true;
        } else {
            this.number = "[no number]";
            return false;
        }
    }

    //显示用户基本信息
    public abstract String info();

    @Override
    public String toString() {
        return "Number: "+number+"\nTime created: "+creatTime+
                "\nTime last edit: "+lastEditTime+"\n";
    }


    //修改的抽象方法
    public abstract void edit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    //搜索的抽象方法
    public abstract Contact searchQuery(String str);

    public static final long serialVersionUID=123123L;

}
