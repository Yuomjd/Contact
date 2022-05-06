package contacts;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Organization extends Contact implements Serializable {
    private String name;
    private String address;

    {
        creatTime = dateTimeFormatter.format(LocalDateTime.now());
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static Organization create(){
        Scanner scanner = new Scanner(System.in);
        Organization organization = new Organization();
        System.out.print("Enter the organization name:");
        String name = scanner.nextLine();
        organization.setName(name);
        System.out.print("Enter the address:");
        String address = scanner.nextLine();
        organization.setAddress(address);
        System.out.print("Enter the number:");
        String number = scanner.nextLine();
        organization.setNumber(number);
        System.out.println("The record added.");
        organization.lastEditTime = dateTimeFormatter.format(LocalDateTime.now());
        return organization;
    }


    public void editName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name:");
        String next = scanner.nextLine();
        this.name=next;
    }

    public void editAddress(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter address");
        String address = scanner.nextLine();
        this.setAddress(address);
    }
    public String info(){
        return name;
    }

    public void editNumber(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number:");
        String number = scanner.nextLine();
        this.setNumber(number);
    }

    @Override
    public String toString(){
        return "Organization name: " + name +"\nAddress: " + address + super.toString();
    }

    @Override
    public void edit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.print("Select a field (name, address,number):");
        Scanner scanner = new Scanner(System.in);
        String str=scanner.next();
        String str2 = str.substring(0,1).toUpperCase()+str.substring(1);
        String s2 = "edit"+str2;
        Method method = this.getClass().getMethod(s2);
        method.invoke(this);
        System.out.println("The record updated!");
        System.out.println();
    }

    public Organization searchQuery(String str) {
        str = str.toUpperCase();
        Pattern pattern = Pattern.compile(".*"+str+".*");
        //如果符合搜索结果返回这个对象
        String s = this.name.toUpperCase();
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()){
            return this;
        }
        String s2 = this.getNumber().toUpperCase();
        matcher = pattern.matcher(s2);
        if(matcher.find()){
            return this;
        }
        return null;
    }


    public static final long serialVersionUID=12312414123L;
}
