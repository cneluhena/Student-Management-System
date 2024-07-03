import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
interface StudentData {
    student_id: string;
    full_name?: string;
    nic?: string;
    // Add other fields as necessary
  }
const StudentProfile = () =>{
    const { id } = useParams();
    const [studentData, setStudentData] = useState<StudentData>({} as StudentData);
    useEffect(() => {
        // Fetch student data from backend
        axios.get(`http://localhost:8080/student/profile?id=${id}`)
          .then(response => {
            setStudentData(response.data[0]);
            console.log(response.data);
          })
          .catch(error => {
            console.error('Error fetching student data:', error);
            
          });
      }, [id]);
    return (
        <div>
            <h1>Student Profile</h1>
            <div>
                {studentData.student_id}
            </div>
            <div>
                {studentData.full_name}
            </div>
        </div>
    )
}

export default StudentProfile;