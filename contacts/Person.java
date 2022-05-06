package contacts;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person extends Contact implements Serializable {
    private String name;
    private String surname;

    private Gender gender;

    private LocalDate birth;

    {
        creatTime = dateTimeFormatter.format(LocalDateTime.now());
    }
    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getSurname() {
        return surname;
    }

    private void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        try {
            this.birth = LocalDate.parse(birth);
        } catch (Exception e) {
            System.out.println("Bad birth date!");
            this.birth = null;
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(String gender) {
        try {
            this.gender=Gender.valueOf(gender);
        } catch (IllegalArgumentException e) {
            System.out.println("Bad gender!");
            this.gender = null;
        }
    }

    public Person() {
    }

    public Person(String name, String surname, String number) {
        this.name = name;
        this.surname = surname;
        if(!setNumber(number)){
            System.out.println("Wrong number format!");
        };
    }

    @Override
    public String toString() {
        String birth1;
        String gender2;
        if(birth == null){
             birth1 = "[no data]";
        }
        else
            birth1 = birth.toString();
        if(gender == null){
            gender2 = "[no data]";
        }
        else
            gender2 = gender.toString();
        return "Name: "+name+"\nSurname: "+surname+"\nBirth date: "+ birth1
                +"\nGender: "+gender2+"\n"+super.toString();

    }

    public void editName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name:");
        String next = scanner.nextLine();
        this.name=next;
    }

    public void editSurname() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter surname:");
        String next = scanner.nextLine();
        this.surname=next;
    }

    public void editNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number:");
        String number = scanner.nextLine();
        this.setNumber(number);
    }

    public void editBirth(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Birth:");
        String birth = scanner.nextLine();
        this.setBirth(birth);
    }

    public void editGender(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Gender:");
        String gender = scanner.nextLine();
        this.setGender(gender);
    }

    public String info(){
        return name+" "+surname;
    }
    public static Person create(){
        Person person = new Person();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name: ");
        String name=scanner.nextLine();
        person.setName(name);
        System.out.print("Enter the surname: ");
        String surname=scanner.nextLine();
        person.setSurname(surname);
        System.out.print("Enter the birth date: ");
        String date = scanner.nextLine();
        person.setBirth(date);
        System.out.print("Enter the gender (M, F): ");
        String gender = scanner.nextLine();
        gender.toUpperCase();
        person.setGender(gender);
        System.out.print("Enter the number: ");
        String number=scanner.nextLine();
        person.setNumber(number);
        person.lastEditTime = dateTimeFormatter.format(LocalDateTime.now());
        System.out.println("The record added.");
        return person;
    }

    @Override
    public void edit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.print("Select a field (name, surname, birth, gender, number):");
        Scanner scanner = new Scanner(System.in);
        String str=scanner.next();
        String str2 = str.substring(0,1).toUpperCase()+str.substring(1);
        String s2 = "edit"+str2;
        Method method = this.getClass().getMethod(s2);
        method.invoke(this);
        lastEditTime = dateTimeFormatter.format(LocalDateTime.now());
        System.out.println("Saved");
        System.out.println(this);
    }

    @Override
    public Person searchQuery(String str) {
        str = str.toUpperCase();
        Pattern pattern = Pattern.compile(".*"+str+".*");
        //如果符合搜索结果返回这个对象
        String s = this.name.toUpperCase();
        String s2 = this.surname.toUpperCase();
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            return this;
        }
        matcher = pattern.matcher(s2);
        if(matcher.find()){
            return this;
        }
        String s3 = this.getNumber().toUpperCase();
        matcher = pattern.matcher(s3);
        if(matcher.find()){
            return this;
        }
        return null;
    }

    public static final long serialVersionUID=423123L;
}
