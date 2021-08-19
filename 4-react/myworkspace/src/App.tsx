import React from 'react';
import logo from './logo.svg';
import './App.css';


// JSX: Javascript 기반의 HTML 태그 형식
// 각각의 태그(element)들은 javascript 객체임
// 일반적인 html 태그 표기법과 다름

// JSX Elemnet
// const element = (
//   <h1 className="greeting">
//     Hello, world!
//   </h1>
// );

// 실제로 컴파일되는 결과
// const element = React.createElement(
//   'h1', // 태그 종류
//   {className: 'greeting'}, // 속성
//   'Hello, world!' // 컨텐트
// );

// document.createElement("div")
// 실제 DOM을 생성함

// React.creatElement("div", ...)
// 가상 DOM을 생성함
// 가상 DOM == javascript 객체
// 내부적으로 가상 DOM tree를 관리함

// 렌더링(rendering): 화면에 그리기
// 가상DOM을 생성하고 렌더링 시점(event loop)에 가상DOM을 HTML DOM으로 그림

// 일반 DOM
// DOM을 조작할 때마다 렌더링함 , 성능저하

// 가상 DOM
// 렌더링 주기에 따라서 변동사항만 렌더링함, 성능 향상

// --------------------------------------------------------------------------

// react 관련 자료는 2020년 이후 것으로만

// Function Component
// 대문자로 시작함
// JSX Element를 반환함
// JS함수인데, JSX Element를 반환함 == Component
// 리액트에서 컴포넌트는 JSX Element를 렌더링하는 함수

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}
// App.tsx 모듈의 기본 내보내기를 App함수로 함
export default App;
