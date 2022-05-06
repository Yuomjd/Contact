package contacts;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

public class Contacts implements Serializable {

    private  ArrayList<Contact> contacts =new ArrayList<>();

    File file;

    public Contacts() {
    }

    /**
     * 通过传入的字符串创建文件
     * @param str
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Contacts(String str) throws IOException, ClassNotFoundException {
        file = new File(str);
        if(!file.exists()){
            file.createNewFile();
            return;
        }
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
//        contacts= (ArrayList<Contact>) ois.readObject();
    }

    /**将首字母大写
     *
     * @param name
     * @return
     */
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);

    }

    /**
     * 接受一个Contact类型的对象，并将其添加到contacts中
     * @param contact
     */
    public  void addContact(Contact contact){
        contacts.add(contact);
    }

    /**
     *  为contacts添加一个对象
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void add() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the type (person, organization):");
        String str = scanner.next();
        String str2 = str.substring(0,1).toUpperCase()+str.substring(1);
        Class name1 = Class.forName("contacts."+str2);
        Method create = name1.getMethod("create");
        Object invoke = create.invoke(null);
        contacts.add((Contact) invoke);
        if(file!=null)
            this.output(contacts);
        System.out.println();
    }

    /**
     * 显示基本信息，并让用户选择提供详细信息
     * @throws IOException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public void list() throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        if(contacts.size()==0){
            System.out.println("No records to info!");
            return;
        }
        for (int i = 0; i < contacts.size(); i++) {
            Contact person = contacts.get(i);
            System.out.println(i+1+". "+ person.info());
        }
        System.out.println();
        System.out.print("[list] Enter action ([number], back):");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        if(Objects.equals(s,"back")) {
            return;
        }
        int i = Integer.parseInt(s);
        i--; //索引从0开始
        if (i >= contacts.size()) {
            System.out.println("Sorry,no such connection to recognize");
            return;
        } else {
            System.out.println(contacts.get(i));
            this.record(i);
        }
    }

    /**
     * 展示基本信息
     */
    public void list2(){
        if(contacts.size()==0){
            System.out.println("No records to info!");
            return;
        }
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            System.out.println(i+1+". "+ contact.info());
        }
    }

    /**
     * 删除指定位置上的联系人
     * @param i
     * @throws IOException
     */
    public void delete(Integer i) throws IOException {
        if(i< contacts.size()) {
            contacts.remove(i.intValue());
            System.out.println("The record removed!");
            this.output(contacts);
        }
        else {
            System.out.println("Nothing be removed");
        }
    }

    /**
     * 修改指定位置上的联系人
     * @param integer
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void edit(Integer integer) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Contact contact = contacts.get(integer);
        contact.edit();
        contact.lastEditTime = Contact.dateTimeFormatter.format(LocalDateTime.now());
        if(file!=null)
            this.output(contacts);
    }

    /**
     * 查询操作，让用户输入
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void search() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException, ClassNotFoundException {
        System.out.print("Enter search query:");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        //执行搜索操作
        ArrayList<Integer> arrayList = new ArrayList<>();
        int i = 0;
        for (Contact contact : contacts) {
            Contact c = contact.searchQuery(s);
            if(c!=null){
                arrayList.add(i);
            }
            i++;
        }
        System.out.println("Found "+ arrayList.size()+" results:");
        for (int j = 0; j <arrayList.size(); j++) {
            System.out.println(j+1+". "+contacts.get(arrayList.get(j)).info());
        }
        this.searchMenu(arrayList);
    }

    /**
     * 统计有多少联系人
     */
    public void count(){
        System.out.println("The Phone Book has "+ contacts.size() +" records\n");
    }

    /**
     * 该方法显示搜索结果菜单，并让用户选择进行的操作
     * @param arrayList
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void searchMenu(ArrayList<Integer> arrayList) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException, ClassNotFoundException {
        System.out.print("[search] Enter action ([number], back, again):");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        //再次执行搜索操作
        if(Objects.equals(s,"again")){
            this.search();
            return;
        }
        //回到主菜单
        if(Objects.equals(s,"back")){
            return;
        }
        int i = Integer.parseInt(s);
        i--; //索引从0开始
        if(i >= arrayList.size()){
            System.out.println("Sorry,no such connection to recognize");
            this.searchMenu(arrayList);
        }
        else {
            Contact contact = contacts.get(arrayList.get(i));
            System.out.println(contact);
            this.record(arrayList.get(i));
        }
    }


    /**
     * 让用户选择对指定位置上的字段进行的操作
     * @param integer
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void record(Integer integer) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, ClassNotFoundException {
        while(true) {
            System.out.print("[record] Enter action (edit, delete, menu):");
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            if(Objects.equals(next,"menu")){
                System.out.println();
                return;
            }
            if(Objects.equals(next,"edit")){
                System.out.println();
                this.edit(integer);
            }
            if(Objects.equals(next,"delete")){
                System.out.println();
                this.delete(integer);
            }
        }
    }

    /**
     * 主菜单
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void menu() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        for (;;) {
            System.out.print("[menu] Enter action (add, list, search, count, exit):");
            String next = scanner.next();
            if (Objects.equals(next, "exit")) {
                return;
            }
            Method method = Contacts.class.getMethod(next);
            method.invoke(this);
        }
    }


    /**
     * 将联系人写入到硬盘中
     * @param contacts
     * @throws IOException
     */
    public void output(ArrayList<Contact> contacts) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(contacts);
    }


    public static final long serialVersionUID=123123547899L;
}
