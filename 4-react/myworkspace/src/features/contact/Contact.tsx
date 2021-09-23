import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import { AppDispatch, RootState } from "../../store";
import { requestFetchContacts } from "./contactSaga";

// 날짜/시간
const getTimeString = (unixtime: number) => {
  const dateTime = new Date(unixtime);
  return `${dateTime.toLocaleDateString()} ${dateTime.toLocaleTimeString()}`;
};

const Contact = () => {
  // contact state값 가져오기
  const contact = useSelector((state: RootState) => state.contact);
  const history = useHistory();
  const dispatch = useDispatch<AppDispatch>();

  // fetch 4. 컴포넌트가 마운팅되는 시점에 실행
  useEffect(() => {
    // console.log(dispatch);
    // console.log(contact.isFetched);
    // 데이터 fetch가 안되었으면 데이터를 받아옴
    if (!contact.isFetched) {
      dispatch(requestFetchContacts());
    }
  }, [dispatch, contact.isFetched]);

  return (
    <div className="mx-auto" style={{ width: "40vw" }}>
      <h2 className="text-center my-5">Contact</h2>
      <div className="d-flex justify-content-end mb-2">
        {/* 새로고침 */}
        <button
          className="btn btn-secondary me-2"
          onClick={() => {
            dispatch(requestFetchContacts());
          }}
        >
          <i className="bi bi-arrow-clockwise"></i>
          새로고침
        </button>
        {/* 추가 */}
        <button
          type="button"
          className="btn btn-primary text-nowrap"
          onClick={() => {
            history.push("/contacts/create");
          }}
        >
          <i className="bi bi-plus me-1" />
          추가
        </button>
      </div>
      <table className="table table-hover">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">이름</th>
            <th scope="col">전화번호</th>
            <th scope="col">이메일</th>
            <th scope="col">작성일시</th>
          </tr>
        </thead>
        <tbody>
          {contact.data.map((item, index) => (
            <tr
              key={`contact-item-${index}`}
              style={{ cursor: "pointer" }}
              onClick={() => {
                history.push(`/contacts/detail/${item.id}`);
              }}
            >
              <td>{item.id}</td>
              <td>{item.name}</td>
              <td>{item.phone}</td>
              <td>{item.email}</td>
              <td>{getTimeString(item.createdTime)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Contact;
