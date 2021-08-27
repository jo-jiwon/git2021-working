// https://react.vlpt.us/styling/02-css-module.html
// css module
// 파일명.module.css
// css를 사용하는 컴포넌트 범위로 css class 사용범위를 좁힐 수 있음.

import "./App.scss";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import { Suspense, lazy } from "react";

import Home from "./domain/Home";

// SPA(Single Page Application)
// : 페이지 파일이 1개, index.html
// : 특정 영역(Switch)에 컴포넌트(js)를 로딩함
// : 애플리케이션이 컴파일될 때 import한 컴포넌트가 같이 컴파일됨
//   -> 컴파일됐을 때 파일크기가 커짐, 초기 로딩할 때 시간 걸림

// Lazy-Loading 처리
// 컴포넌트를 방문하는 시점에 로딩함
// const Counter = lazy(() => import("./domain/Counter"));
// const Calculator = lazy(() => import("./domain/CalculatorRef"));
// const Generator = lazy(() => import("./domain/Generator"));
// const AccountManager = lazy(() => import("./domain/AccountManagerRef"));
// const Components = lazy(() => import("./domain/Components"));
// const BootStrap = lazy(() => import("./domain/Bootstrap"));
// const Todo = lazy(() => import("./domain/Todo"));
const Todo = lazy(() => import("./domain/todo/Todo"));
const Feed = lazy(() => import("./domain/feed/Feed"));

// const Feed_1 = lazy(() => import("./components/Feed_1"));
const Contact = lazy(() => import("./domain/Contact_조지원"));
// React == 컴포넌트 개발 라이브러리
function App() {
  return (
    <Router>
      {/* main container */}
      <div className="mx-auto">
        <header className="bg-primary app-bar shadow">App Bar</header>
        <nav className="position-fixed bg-light shadow-sm draw-menu">
          <h3>My WorkSpace</h3>
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/todo">Todo</Link>
            </li>
            <li>
              <Link to="/feeds">Feeds</Link>
            </li>
            <li>
              <Link to="/contact">Contact</Link>
            </li>
            {/* <li>
              <Link to="/feed_1">Feed_1</Link>
            </li> */}
          </ul>
        </nav>
        <main className="content-container">
          {/* Suspense 컴포넌트로 로딩중에 보여줄 화면을 처리하는 것 */}
          {/* fallback={로딩중에 보여줄 컴포넌트} */}
          <Suspense fallback={<div>Loading...</div>}>
            <Switch>
              {/* Switch 영역에 컴포넌트가 로딩됨 */}

              {/* 해당 경로에 대해서 로딩할 컴포넌트 목록을 작성 */}
              <Route path="/" component={Home} exact />
              <Route path="/todo" component={Todo} />
              <Route path="/feeds" component={Feed} />
              <Route path="/contact" component={Contact} />
              {/* <Route path="/feed_1" component={Feed_1} /> */}
              {/* <Route path="/components" component={Components} />
              <Route path="/counter" component={Counter} />
              <Route path="/calculator" component={Calculator} />
              <Route path="/generator" component={Generator} />
              <Route path="/account-manager" component={AccountManager} />
              <Route path="/bootstrap" component={BootStrap} /> */}
            </Switch>
          </Suspense>
        </main>
      </div>
    </Router>
  );
}

// App.tsx 모듈의 기본 내보내기를 App 함수로 함
export default App;
