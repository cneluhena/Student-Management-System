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
    Select,
    Stack,
    Icon,
    Alert,
    AlertIcon,
    AlertTitle,
    AlertDescription,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter,
    useDisclosure,
    Spinner,
    Box,
    TableContainer,
    Table,
    TableCaption,
    Thead,
    Tr,
    Th,
    Tbody,
    Td,
    Tfoot
  
  } from "@chakra-ui/react";
  import "../styles/Student.css";
  import { useState, useRef, useEffect, ChangeEventHandler, ChangeEvent, MouseEventHandler } from "react";
  import { FieldValues,  useForm } from "react-hook-form";

  import axios, { AxiosError } from "axios";
import { CheckCircleIcon, WarningIcon } from "@chakra-ui/icons";
  
  const PaymentForm = () => {
    interface Subject {
      grade: string,
      subjects: string[]
      
    }

    interface Student{
        student_id: number,
        full_name: string,
        birth_date: string,
        guardian_name: string,
        guardian_phone_number: string,
        nic:string
    }

    interface Teacher{
      teacher_id: number,
      full_name: string
    }

    interface Enrollment{
        enrollment_id: number,
        student: Student,
        course: Course
    }
    
    interface Course{
      course_id: number, 
      course_name: string, 
      grade: string, 
      teacher: Teacher, 
      medium: string, 
      day: string, 
      start_time: string, 
      end_time: string, 
      full_name: string
    }

    const [enrollmentData, setEnrollmentData] = useState<Enrollment[]>();
    const [selectedGrade, setSelectedGrade] = useState('');
    const [subjectData, setSubjectData] = useState([]);
    const [currentGradeData, setCurrentGradeData] = useState<Subject|undefined>(undefined)
    const [selectedSubject, setSelectedSubject] = useState('');
    const [teachers, setTeachers] = useState<string[]|undefined>(undefined);
    const [courses, setCourses] = useState<Course[]|undefined>(undefined);
    const [selectedTeacher, setSelectedTeacher] = useState('');
    const [selectedMedium, setSelectedMedium] = useState('');
    const [selectedCourseId, setSelectedCourseId] = useState('');
    const [currentCourseId, setCurrentCourseId] = useState<number>();
    const [notFoundWarning, setNotFoundWarning] = useState<Boolean>();
    const [check, setCheck] = useState<Boolean>(false);
    const [loading, setLoading] = useState(false);
    const [isSuccessful, setIsSuccessful] = useState(false);
    const [feedbackMessage, setFeedbackMessage] = useState('');
    const [showSuccessmessage, setShowSuccessmessage] = useState(false);
    const [courseFee, setCourseFee] = useState('');



    const grades = ['Grade 1', 'Grade 2', 'Grade 3', 'Grade 4','Grade 5', 'Grade 6', 'Grade 7', 'Grade 8', 'Grade 9', 'Grade 10', 'Grade 11', 'A/L']
    const { isOpen, onOpen, onClose } = useDisclosure();


    
    const {
      register,
      getValues,
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
        const enrolllmentData = {
            "student_id": getValues("student_id"),
            "course_id": selectedCourseId
        }
          setLoading(true);
          await axios.post("http://localhost:8080/enrollment/add", enrolllmentData);
          setLoading(false);
          setIsSuccessful(true);
          setShowSuccessmessage(true);
  
      } catch(error){
        if (axios.isAxiosError(error)){
          setFeedbackMessage(error.response?.data);
        } else{
          setFeedbackMessage("An error occures when regisering. Try Again!")
        }
        setLoading(false);
        setIsSuccessful(false);
        setShowSuccessmessage(true);
        
      }
      
    };

  
    //handle the the change in grade selecting
    const handleChange = (event:ChangeEvent<HTMLSelectElement>)=>{
        const grade = event.target.value;
        setSelectedGrade(event.target.value);  //selected grade
        setSelectedSubject('');
        const data = subjectData.find((item:Subject)=>item.grade == grade);
        setCurrentGradeData(data);
        setSelectedTeacher('');
        setSelectedMedium('');
        setTeachers([]);

      
    }


    //operations that must be done after seelcting medium
    const handleMediumChange = (event:ChangeEvent<HTMLSelectElement>)=>{
      setSelectedMedium(event.target.value);
      setTeachers([...new Set(courses?.filter(course=>course.medium === event.target.value).map(course=>course.full_name))]);
    }

    //handling the change of the subject select element
    const handleSubjectChange = (event:ChangeEvent<HTMLSelectElement>)=>{
      const subject = event.target.value;
      setSelectedSubject(event.target.value);
      if (selectedGrade != ''){
        axios.get(`http://localhost:8080/course/getteachers?course=${subject}&grade=${selectedGrade}`).
        then(response=>setCourses(response.data)).
        catch(error=>console.error(error));
      }
    }

    const checkAvailability = async ()=>{
        const studentId = getValues("student_id");
        setLoading(true);
        try{
          const response = await axios.get(`http://localhost:8080/enrollment/getStudent?id=${studentId}`);
          const enrollmentData:Enrollment = response.data
          setLoading(false);
          setCheck(true);
          setEnrollmentData(response.data);
          setNotFoundWarning(false);
        } catch(error){
          console.log(error);
          setNotFoundWarning(true)
          setLoading(false)
        }
        // axios.get(`http://localhost:8080/enrollment/getStudent?id=${studentId}`).
        // then(axios.get(`http://localhost:8080/enrollment/getStudent?id=${studentId}`)response=>{
        //   setLoading(false);
        //   setCheck(true);
        //   setEnrollmentData(response.data);
        //   setNotFoundWarning(false);
        //   console.log(response.data);
        // }).catch(error=>{});

    }

    const handlePayClick = async (course_id: number)=>{
      try{

          const response = await axios.get(`http://localhost:8080/coursefee/get?id=${course_id}`);
          console.log(response.data);
          setCourseFee(response.data.coursefee);
          setCurrentCourseId(course_id);
        

      } catch(error){
        console.log(error);
      }
    }

    const handleModalClose = ()=>{
      setShowSuccessmessage(false);
      onClose();
    }


    const handlePayConfirm = async ()=>{
      try{
        const studentId = getValues("student_id");
        const courseId = currentCourseId;
        await axios.post(`http://localhost:8080/payments/pay`, {
          "student_id": studentId,
          "course_id": courseId,
          "amount": courseFee
        })
        console.log(studentId, courseId, courseFee);
      } catch(error){
        console.log(error);
      }
     
      

    }
   
    return (
      <Center>
           <form onSubmit={handleSubmit(onSubmit)}>
          <Center>
            <Heading marginBottom="20px">Payment Form</Heading>
          </Center>
       
  
          <FormLabel>Student ID</FormLabel>
          <Stack direction='row'>
          <Input
            {...register("student_id")}
            placeholder="Enter Student ID"
            type="text"
            marginBottom="15px"
            onChange={()=>{
              setCheck(false);
              setNotFoundWarning(false);
            }}
          />
          <Button onClick={checkAvailability}>Check</Button>
          {check &&  <Icon as={CheckCircleIcon} marginTop="12px" color="green.500" />}
          {notFoundWarning &&  <Icon as={WarningIcon} marginTop="12px" color="red.500"/>}
          </Stack>
          {loading && (
          <Box textAlign="center">
          <Spinner size="lg" />
        </Box>
        )}
          {
            check && <TableContainer>
            <Table variant='simple'>
              <Thead>
                <Tr>
                  <Th>Course Name</Th>
                  <Th>Grade</Th>
                  <Th>Teacher</Th>
                  <Th>Medium</Th>
                  <Th>Day</Th>
                  <Th>Class Time</Th>
                  <Th></Th>
                </Tr>
              </Thead>
              <Tbody>
                {
                  enrollmentData?.map((enrollment)=>(
                    <Tr>
                      <Td>{enrollment.course.course_name}</Td>
                      <Td>{enrollment.course.grade}</Td>
                      <Td>{enrollment.course.teacher.full_name}</Td>
                      <Td>{enrollment.course.medium}</Td>
                      <Td>{enrollment.course.day}</Td>
                      <Td>{enrollment.course.start_time}-{enrollment.course.end_time}</Td>
                      <Td><Button onClick={async ()=>{await handlePayClick(enrollment.course.course_id); onOpen();}}>Pay</Button></Td>

                  </Tr>
                  ))
                }
              
              </Tbody>
              
            </Table>
          </TableContainer>
          }
          <FormControl w="60vw">
            <FormLabel>Course</FormLabel>
            <Select placeholder='Select Course' marginBottom="15px" value={selectedCourseId} onChange={handleChange} isRequired>
              {enrollmentData?.map((enrollment, index)=>(
                <option key={index} value={enrollment.course.course_id}>{}</option>
              ))}
            </Select>
          </FormControl>
  
          <FormControl w="60vw" >
          <FormLabel>Subject</FormLabel>
          <Select placeholder='Select Subject' isDisabled={selectedGrade ==''} value={selectedSubject} onChange={handleSubjectChange} marginBottom="15px" isRequired>
            {
              currentGradeData?.subjects.map((subject, index)=>(
                <option key={index}>{subject}</option>
              ))
            }
              
            </Select>
          </FormControl>
          


          <FormControl w="60vw" >

    
          <FormLabel>Medium</FormLabel>
        
          <Select placeholder='Select Medium' isDisabled={selectedSubject ==''} value={selectedMedium} onChange={handleMediumChange} marginBottom="15px" isRequired>
            
            {
              [...new Set(courses?.map((course)=>course.medium))].map((medium)=>(
                <option key={medium}>{medium}</option>
              ))
            }
              
            </Select>
          </FormControl>
          
          <FormControl w="60vw" >

          <FormLabel>Teacher</FormLabel>
          <Select placeholder='Select Teacher' isDisabled={selectedMedium==''} value={selectedTeacher} onChange={(event)=>{setSelectedTeacher(event.target.value)}} marginBottom="15px" isRequired>
          {
              teachers?.map(teacher=>(
                  <option value={teacher}>{teacher}</option>
              ))
            }
            </Select>
            </FormControl>

            <FormControl w="60vw" >

          <FormLabel>Day and Time</FormLabel>
          <Select placeholder='Select Date and Time' isDisabled={selectedTeacher==''} value={selectedCourseId} onChange={(event)=>{setSelectedCourseId(event.target.value);console.log(event.target.value)}} marginBottom="15px" isRequired>
          { courses?.filter(course=> 
          course.grade === selectedGrade &&
          course.course_name === selectedSubject &&
          course.medium === selectedMedium && 
          course.full_name == selectedTeacher
          ).map(course=>(
            <option value={course.course_id}>{`${course.day} ${course.start_time}-${course.end_time}`}</option>
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
          
          </Flex>
        
  
          <Center>
            <Button isDisabled={check==false} onClick={onOpen} colorScheme="blue" type='submit' >
              Add Student
            </Button>
            
          </Center>
          <Modal isCentered blockScrollOnMount={false} isOpen={isOpen} onClose={handleModalClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalBody paddingTop={8}>
          <FormLabel>Course Fee</FormLabel>
            <Input
            isReadOnly
            value={courseFee}
          />
          </ModalBody>

          <ModalFooter>
          <Button size='sm' colorScheme='blue' onClick={async()=>{handlePayConfirm();}} marginRight={15}>Confirm Payment</Button>
            { !loading && 
            <Button size='sm' colorScheme='blue' mr={3} onClick={()=>{setCurrentCourseId(undefined);onClose()}}>
              Close
            </Button>
            
            }
            
          </ModalFooter>
        </ModalContent>
      </Modal>
          </form>
      </Center>
    );
  };
  
  export default PaymentForm;
  