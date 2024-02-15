import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom'; 
import BasicLayout from '../../layouts/BasicLayout';


const UpdatePage = () => {
  
  const navigate = useNavigate(); 

  const {id} = useParams(); //url id 매핑
  const [formData, setFormData] = useState({
    title: '',
    content: '',
  });

  useEffect(() => {
    if (id) {
      getBoard();
    }
  }, [id]);

  const getBoard = async () => {
    try {
      const response = await axios.get(`http://127.0.0.1:8080/api/post/detail/${id}`);
      setFormData(response.data);
    } catch (error) {
      console.error('Error fetching post:', error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.title.trim()) {
      alert('제목을 입력해주세요.');
      return;
    }

    if (!formData.content.trim()) {
      alert('내용을 입력해주세요.');
      return;
    }

    try {
      const response = await axios.put(`http://127.0.0.1:8080/api/post/update/${id}`, formData);
      alert('수정되었습니다.');
      console.log('글 수정 성공:', response.data);
      // 글 수정 후 다음 단계로 이동하거나 필요한 작업을 수행합니다.
      window.location.href = '/'; //글 수정 후 리스트로 이동
    } catch (error) {
    }
  };

  return (
    <BasicLayout>
    <div style={{ 
      display: 'flex', justifyContent: 'center', alignItems: 'center', 
      width: '100%', height: '80vh' , display:'flex', flexDirection:'column'
      }}>
    <div>
      <h2>게시물 수정 페이지</h2>
      <br />
      <form onSubmit={handleSubmit}>
        <div>
          <label>제목</label>
          <input
          placeholder="제목을 입력해주세요" className="input input-bordered w-full max-w-xs" 
          type="text" name="title" value={formData.title} onChange={handleChange} />
        </div>
        <br />
        <div>
          <label>내용</label>
          <textarea 
          className="textarea textarea-bordered w-full max-w-xs" placeholder="내용을 입력해주세요"
          name="content"  value={formData.content} onChange={handleChange}></textarea>
        </div>
        <button className="btn btn-primary" style={{ marginRight: '50px'}} type="submit">수정 하기</button>
        <button className="btn btn-primary" onClick={() => { navigate('/post/list') }}>뒤로가기</button>
      </form>
    </div>
    </div>
    </BasicLayout>
  );
};

export default UpdatePage;