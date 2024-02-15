import axios from "axios";
import {  useState } from "react";
import BasicLayout from "../../layouts/BasicLayout";
import { useNavigate } from "react-router-dom";

const WritePage = () => {

  const [formData, setFormData] = useState({
    title: '',
    content: '',
  });
  const { title,  content } =  formData; //비구조 할당
  const navigate = useNavigate(); 

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

 
  //저장 버튼을 눌렀을때 
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if(!title.trim()) {
      alert("제목을 입력해주세요.");
      return;
    }

    if(!content.trim()) {
      alert("내용을 입력해주세요.");
      return;
    }
    
    try {
      const response = await axios.post('http://127.0.0.1:8080/api/post/create', formData)
        alert('등록되었습니다.');
        console.log('글 생성 성공:', response.data);
        // 글 생성 후 다음 단계로 이동하거나 필요한 작업을 수행합니다.
        window.location.href = "/"; //글 저장 후 리스트로 이동
      }
     catch (error) {
      console.error('글 생성 실패!:', error);
    }
  };


  return (
<BasicLayout>
<div style={{ 
      display: 'flex', justifyContent: 'center', alignItems: 'center', 
      width: '100%', height: '80vh' , display:'flex', flexDirection:'column'
      }}>
    <div>
    <h2>글 작성 페이지</h2>

    <br />

    <div>
        <label>제목</label>
        <input type="text" placeholder="제목을 입력해주세요" className="input input-bordered w-full max-w-xs" name="title" value={title} onChange={handleChange} />
      </div>
      
      <br />
      
      <div>
        <label>내용</label>
        <textarea className="textarea textarea-bordered w-full max-w-xs" placeholder="내용을 입력해주세요"
          name="content"
          value={content}
          onChange={handleChange} 
        ></textarea>
        </div>
      </div>
     
      <div>
        <button className="btn btn-primary"  style={{ marginRight: '80px'}}onClick={handleSubmit}>저장</button>
        <button className="btn btn-primary"  onClick={() => { navigate('/post/list') }}>뒤로가기</button>

        </div>
      </div>
      </BasicLayout>
  );
};

export default WritePage;