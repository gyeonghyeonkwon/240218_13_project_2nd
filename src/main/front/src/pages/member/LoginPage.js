import axios from 'axios';
import React, { useState } from 'react';


const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!username.trim()) {
      alert('아이디를 입력해주세요');
      return;
    }

    if (!password.trim()) {
      alert('비밀번호를 입력해주세요');
      return;
    }

    try {
      const response = await axios.post('http://localhost:8090/api/v1/members/login', {
        username,
        password
      });
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    
    <div style={{ 
      display: 'flex', justifyContent: 'center', alignItems: 'center', 
      width: '100%', height: '100vh'
      }}>
      <form style={{ display:'flex', flexDirection:'column'}} onSubmit={handleSubmit}>
      <label>id</label>
      <input className="input input-bordered w-full max-w-xs"
        type="username" 
        value={username} 
        onChange={(e) => setUsername(e.target.value)} 
        placeholder="Username" 
      />
      <label>pw</label>
      <input className="input input-bordered w-full max-w-xs"
        type="password" 
        value={password} 
        onChange={(e) => setPassword(e.target.value)} 
        placeholder="Password" 
      />
      <br />
      <button className="btn btn-primary" type="submit">Submit</button>
    </form>
    </div>
    
  );
};
export default LoginPage;