import React from 'react';
import NaviGationBar from '../component/NaviGationBar';

//공통 레이아웃 구성 
function BasicLayout({children}) {
  return (
    <>
    <header >
      <NaviGationBar/>
    </header>
    <div>
    <main>{children}</main>
    </div>
    </>
  );
}

export default BasicLayout;