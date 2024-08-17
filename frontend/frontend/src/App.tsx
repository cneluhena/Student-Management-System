import { ChakraProvider } from '@chakra-ui/react'
import { useState } from 'react';
import Login from './components/Login';
import StudentSearch from './components/StudentSearch';

import StudentProfile from './components/StudentProfile';
import EnrollmentForm from './components/Enrollment'

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import PaymentForm from './components/Payment';




const App = ()=>{

  return (
    <>
    <Router>
      <Routes>
      <Route path="/search" element={<StudentSearch/>}/>
      <Route path="/profile/:id" element={<StudentProfile/>}/>
      <Route path="/enrol" element={<EnrollmentForm/>}/>
      <Route path="/payment" element={<PaymentForm/>}/>
      </Routes>
    </Router>
    </>
      
  );

}

export default App;