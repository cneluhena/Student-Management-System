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
  Text

} from "@chakra-ui/react";
import "../styles/Student.css";
import { useState, useRef } from "react";
import { FieldValues,  useForm } from "react-hook-form";
import axios from "axios";

const StudentForm = () => {
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



  return (
    <Center>
         <form onSubmit={handleSubmit(onSubmit)}>
        <Center>
          <Heading marginBottom="20px">Student Form</Heading>
        </Center>
     

        <FormLabel>Student ID</FormLabel>
        <Input
          {...register("student_id")}
          placeholder="Enter Student ID"
          type="text"
          marginBottom="15px"
        />
        <FormControl w="60vw" isRequired>
          <FormLabel>First Name</FormLabel>
          <Input
            {...register("first_name")}
            id="firstname"
            placeholder="Enter First Name"
            type="text"
            marginBottom="15px"
          />
        </FormControl>

        <FormControl w="60vw" >
        <FormLabel>Last Name</FormLabel>
        <Input
          {...register("last_name")}
          id="lastname"
          placeholder="Enter Last Name"
          type="text"
          marginBottom="15px"
        />
        </FormControl>

        <FormControl w="60vw" isRequired>
        <FormLabel>Birth Date</FormLabel>
        <Input
          {...register("birth_date")}
          id="birthdate"
          placeholder="Select Date and Time"
          size="md"
          type="date"
          marginBottom="15px"
        />
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

export default StudentForm;
