// 계좌관리

import { useState } from "react";

// 버튼: 입금버튼, 출금버튼
// 버튼 클릭시에 입금 금액 또는 출금 금액을 입력 받을 수 있음
// 목록: 입금, 출금액 목록을 ul > li 로 표시한다
// 입금 금액은 <li> 입금: 금액 (녹색)</li> 으로 표시
// 출금 금액은 <li> 출금: -금액(빨간색)</li> 으로 표시
// 총액: 잔액을 입금, 출금 버튼 우측에 표시한다

const AccountManager = () => {
  const [result, setResult] = useState(0);
  // 입금 버튼 prompt 생성
  const deposit = () => {
    const a = prompt("입금할 금액을 입력해 주세요!",'');
    setResult(eval(`${a}`));
  }

  // 출금 버튼 prompt 생성
  const withdraw = () => {
    const b = prompt("출금할 금액을 입력해 주세요!",'');
    setResult(eval(`${b}`));
  }
  

  return (
    <div>
      <h2>Account Manager</h2>
      <button style={{color:"green"}} onClick={deposit}>입금</button>
      <button style={{color:"red"}}onClick={withdraw}>출금</button>
      <div>{result}</div>
      <ul>
  
      </ul>
    </div>
  )
}
export default AccountManager;