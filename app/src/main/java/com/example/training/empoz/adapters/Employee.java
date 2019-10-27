package com.example.training.empoz.adapters;

public class















Employee {


        public String name;
        public String contact;
        public String age;
        public String department;
        public String email;

        public String position;


        public Employee() {
        }

        public Employee(String name, String contact,String age,String department,String email,String position) {
            this.name = name;
            this.contact = contact;
            this.age = age;
            this.department=department;
            this.email=email;
            this.position=position;

        }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getPosition() {
        return position;
    }
}
