package com.driver.services;

import com.driver.models.Card;
import com.driver.models.Student;
import com.driver.repositories.CardRepository;
import com.driver.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    CardService cardService4;

    @Autowired
    CardRepository cardRepository;    //me

    @Autowired
    StudentRepository studentRepository4;

    public Student getDetailsByEmail(String email){

        int student_id=studentRepository4.findByEmailId(email).getId();
         Student student = studentRepository4.findById(student_id).get();


        return student;
    }

    public Student getDetailsById(int id){
        Student student = studentRepository4.findById(id).get();

        return student;
    }

    public void createStudent(Student student){
       Card card=cardService4.createAndReturn(student);  //me
       card.setStudent(student);
       student.setCard(card);
       cardRepository.save(card);

    }

    public void updateStudent(Student student){
        int id=student.getId();
        Card card=cardRepository.findById(id).get();
        Student updateStudent=studentRepository4.findById(id).get();
        updateStudent.setName(student.getName());
        updateStudent.setAge(student.getAge());
        updateStudent.setEmailId(student.getEmailId());
        updateStudent.setCountry(student.getCountry());
         card.setStudent(updateStudent);
         updateStudent.setCard(card);
         cardRepository.save(card);
    }

    public void deleteStudent(int id){

         cardService4.deactivateCard(id);
         studentRepository4.deleteCustom(id);
        //Delete student and deactivate corresponding card
    }
}
