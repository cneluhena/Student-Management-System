import {
  Input,
  InputGroup,
  Stack,
  Flex,
  AbsoluteCenter,
  Container,
  Heading,
  Center,
  Button,
} from "@chakra-ui/react";

import "../styles/Login.css";
import { useState } from "react";
import {FieldValues, useForm} from 'react-hook-form';


const Login = () => {

    // const [userName, setUserName] = useState("");
    // const [password, setPassword] = useState("");
    
    const {register, handleSubmit} = useForm();
    const onSubmit = (data:FieldValues)=>{
      console.log(data);
    }

    
    return (
    

    <form onSubmit={handleSubmit(onSubmit)}>
    <Center h="100vh">
      <Container className="login-container">
       
        <Stack padding="16px">
          <Center>
            <Heading size="lg" marginBottom="15px">
              Login
            </Heading>
          </Center>

          <Flex justifyContent="center">
            <InputGroup size="md" w="75%">
              <Input {...register('username')}placeholder="User Name" type='text' size="md"  bgColor="#ffffff" />
            </InputGroup>
          </Flex>


          <Flex justifyContent="center">
            <InputGroup size="md" w="75%">
              <Input
                {...register('password')}
                placeholder="Password"
                type="password"
                size="md"
                bgColor="#ffffff" name='password'
              />
            </InputGroup>
          </Flex>

          <Button colorScheme="blue" marginX="120px" marginY="20px" type='submit'>
            Login
          </Button>
        </Stack>
      </Container>
    </Center>
    </form>
  );
};

export default Login;
