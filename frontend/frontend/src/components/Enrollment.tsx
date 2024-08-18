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
    Box
  
  } from "@chakra-ui/react";
  import "../styles/Student.css";
  import { useState, useRef, useEffect, ChangeEventHandler, ChangeEvent, MouseEventHandler } from "react";
  import { FieldValues,  useForm } from "react-hook-form";

  import axios, { AxiosError } from "axios";
import { CheckCircleIcon, WarningIcon } from "@chakra-ui/icons";
  
  const EnrollmentForm = () => {
    interface Subject {
      grade: string,
      subjects: string[]
      
    }

    interface Teacher{
      teacherid: number,
      fullname: string
    }
    
    interface Course{
      course_id: number, 
      course_name: string, 
      grade: string, 
      teacher_id: number, 
      medium: string, 
      day: string, 
      start_time: string, 
      end_time: string, 
      full_name: string
    }

    const [selectedGrade, setSelectedGrade] = useState('');
    const [subjectData, setSubjectData] = useState([]);
    const [currentGradeData, setCurrentGradeData] = useState<Subject|undefined>(undefined)
    const [selectedSubject, setSelectedSubject] = useState('');
    const [teachers, setTeachers] = useState<string[]|undefined>(undefined);
    const [courses, setCourses] = useState<Course[]|undefined>(undefined);
    const [selectedTeacher, setSelectedTeacher] = useState('');
    const [selectedMedium, setSelectedMedium] = useState('');
    const [selectedCourseId, setSelectedCourseId] = useState('');
    const [notFoundWarning, setNotFoundWarning] = useState<Boolean>();
    const [check, setCheck] = useState<Boolean>(false);
    const [loading, setLoading] = useState(false);
    const [isSuccessful, setIsSuccessful] = useState(false);
    const [feedbackMessage, setFeedbackMessage] = useState('');
    const [showSuccessmessage, setShowSuccessmessage] = useState(false);


    const grades = ['Grade 1', 'Grade 2', 'Grade 3', 'Grade 4','Grade 5', 'Grade 6', 'Grade 7', 'Grade 8', 'Grade 9', 'Grade 10', 'Grade 11', 'A/L']
    const { isOpen, onOpen, onClose } = useDisclosure();


    useEffect(()=>{
      axios.get("http://localhost:8080/course/get")
      .then(response=>setSubjectData(response.data))
      .catch(error=>console.error(error));
     
    }, [])
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

    const checkAvailability = ()=>{
        const studentId = getValues("student_id")
        axios.get(`http://localhost:8080/student/find?id=${studentId}`).
        then(response=>{
          setNotFoundWarning(false);
          setCheck(true);
          
        }).catch(error=>setNotFoundWarning(true));

    }

    const handleModalClose = ()=>{
      setShowSuccessmessage(false);
      onClose();
    }
   
    return (
      <Center>
           <form onSubmit={handleSubmit(onSubmit)}>
          <Center>
            <Heading marginBottom="20px">Enrollment Form</Heading>
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
          
          <FormControl w="60vw">
            <FormLabel>Grade</FormLabel>
            <Select placeholder='Select Grade' marginBottom="15px" value={selectedGrade} onChange={handleChange} isRequired>
              {grades.map((grade, index)=>(
                <option key={index} value={grade}>{grade}</option>
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
          <Modal blockScrollOnMount={false} isOpen={isOpen} onClose={handleModalClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalBody paddingTop={8}>
            {loading && (
              <Box textAlign="center">
              <Spinner size="lg" />
            </Box>
            )}
            {
              isSuccessful && showSuccessmessage && (
                <Alert
                  status='success'
                  variant='subtle'
                  flexDirection='column'
                  alignItems='center'
                  justifyContent='center'
                  textAlign='center'
                  height='200px'
                >
                  <AlertIcon boxSize='40px' mr={0} />
                  <AlertTitle mt={4} mb={1} fontSize='lg'>
                    Registration Successful!
                  </AlertTitle>
                  <AlertDescription maxWidth='sm'>
                    Course enrollment successful!
                  </AlertDescription>
                </Alert>
              )
            }
            {
              !isSuccessful && showSuccessmessage &&(
                <Alert
                status='warning'
                variant='subtle'
                flexDirection='column'
                alignItems='center'
                justifyContent='center'
                textAlign='center'
                height='200px'
              >
                <AlertIcon boxSize='40px' mr={0} />
                <AlertTitle mt={4} mb={1} fontSize='lg'>
                  Registration Failed!
                </AlertTitle>
                <AlertDescription maxWidth='sm'>
                  {feedbackMessage}
                </AlertDescription>
              </Alert>
              )
            }
            
          </ModalBody>

          <ModalFooter>
            { !loading && 
            <Button size='sm' colorScheme='blue' mr={3} onClick={onClose}>
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
  
  export default EnrollmentForm;
  