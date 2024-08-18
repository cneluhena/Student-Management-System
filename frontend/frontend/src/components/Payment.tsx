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
    const [currentCourseId, setCurrentCourseId] = useState<number>();
    const [notFoundWarning, setNotFoundWarning] = useState<Boolean>();
    const [check, setCheck] = useState<Boolean>(false);
    const [loading, setLoading] = useState(false);
    const [isSuccessful, setIsSuccessful] = useState(false);
    const [feedbackMessage, setFeedbackMessage] = useState('');
    const [showSuccessmessage, setShowSuccessmessage] = useState(false);
    const [courseFee, setCourseFee] = useState('');
    const [currCourseName, setCurrCourseName] = useState('');
    const [grade, setGrade] = useState('');
    const [teacher, setTeacher] = useState('');
    const [classTime, setClassTime] = useState('');
    const [isPayClicked, setIsPayClicked] = useState(false);
    const[confirmLoading, setConfirmLoading] = useState(false);
    const [isPayConfirmClicked, setIsPayConfirmClicked] = useState(false);
    const [studName, setStudName] = useState('');
    


    const { isOpen, onOpen, onClose } = useDisclosure();


    
    const {
      register,
      getValues,
      handleSubmit,
      formState: { errors }
    } = useForm();
  
   

    const checkAvailability = async ()=>{
        const studentId = getValues("student_id");
        setLoading(true);
        try{
          const response = await axios.get(`http://localhost:8080/enrollment/getStudent?id=${studentId}`);
          
          setLoading(false);
          setCheck(true);
          setEnrollmentData(response.data);
          setStudName(response.data[0].student.full_name);
          console.log(response.data);
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

    const handlePayClick = async (enrollment:Enrollment)=>{
      try{

          const response = await axios.get(`http://localhost:8080/coursefee/get?id=${enrollment.course.course_id}`);
          console.log(response.data);
          setCourseFee(response.data.coursefee);
          setCurrCourseName(enrollment.course.course_name);
          setCurrentCourseId(enrollment.course.course_id);
          setGrade(enrollment.course.grade);
          setTeacher(enrollment.course.teacher.full_name);
          setClassTime(enrollment.course.start_time + '-' + enrollment.course.end_time)
          setIsPayClicked(true);
          

      } catch(error){
        console.log(error);
      }
    }

    const handleModalClose = ()=>{
      setShowSuccessmessage(false);
      setIsSuccessful(false);
      onClose();
      setIsPayClicked(false);
      setIsPayConfirmClicked(false);
     
    }


    const handlePayConfirm = async ()=>{
      try{
        const studentId = getValues("student_id");
        const courseId = currentCourseId;
        setConfirmLoading(true);
        setIsPayClicked(false);
        setIsPayConfirmClicked(true);
     
          await axios.post(`http://localhost:8080/payments/pay`, {
            "student_id": studentId,
            "course_id": courseId,
            "amount": courseFee
          })
          setConfirmLoading(false)
          
          setShowSuccessmessage(true);
          setIsSuccessful(true);
   
        
      } catch(error){
        setLoading(false);
        console.log(error);
      }
     
      

    }
   
    return (
      <Center>
           <form>
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
        {check && <>
          <Box fontSize='lg' margin={15}>
            <b>Student Name:</b> <Text as="span" marginLeft={2}>{studName}</Text>
          </Box>
        </>}
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
                      <Td><Button onClick={async ()=>{
                        await handlePayClick(enrollment);
                         onOpen();}}>Pay</Button></Td>

                  </Tr>
                  ))
                }
              
              </Tbody>
              
            </Table>
          </TableContainer>
          }
          

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
          
          </Center>
          <Modal isCentered blockScrollOnMount={false} isOpen={isOpen} onClose={handleModalClose}>
        <ModalOverlay />
        <ModalContent>
        {confirmLoading && (
          <ModalBody paddingTop={8}>
              <Box textAlign="center">
              <Spinner size="lg" />
            </Box>
            </ModalBody>
            )}
          { 
          isPayClicked && 
          <ModalBody paddingTop={8}>
            
          <FormLabel>Course</FormLabel>
            <Input
            isReadOnly
            value={currCourseName}
          />
           <FormLabel>Course Fee</FormLabel>
            <Input
            isReadOnly
            value={courseFee}
          />
           <FormLabel>Grade</FormLabel>
            <Input
            isReadOnly
            value={grade}
          />
           <FormLabel>Teacher</FormLabel>
            <Input
            isReadOnly
            value={teacher}
          />
          <FormLabel>Class Time</FormLabel>
            <Input
            isReadOnly
            value={classTime}
          />
          </ModalBody>
  }
  
    {
      
      isSuccessful && showSuccessmessage && (
        <ModalBody paddingTop={8}>
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
            Payment Successful
          </AlertTitle>
          
        </Alert>
        </ModalBody>
      )
    }
  

          <ModalFooter>
            {!isPayConfirmClicked &&
          <Button size='sm' colorScheme='blue' onClick={async()=>{handlePayConfirm();}} marginRight={15}>Confirm Payment</Button>
            }
            { !confirmLoading && 
            <Button size='sm' colorScheme='blue' mr={3} onClick={()=>{
              setCurrentCourseId(undefined)
              setCurrCourseName('');
              setGrade('');
              setTeacher('');
              setClassTime('');
              ;handleModalClose()}}>
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
  