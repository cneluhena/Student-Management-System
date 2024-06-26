import {
  Menu,
  MenuButton,
  MenuList,
  MenuItem,
  MenuItemOption,
  MenuGroup,
  MenuOptionGroup,
  MenuDivider,
  Button,
  InputGroup,
  InputLeftAddon,
  Input,
  Container,
  InputLeftElement,
  TableContainer,
  Table,
  Thead,
  Tr,
  Th,
  Td,
  Flex,
  Text,
  InputRightElement,
  useDisclosure,
  AlertDialog,
  AlertDialogOverlay,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogBody,
  AlertDialogFooter,
  Stack,
  Center,
} from "@chakra-ui/react";

import { ChevronDownIcon, Search2Icon, WarningIcon } from "@chakra-ui/icons";
import { RefObject, useRef, useState } from "react";
import axios from "axios";
import React from "react";


const StudentSearch = () => {
  const [buttonName, setButtonName] = useState("Select Search Type"); //show the text inside the dropdown button
  const [searchType, setSearchType] = useState(""); //the type of search that the user wants to do(student_id, nic, name)
  const [responseData, setResponseData] = useState([] as any[]); //store the response data from the server
  const [isAvailable, setIsAvailable] = useState(false); //this is used to show the table only if the data is available
  const searchvalue = useRef<HTMLInputElement>(null); //used to store the input of the search bar
  const { isOpen, onOpen, onClose } = useDisclosure()
  const [message, setMessage] = useState(""); //used to store the message alert message
  const cancelRef = React.useRef(null);
  const handleSearch = async (event: React.MouseEvent<HTMLButtonElement>) => {
    try {
     
      if (searchType === "student_id") {
        const response = await axios.get(
          `http://localhost:8080/student/find?id=${searchvalue.current?.value}`
        ); //getting the data from the server by id
        setResponseData(response.data);
        setIsAvailable(true);
      } else if (searchType === "nic") {
        const response = await axios.get(
          `http://localhost:8080/student/find?nic=${searchvalue.current?.value}`
        ); //getting the data from the server by nic
        setResponseData(response.data);
        setIsAvailable(true);

      } else {
        setIsAvailable(false); //if the user selects the name option, the table will not be shown
      }
    } catch (error: any) {
      setIsAvailable(false);
      if (error.response) {
        if (searchvalue.current?.value === "") {
            setMessage("Please enter a value to search");
            onOpen();
        } else{
            console.error(error.response.data);
            setMessage("Student does not exist");
            onOpen();
        }
       
        //if there is an error in the response it will show the message thrwon by the server
      } else console.error(error.message); //if there is an error in the request it will show the error message
    }
  };

  //function to handle the menu item click
  const handleMenuItemClick = (
    buttonLabel: string,
    searchTypeValue: string
  ) => {
    setButtonName(buttonLabel);
    setSearchType(searchTypeValue);
    setIsAvailable(false);
    if (searchvalue.current) {
      searchvalue.current.value = ""; // Clear the search input
    }
  };

  return (
    <>
      <Container>
        <Flex>
          <InputGroup marginInlineEnd="15px">
            <InputLeftElement>
              <Search2Icon />
            </InputLeftElement>
            <Input ref={searchvalue} type="search" placeholder="Search" />
            <InputRightElement width="auto">
              <Menu>
                <MenuButton
                  as={Button}
                  fontSize="14"
                  height="2rem"
                  marginRight="5px"
                  rightIcon={<ChevronDownIcon />}
                >
                  {buttonName}
                </MenuButton>
                <MenuList>
                  <MenuItem
                    onClick={() => {
                      handleMenuItemClick("Search By Student ID", "student_id");
                    }}
                  >
                    Search By Student ID
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      handleMenuItemClick("Search By NIC", "nic");
                    }}
                  >
                    Search By NIC
                  </MenuItem>
                  <MenuItem
                    onClick={() => {
                      handleMenuItemClick("Search By Name", "name");
                    }}
                  >
                    Search By Name
                  </MenuItem>
                </MenuList>
              </Menu>
            </InputRightElement>
          </InputGroup>
          <Button colorScheme="blue" type="button" onClick={handleSearch}>
            Search
          </Button>
        </Flex>
      </Container>

      <TableContainer>
        <Table variant="simple">
          <Thead>
            <Tr>
              <Th>Student ID</Th>
              <Th>Full Name</Th>
              <Th>NIC</Th>
            </Tr>
            
            
            </Thead>
            {isAvailable &&
              responseData.map((data, index) => {
                return (
                  <Tr key={index}>
                    <Td>{data.student_id}</Td>
                    <Td>{data.full_name}</Td>
                    <Td>{data.nic}</Td>
                  </Tr>
                );
              })}
          
        </Table>
      </TableContainer>

      <Container >
      <AlertDialog
        isOpen={isOpen}
        leastDestructiveRef={cancelRef}
        onClose={onClose}

      >
        <AlertDialogOverlay>
          <AlertDialogContent>
            

            <AlertDialogBody alignItems='Center'>
                <Stack  direction='row'>
            
      <Center>
      <Text fontSize='18' >
      <WarningIcon  marginBottom ='5px' marginRight='10px' color="red.500"/>
        {message}
      </Text>
      </Center>
            
         
        
            </Stack>
            </AlertDialogBody>

            <AlertDialogFooter padding='0' margin='10px'>
              <Button ref={cancelRef} onClick={onClose}>
                Cancel
              </Button>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialogOverlay>
      </AlertDialog>
      </Container>
    </>
  );
};

export default StudentSearch;
