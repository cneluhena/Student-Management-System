import {
    FormControl,
    FormLabel,
    Input,
    Center,
    Flex,
    Container,
    Heading,
    Button,
    FormErrorMessage,
    Text,
    Select
  
  } from "@chakra-ui/react";
  import "../styles/Student.css";
  import { useState, useRef, useEffect, ChangeEventHandler, ChangeEvent } from "react";
  import { FieldValues,  useForm } from "react-hook-form";

  import axios from "axios";
  
  const EnrollmentForm = () => {
    interface Subject {
      grade: string,
      subjects: string[]
      
    }

    interface Teacher{
      teacherid: number,
      fullname: string
    }

    const [selectedGrade, setSelectedGrade] = useState('');
    const [subjectData, setSubjectData] = useState([]);
    const [currentGradeData, setCurrentGradeData] = useState<Subject|undefined>(undefined)
    const [selectedSubject, setSelectedSubject] = useState('');
    const [teachers, setTeachers] = useState<Teacher[]>([]);
    const [selectedTeacher, setSelectedTeacher] = useState('');
;
    useEffect(()=>{
      axios.get("http://localhost:8080/course/get")
      .then(response=>setSubjectData(response.data))
      .catch(error=>console.error(error));
     
    }, [])
    const {
      register,
      handleSubmit,
      formState: { errors }
    } = useForm();
  
    // const handleValidate = async () => {
    //   const isValid = await trigger();
    //   if (isValid) {
    //     handleSubmit(onSubmit)();
    //   }
    // };
  
    const onSubmit = async (data: FieldValues) => {
      try{
        const full_name = data.first_name + ' ' + data.last_name;
        data.full_name = full_name;
        await axios.post("http://localhost:8080/student/", data);
        console.log(data);
  
      } catch{
        console.log('error');
      }
      
    };

  
    //handle the the change in grade selecting
    const handleChange = (event:ChangeEvent<HTMLSelectElement>)=>{
        const grade = event.target.value;
        setSelectedGrade(event.target.value);  //selected grade
        setSelectedSubject('');
        const data = subjectData.find((item:Subject)=>item.grade == grade);
        setCurrentGradeData(data);
        setTeachers([]);
      
      
    }


    //handling the change of the subject select element
    const handleSubjectChange = (event:ChangeEvent<HTMLSelectElement>)=>{
      const subject = event.target.value;
      setSelectedSubject(event.target.value);
      if (selectedGrade != ''){
        axios.get(`http://localhost:8080/course/getteachers?course=${subject}&grade=${selectedGrade}`).
        then(response=>setTeachers(response.data)).
        catch(error=>console.error(error));
      }
    }

  
    return (
      <Center>
           <form onSubmit={handleSubmit(onSubmit)}>
          <Center>
            <Heading marginBottom="20px">Enrollment Form</Heading>
          </Center>
       
  
          <FormLabel>Student ID</FormLabel>
          <Input
            {...register("student_id")}
            placeholder="Enter Student ID"
            type="text"
            marginBottom="15px"
          />
          <FormControl w="60vw">
            <FormLabel>Grade</FormLabel>
            <Select placeholder='Select Grade' marginBottom="15px" value={selectedGrade} onChange={handleChange}>
              <option value='Grade 1'>Grade 1</option>
              <option value='Grade 2'>Grade 2</option>
              <option value='Grade 3'>Grade 3</option>
              <option value='Grade 4'>Grade 4</option>
              <option value='Grade 5'>Grade 5</option>
              <option value='Grade 6'>Grade 6</option>
              <option value='Grade 7'>Grade 7</option>
              <option value='Grade 8'>Grade 8</option>
              <option value='Grade 9'>Grade 9</option>
              <option value='Grade 10'>Grade 10</option>
              <option value='Grade 11'>Grade 11</option>
              <option value='A/L'>A/L</option>
            </Select>
          </FormControl>
  
          <FormControl w="60vw" >
          <FormLabel>Subject</FormLabel>
          <Select placeholder='Select Subject' isDisabled={selectedGrade ==''} value={selectedSubject} onChange={handleSubjectChange} marginBottom="15px">
            {
              currentGradeData?.subjects.map((subject, index)=>(
                <option key={index}>{subject}</option>
              ))
            }
              
            </Select>
          </FormControl>


          <FormControl w="60vw" >
          <FormLabel>Teacher</FormLabel>
          <Select placeholder='Select Teacher' isDisabled={selectedSubject==''} value={selectedTeacher} marginBottom="15px">
            {
              teachers.map((teacher, index)=>(
                <option key={teacher.teacherid}>{teacher.fullname}</option>
              ))
            }
              
            </Select>
          </FormControl>
  
          
  
          <Flex marginBottom='20px'>
          {/* <Container paddingLeft="0px">
          <FormControl  isRequired>
          <FormLabel>NIC</FormLabel>
          <Input
            {...register("nic", { required: true, minLength:3 })}
            id="nic"
            placeholder="Enter NIC"
            type="text"
          />
          {errors.nic?.type=='required' && <Text color='red'>NIC is required</Text>}
          {errors.nic?.type=='minLength' && <Text color='red'>NIC is required</Text>}
          </FormControl>
          </Container> */}
  
  
  
          <Container margin = '0px' paddingLeft = '0px' paddingRight="0px">
          <FormControl  isRequired>
  
          <FormLabel>NIC</FormLabel>
          <Input
            {...register("nic", { required: true, minLength:11 })}
            id="nic"
            placeholder="Enter NIC"
            type="text"
          />
          {errors.nic?.type=='required' && <Text color='red'>NIC is required</Text>}
          {errors.nic?.type=='minLength' && <Text color='red'>NIC must have 10 characters</Text>}
          </FormControl>
          </Container>
          
          </Flex>
           <FormControl w="60vw" >
          <Flex justifyContent="space-between" marginBottom="20px">
            <Container paddingLeft="0px">
              <FormLabel>Father's full name</FormLabel>
              <Input
                {...register("father_name")}
                id="fathername"
                placeholder="Enter father's full name"
                type="text"
              />
            </Container>
            <Container paddingRight="0px">
              <FormLabel>Father's phone number</FormLabel>
              <Input
                {...register("father_phone_number")}
                id="fatherphone"
                placeholder="Enter mother's full name"
                type="text"
              />
            </Container>
          </Flex>
          </FormControl>
  
          <FormControl w="60vw" >
          <Flex justifyContent="space-between" marginBottom="20px">
            <Container paddingLeft="0px">
              <FormLabel>Mother's full name</FormLabel>
              <Input
                {...register("mother_name")}
                id="mothername"
                placeholder="Enter father's full name"
                type="text"
              />
            </Container>
            <Container paddingRight="0px">
              <FormLabel>Mother's phone number</FormLabel>
              <Input
                {...register("mother_phone_number")}
                id="motherphone"
                placeholder="Enter mother's full name"
                type="text"
              />
            </Container>
          </Flex>
          </FormControl> 
  
          <Center>
            <Button colorScheme="blue" type='submit' >
              Add Student
            </Button>
          </Center>
          </form>
      </Center>
    );
  };
  
  export default EnrollmentForm;
  