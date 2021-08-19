// 계좌관리

import { useState } from "react";

// 버튼: 입금버튼, 출금버튼
// 버튼 클릭시에 입금 금액 또는 출금 금액을 입력 받을 수 있음
// 목록: 입금, 출금액 목록을 ul > li 로 표시한다
// 입금 금액은 <li> 입금: 금액 (녹색)</li> 으로 표시
// 출금 금액은 <li> 출금: -금액(빨간색)</li> 으로 표시
// 총액: 잔액을 입금, 출금 버튼 우측에 표시한다

const AccountManager = () => {
  const [rest, srest] = useState(0);
  const btnclick = () => {
    const c = prompt("금액");
    srest(eval(`${c}`));
  }
  const btnclick2 = () => {
    const d = prompt("금액");
    srest(eval(`${d}`));
  }
  return (
  <div>
    <h2>Account Manager</h2>
    <button onClick={() => {
      btnclick();
    }}
    >입금
    </button>
    <button onClick={()=> {
      btnclick2();
    }}
    >
      입금
    </button>
    <ul>
      {
    rest.map((num, index) =>
      num < 0 ? (
        <li> style={{color: "green"}} key={index}>
          입금:{rest}
        </li>
      ) : (
        <li style={{color: "red"}} key={index}>
          출금:{rest}
        </li>
      )
    )
  }
    </ul>
    
  </div>
  )
}
export default AccountManager;