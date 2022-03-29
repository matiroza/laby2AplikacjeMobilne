import java.util.*;
import java.util.stream.Collectors;

public class Source {
    public static void main(String[] args) {
        LinkedList<Student> studentList = new LinkedList<>();
            Student s1 = new Student("Mateusz", "gRozanowski", 1999, StudentCondition.nieobecny,1);
            Student s2 = new Student("Mateusz1", "fRozanowski1", 1999, StudentCondition.odrabiajacy,2);
            Student s3 = new Student("Mateusz2", "eRozapowski2", 1999, StudentCondition.chory,3);
            Student s4 = new Student("Mateusz3", "dRozapowski3", 1999, StudentCondition.chory,4);
            Student s5 = new Student("Mateusz4", "cRozanowski4", 1999, StudentCondition.chory,10);
            Student s6 = new Student("Mateusz5", "bRozanowski5", 1999, StudentCondition.chory,9);
            Student s7 = new Student("Mateusz6", "aRozanowski6", 1999, StudentCondition.chory,8);
            Student s8 = new Student("Mateusz7", "oRozanowski7", 1999, StudentCondition.nieobecny,7);
            Student s9 = new Student("Mateusz8", "pRozanowski8", 1999, StudentCondition.nieobecny,23);
            Student s10 = new Student("Mateusz9", "zRozanowski9", 1999, StudentCondition.nieobecny,6);

            studentList.add(s1);
            studentList.add(s2);
            studentList.add(s3);
            studentList.add(s4);
            studentList.add(s5);
            studentList.add(s6);
            studentList.add(s7);
            studentList.add(s8);
            studentList.add(s9);
            studentList.add(s10);

        Class c = new Class("P2", studentList);
        Class c2 = new Class("P1", studentList);
        c.addPoints(s10,300);
        System.out.println(c.countByCondition(StudentCondition.chory));
        System.out.println(c.max());
        System.out.println("getStudent");
        System.out.println("******");
        c.summary();
        c.getStudent(s2);
        c.addStudent(new Student("Kamil", "Wolski", 1999, StudentCondition.odrabiajacy, 1000));
        System.out.println("*****");
        c.summary();
        System.out.println("sort by name");
        c.sortByName();
        List<Student>  students =c.sortByPoints();
        students.forEach(Student::print);
        System.out.println("#$##@$#@$#@$@");
        List<Student> studentList1 = c.searchPartial("Rozap");
        studentList1.forEach(Student::print);

        HashMap<String, Class> map = new HashMap<>();

        ClassContainer classContainer = new ClassContainer(map);
        classContainer.addClass2("P1", c);
        classContainer.addClass2("P2", c2);

        System.out.println("***********");
        classContainer.summary();
    }
}

enum StudentCondition{
    odrabiajacy, chory, nieobecny;
}

class Student implements  Comparable<Student>{
    private final String imie;
    private final String nazwisko;
    private final int rokUrodzenia;

    public double getIloscPunktow() {
        return iloscPunktow;
    }

    public StudentCondition getStudentCondition() {
        return studentCondition;
    }

    private double iloscPunktow;
    private StudentCondition studentCondition;

    public Student(String imie, String nazwisko, int rokUrodzenia, StudentCondition studentCondition) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.rokUrodzenia = rokUrodzenia;
        this.iloscPunktow = 0;
        this.studentCondition = studentCondition;
    }

    public Student(String imie, String nazwisko, int rokUrodzenia, StudentCondition studentCondition, double p) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.rokUrodzenia = rokUrodzenia;
        this.iloscPunktow = p;
        this.studentCondition = studentCondition;
    }

    public void print() {
        System.out.println( "Student{" +
                "imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", rokUrodzenia=" + rokUrodzenia +
                ", iloscPunktow=" + iloscPunktow +
                ", studentCondition=" + studentCondition +
                '}'
        );
    }

    @Override
    public int compareTo(Student o) {
        return Integer.parseInt(String.valueOf(nazwisko.equals(o.nazwisko)));
    }

    public void setIloscPunktow(double iloscPunktow) {
        this.iloscPunktow = iloscPunktow;
    }

    public void setStudentCondition(StudentCondition studentCondition) {
        this.studentCondition = studentCondition;
    }

    public String getNazwisko() {
        return nazwisko;
    }
}

class Class{
    public Class(String nazwagrupy, int d) {
        this.nazwagrupy = nazwagrupy;
        this.studentList = new LinkedList<>();
        MAX_STUDENTS = d;
    }

    public Class(String nazwagrupy, LinkedList studentList) {
        this.nazwagrupy = nazwagrupy;
        this.studentList = studentList;
    }

    private int MAX_STUDENTS = 30;
    private String nazwagrupy;
    private LinkedList<Student> studentList;

    public void addStudent(Student student){
        if(studentList.contains(student))
            System.out.println("Ten student już istnieje");
        else if(studentList.size() < MAX_STUDENTS)
            studentList.add(student);
    }

    public void addPoints(Student student, double points){
        Student s = studentList.get(studentList.indexOf(student));
        s.setIloscPunktow(s.getIloscPunktow() + points);
    }

    public void getStudent(Student s){
        studentList.remove(s);
    }

    public void changeCondition(Student s, StudentCondition studentCondition){
        s.setStudentCondition(studentCondition);
    }

    public void removePoints(Student s, double points){
        s.setIloscPunktow(s.getIloscPunktow() - points);
    }

    public Student search(String s){
        Student student=null;
        for (Student st : studentList){
            if(st.getNazwisko().equals(s)) {
                student = st;
                break;
            }
        }
        return student;
    }
    
    public List<Student> searchPartial(String s){
        List<Student> list = new LinkedList<>();
        for(Student st : studentList){
            if(st.getNazwisko().contains(s)){
                list.add(st);
            }
        }
        return list;
    }

    public int countByCondition(StudentCondition studentCondition){
        return (int) studentList.stream()
                .map(Student::getStudentCondition)
                .filter(studentCondition1 -> studentCondition1.equals(studentCondition))
                .count();
    }

    public void summary(){
        studentList.forEach(Student::print);
    }

    public void sortByName(){
        studentList.stream()
                .map(Student::getNazwisko)
                .sorted()
                .forEach(System.out::println);
    }

    public List<Student> sortByPoints(){
        List<Student> s = studentList.stream()
                .sorted(Comparator.comparing(Student::getIloscPunktow)).
                collect(Collectors.toList());
        return s;
    }

    public double max(){
        return studentList.stream()
                .mapToDouble(Student::getIloscPunktow)
                .max().orElseThrow(NoSuchElementException::new);
    }

    public String getNazwagrupy() {
        return nazwagrupy;
    }
}

class ClassContainer{
    Map<String, Class> map;

    public ClassContainer(HashMap<String, Class> map) {
        this.map = map;
    }

    public void addClass(String s , int d){
        Class temp = new Class(s, d);
        map.put(s, temp);
    }

    public void addClass2(String s , Class c){
        map.put(s, c);
    }

    public void removeClass(String s){
        map.remove(s);
    }

    public void summary(){
        for(Class cl : map.values()){
            System.out.println(cl.getNazwagrupy());
            cl.summary();
            System.out.println();
        }
    }
}
